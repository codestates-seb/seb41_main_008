import * as AlertDialog from '@radix-ui/react-alert-dialog';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useAppDispatch } from 'hooks/hooks';
import { useNavigate } from 'react-router-dom';
import { setDeleteUserOpen } from 'store/toastSlice';
import customAxios from 'utils/api/axios';

export default function Alert({ id }: { id: number | undefined }) {
  const navigate = useNavigate();

  const queryClient = useQueryClient();
  const { mutate, isLoading, error } = useMutation({
    mutationFn: (id: string) => customAxios.delete(`api/members/${id}`),
    onSuccess: (data) => {
      queryClient.invalidateQueries(['members', 'mypage'], { exact: true });
      navigate('/');
    },
  });

  const dispatch = useAppDispatch();

  const deleteAccount = () => {
    mutate(id?.toString()!);
    dispatch(setDeleteUserOpen(true));
  };

  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  return (
    <AlertDialog.Root>
      <AlertDialog.Trigger asChild>
        <button className="Button emerald">Delete account</button>
      </AlertDialog.Trigger>
      <AlertDialog.Portal>
        <AlertDialog.Overlay className="AlertDialogOverlay" />
        <AlertDialog.Content
          className="AlertDialogContent"
          onOpenAutoFocus={(e) => e.preventDefault()}
        >
          <AlertDialog.Title className="AlertDialogTitle">
            Are you absolutely sure?
          </AlertDialog.Title>
          <AlertDialog.Description className="AlertDialogDescription">
            This action cannot be undone. This will permanently delete your
            account and remove your data from our servers.
          </AlertDialog.Description>
          <div className="flex gap-[25px] justify-end">
            <AlertDialog.Cancel asChild>
              <button className="Button mauve">Cancel</button>
            </AlertDialog.Cancel>
            <AlertDialog.Action asChild>
              <button className="Button red" onClick={deleteAccount}>
                Yes, delete account
              </button>
            </AlertDialog.Action>
          </div>
        </AlertDialog.Content>
      </AlertDialog.Portal>
    </AlertDialog.Root>
  );
}

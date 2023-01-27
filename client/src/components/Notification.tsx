import * as Toast from '@radix-ui/react-toast';
import { useAppSelector } from 'hooks/hooks';
import { setOpen } from 'store/toastSlice';

export default function Notification({
  children,
}: {
  children: React.ReactNode;
}) {
  const open = useAppSelector((state) => state.toast.open);

  return (
    <Toast.Provider>
      <Toast.Root open={open} onOpenChange={setOpen} className="ToastRoot">
        <Toast.Description className="ToastDescription">
          {children}
        </Toast.Description>
      </Toast.Root>

      <Toast.Viewport className="ToastViewport" />
    </Toast.Provider>
  );
}

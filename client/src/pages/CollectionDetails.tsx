import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { BsCheckCircleFill } from 'react-icons/bs';
import * as Toast from '@radix-ui/react-toast';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setOpen } from 'store/toastSlice';

export default function CollectionDetails() {
  const { id } = useParams();

  const open = useAppSelector((state) => state.toast.open);
  const dispatch = useAppDispatch();

  useEffect(() => {
    setTimeout(() => dispatch(setOpen(false)), 3000);
  }, [dispatch]);

  useEffect(() => {
    const getCollection = async () => {
      try {
        const res = await customAxios.get(`/api/collections/${id}`);

        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getCollection();
  }, [id]);

  return (
    <div>
      Collection Details
      <Toast.Provider>
        <Toast.Root open={open} onOpenChange={setOpen} className="ToastRoot">
          <Toast.Description className="ToastDescription">
            <p className="flex items-center gap-1 text-emerald-700">
              <span>
                <BsCheckCircleFill className="h-7 w-7" />
              </span>{' '}
              Created!
            </p>
          </Toast.Description>
        </Toast.Root>

        <Toast.Viewport className="ToastViewport" />
      </Toast.Provider>
    </div>
  );
}

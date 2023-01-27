import * as Toast from '@radix-ui/react-toast';

interface Props {
  children: React.ReactNode;
  open: boolean;
  setOpen: (open: boolean) => void;
}

export default function Notification({ children, open, setOpen }: Props) {
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

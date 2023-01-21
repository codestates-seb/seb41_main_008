import * as Dialog from '@radix-ui/react-dialog';
import { RxCross2 } from 'react-icons/rx';

export default function Modal() {
  return (
    <Dialog.Root>
      <Dialog.Trigger asChild>
        <button className="btn">Select a blockchain</button>
      </Dialog.Trigger>
      <Dialog.Portal>
        <Dialog.Overlay className="DialogOverlay" />
        <Dialog.Content className="DialogContent">
          <Dialog.Title className="DialogTitle text-lg font-bold">
            Blockchain
          </Dialog.Title>
          <Dialog.Description className="DialogDescription">
            Select the blockchain where you{"'"}d like new items from this
            collection to be added by default.
          </Dialog.Description>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Nisi, saepe?
          <Dialog.Close asChild>
            <button className="IconBtn" aria-label="Close">
              <RxCross2 className="h-4 w-4" />
            </button>
          </Dialog.Close>
        </Dialog.Content>
      </Dialog.Portal>
    </Dialog.Root>
  );
}

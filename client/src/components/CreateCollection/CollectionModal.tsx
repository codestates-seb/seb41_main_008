import * as Dialog from '@radix-ui/react-dialog';

import { useState } from 'react';
import { RxCross2 } from 'react-icons/rx';
import RadioButtons from './RadioButtons';

interface Blockchain {
  name: string;
  id: number;
}

interface Props {
  selectedCoin: Blockchain | null;
  setSelectedCoin: React.Dispatch<React.SetStateAction<Blockchain | null>>;
}

export default function CollectionModal({
  selectedCoin,
  setSelectedCoin,
}: Props) {
  const [coin, setCoin] = useState<Blockchain | null>(null);

  return (
    <div>
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <button className="btn">
            {selectedCoin?.name ? selectedCoin?.name : 'Select a blockchain'}
          </button>
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

            <RadioButtons selectedCoin={selectedCoin} setCoin={setCoin} />

            <div className="flex mt-2 justify-end">
              <Dialog.Close asChild>
                <button
                  onClick={() => coin && setSelectedCoin(coin)}
                  className="text-lg font-semibold px-3 py-1.5 green"
                >
                  Save changes
                </button>
              </Dialog.Close>
            </div>
            <Dialog.Close asChild>
              <button className="IconBtn" aria-label="Close">
                <RxCross2 className="h-4 w-4" />
              </button>
            </Dialog.Close>
          </Dialog.Content>
        </Dialog.Portal>
      </Dialog.Root>
    </div>
  );
}

import * as Dialog from '@radix-ui/react-dialog';
import { useState } from 'react';
import { RxCross2 } from 'react-icons/rx';
import CollectionOptions from './CollectionOptions';

interface Collection {
  collectionName: string;
  collectionId: number;
  logoImgName: string;
}

interface Props {
  collections: Collection[];
  selectedCol: Collection | undefined;
  setSelectedCol: React.Dispatch<React.SetStateAction<Collection | undefined>>;
}

export default function ItemModal({
  collections,
  selectedCol,
  setSelectedCol,
}: Props) {
  const [collection, setCollection] = useState<Collection>();

  return (
    <div>
      <Dialog.Root>
        <Dialog.Trigger asChild>
          <button className="btn">
            {selectedCol?.collectionName
              ? selectedCol?.collectionName
              : 'Select your Collection'}
          </button>
        </Dialog.Trigger>
        <Dialog.Portal>
          <Dialog.Overlay className="DialogOverlay" />
          <Dialog.Content className="DialogContent">
            <Dialog.Title className="DialogTitle text-lg font-bold">
              Collection
            </Dialog.Title>
            <Dialog.Description className="DialogDescription">
              This is the collection where your item will appear.
            </Dialog.Description>

            <CollectionOptions
              collections={collections}
              selectedCol={selectedCol}
              setCollection={setCollection}
            />

            <div className="flex mt-2 justify-end">
              <Dialog.Close asChild>
                <button
                  onClick={() => collection && setSelectedCol(collection)}
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

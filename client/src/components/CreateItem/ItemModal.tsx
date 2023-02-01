import * as Dialog from '@radix-ui/react-dialog';
import { useState } from 'react';
import { RxCross2 } from 'react-icons/rx';
import CollectionOptions from './CollectionOptions';
import { Collection } from './CreateItem';

interface Props {
  isLoading: boolean;
  error: Error | null;
  collections: Collection[] | undefined;
  selectedCol: Collection | undefined;
  setSelectedCol: React.Dispatch<React.SetStateAction<Collection | undefined>>;
}

export default function ItemModal({
  isLoading,
  error,
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
            {selectedCol?.collectionName ? (
              <div className="flex items-center space-x-3">
                <img
                  src={`${process.env.REACT_APP_IMAGE}${selectedCol.logoImgName}`}
                  alt="Collection logo"
                  className="h-8 w-8 object-cover rounded-full"
                />{' '}
                <span> {selectedCol?.collectionName}</span>
              </div>
            ) : (
              'Select your Collection'
            )}
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
              isLoading={isLoading}
              error={error}
              collections={collections}
              selectedCol={selectedCol}
              setCollection={setCollection}
            />
            {collections?.length ? (
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
            ) : null}
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

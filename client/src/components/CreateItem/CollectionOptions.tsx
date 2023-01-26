import React, { useState } from 'react';

interface Collection {
  collectionName: string;
  collectionId: number;
  logoImgName: string;
}

interface Props {
  collections: Collection[];
  selectedCol: Collection | undefined;

  setCollection: React.Dispatch<React.SetStateAction<Collection | undefined>>;
}

export default function CollectionOptions({
  collections,
  selectedCol,

  setCollection,
}: Props) {
  const [selected, setSelected] = useState<Collection>();
  const [alreadySelected, setAlreadySelected] = useState<
    Collection | undefined
  >(selectedCol);

  return (
    <div className="space-y-3">
      {collections.map((col) => (
        <button
          key={col.collectionId}
          onClick={() => {
            setAlreadySelected(undefined);
            setSelected(col);
            setCollection(col);
          }}
          className={`flex cursor-pointer space-x-3 font-bold p-3 w-fit rounded-md items-center ${
            (alreadySelected?.collectionId === col.collectionId ||
              selected?.collectionId === col.collectionId) &&
            'bg-gray-200'
          }`}
        >
          <img
            src={process.env.REACT_APP_IMAGE + col.logoImgName}
            alt="Collection logo"
            className="h-8 w-8 object-cover rounded-full"
          />
          <h3>{col.collectionName}</h3>
        </button>
      ))}
    </div>
  );
}

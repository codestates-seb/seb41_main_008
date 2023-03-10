import { useState } from 'react';
import { Collection } from './CreateItem';
import { Link } from 'react-router-dom';

interface Props {
  isLoading: boolean;
  error: Error | null;
  collections: Collection[] | undefined;
  selectedCol: Collection | undefined;
  setCollection: React.Dispatch<React.SetStateAction<Collection | undefined>>;
}

export default function CollectionOptions({
  isLoading,
  error,
  collections,
  selectedCol,
  setCollection,
}: Props) {
  const [selected, setSelected] = useState<Collection>();
  const [alreadySelected, setAlreadySelected] = useState<
    Collection | undefined
  >(selectedCol);

  if (isLoading) return <p>Loading...</p>;

  if (error) return <p>An error has occurred: + {error.message}</p>;

  return (
    <div className="space-y-3">
      {collections?.length ? (
        collections?.map((col) => (
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
        ))
      ) : (
        <Link
          to="/collection/create"
          className="text-emerald-700 hover:opacity-80 font-bold"
        >
          Please create a collection first
        </Link>
      )}
    </div>
  );
}

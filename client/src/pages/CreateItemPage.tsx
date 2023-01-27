import CreateItem from 'components/CreateItem/CreateItem';
import ItemImage from 'components/CreateItem/ItemImage';
import { useState } from 'react';

interface Collection {
  collectionName: string;
  collectionId: number;
  logoImgName: string;
}

export default function CreateItemPage() {
  const [itemFile, setItemFile] = useState<File | null>(null);
  const [itemString, setItemString] = useState<string>('');
  const [itemName, setItemName] = useState<string>('');
  const [selectedCol, setSelectedCol] = useState<Collection>();

  return (
    <div className="max-w-2xl mx-auto p-6">
      <div className="flex flex-col items-center space-y-10">
        <h1 className="text-5xl font-bold">Create New Item</h1>
        <span className="ml-auto text-sm">
          <span className="text-red-500 font-bold align-top">*</span> Required
          fields{' '}
        </span>

        <ItemImage
          itemFile={itemFile}
          setItemFile={setItemFile}
          itemString={itemString}
          setItemString={setItemString}
          setItemName={setItemName}
        />
        <CreateItem
          itemFile={itemFile}
          itemName={itemName}
          selectedCol={selectedCol}
          setSelectedCol={setSelectedCol}
        />
      </div>
    </div>
  );
}

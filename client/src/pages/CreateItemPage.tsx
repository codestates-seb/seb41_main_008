import CreateItem from 'components/CreateItem/CreateItem';
import ItemImage from 'components/CreateItem/ItemImage';
import { useState } from 'react';

export default function CreateItemPage() {
  const [itemFile, setItemFile] = useState<File | null>(null);
  const [itemString, setItemString] = useState<string>('');

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
        />
        <CreateItem itemFile={itemFile} />
      </div>
    </div>
  );
}

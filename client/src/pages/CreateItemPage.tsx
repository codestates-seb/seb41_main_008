import CreateItem, { Collection } from 'components/CreateItem/CreateItem';
import ItemImage from 'components/CreateItem/ItemImage';
import Header from 'components/Header/Header';
import { useAppSelector } from 'hooks/hooks';
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

export default function CreateItemPage() {
  const [itemFile, setItemFile] = useState<File | null>(null);
  const [itemString, setItemString] = useState<string>('');
  const [itemName, setItemName] = useState<string>('');
  const [selectedCol, setSelectedCol] = useState<Collection>();
  const { isLogin } = useAppSelector((state) => state.login);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요합니다');
      navigate('/login');
    }
    return;
  }, [isLogin, navigate]);

  return (
    <>
      <Header />
      <div className="mt-20 max-w-2xl mx-auto p-6">
        <div className="flex flex-col items-center space-y-10">
          <h1 className="text-5xl font-bold">Create New Item</h1>
          <Link
            to="/collection/create"
            className="text-emerald-700 hover:opacity-80 text-lg font-bold"
          >
            Please create a collection before you create your own NFT
          </Link>
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
    </>
  );
}

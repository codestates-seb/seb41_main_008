/* eslint-disable */
import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { BsCheckCircleFill } from 'react-icons/bs';
import { HiOutlineStar, HiShare } from 'react-icons/hi';
import * as Toast from '@radix-ui/react-toast';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setOpen } from 'store/toastSlice';
import Card from 'components/Card';
import MissingPage from 'components/MissingPage/MissingPage';

interface Item {
  itemDescription: string;
  itemId: number;
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  onSale: boolean;
  ownerId: number;
  ownerName: string;
}

interface Collection {
  bannerImgName: string;
  logoImgName: string;
  coinId: number;
  coinName: string;
  collectionId: number;
  collectionName: string;
  ownerId: number;
  ownerName: string;
  ownerCount: number;
  createdDate: string;
  description: string;
  highestPrice: number;
  lowestPrice: number;
  totalVolume: number;
  itemList: Item[];
  itemCount: number;
}

export default function CollectionDetails() {
  const { id } = useParams();
  const open = useAppSelector((state) => state.toast.open);
  const dispatch = useAppDispatch();
  const [collection, setCollection] = useState<Collection>();

  useEffect(() => {
    setTimeout(() => dispatch(setOpen(false)), 3000);
  }, [dispatch]);

  useEffect(() => {
    const getCollection = async () => {
      try {
        const res = await customAxios.get(`/api/collections/${id}`);

        setCollection(res.data);
        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getCollection();
  }, [id]);
  console.log(collection?.itemList);
  return (
    <>
      {collection?.totalVolume ? (
        <div className="space-y-16">
          <section className="flex flex-col w-full">
            <div className="h-64 relative">
              <span className="absolute top-0 left-0 bottom-0 right-0">
                <img
                  className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full"
                  src={`${process.env.REACT_APP_IMAGE}${collection?.bannerImgName}`}
                  alt="Collection banner"
                />
              </span>
            </div>
            <div className="absolute mt-32 ml-6 w-[160px] h-[160px]">
              <img
                className="w-full h-full object-cover rounded-full border-8 border-[#ffffff]"
                alt="Collection logo"
                src={`${process.env.REACT_APP_IMAGE}${collection?.logoImgName}`}
              />
            </div>
          </section>

          <section className="px-8 space-y-3 text-[#04111D]">
            <div className="flex justify-between items-center">
              <h1 className="text-4xl font-bold">
                {collection?.collectionName}
              </h1>
              <div className="space-x-3 flex">
                <button className="shadowBtn">
                  <HiOutlineStar className="h-6 w-6" />
                </button>
                <button className="shadowBtn">
                  <HiShare className="h-6 w-6" />
                </button>
              </div>
            </div>

            <h3 className="text-lg">
              By <span className="font-semibold">{collection?.ownerName}</span>
            </h3>
            <div>
              <span className="text-lg">
                Items{' '}
                <span className="font-semibold">{collection?.itemCount}</span>
              </span>
              <span className="font-bold">{' · '}</span>
              <span className="text-lg">
                Created{' '}
                <span className="font-semibold">{collection?.createdDate}</span>
              </span>
              <span className="font-bold">{' · '}</span>
              <span className="text-lg">
                Chain{' '}
                <span className="font-semibold">{collection?.coinName}</span>{' '}
              </span>
            </div>
            <p className="text-lg">{collection?.description}</p>

            <div className="flex space-x-5">
              <div className="text-center">
                <div className="font-semibold text-2xl">
                  {collection?.totalVolume} ETH
                </div>
                <div className="text">total volume</div>
              </div>
              <div className="text-center">
                <div className="font-semibold text-2xl">
                  {collection?.lowestPrice} ETH
                </div>
                <div className="text">floor price</div>
              </div>
              <div className="text-center">
                <div className="font-semibold text-2xl">
                  {collection?.ownerCount}
                </div>
                <div className="text">owners</div>
              </div>
            </div>
          </section>

          <section className="px-8 grid grid-cols-1 gap-y-10 gap-x-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8">
            {collection?.itemList.map((item) => (
              <Card
                onSale={item.onSale}
                key={item.itemId}
                itemId={item.itemId}
                data={collection.itemList}
                collectionName={collection.collectionName}
                logoImgName={collection.logoImgName}
                itemImageName={item.itemImageName}
                itemPrice={item.itemPrice}
                itemDescription={item.itemDescription}
                coinName={collection.coinName}
                filter="collected"
              />
            ))}
          </section>
          <Toast.Provider>
            <Toast.Root
              open={open}
              onOpenChange={setOpen}
              className="ToastRoot"
            >
              <Toast.Description className="ToastDescription">
                <p className="flex items-center gap-1 text-emerald-700">
                  <span>
                    <BsCheckCircleFill className="h-7 w-7" />
                  </span>{' '}
                  Created!
                </p>
              </Toast.Description>
            </Toast.Root>

            <Toast.Viewport className="ToastViewport" />
          </Toast.Provider>
        </div>
      ) : (
        <MissingPage />
      )}
    </>
  );
}

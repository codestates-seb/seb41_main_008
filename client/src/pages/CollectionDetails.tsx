import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { BsCheckCircleFill } from 'react-icons/bs';
import { HiOutlineStar, HiShare } from 'react-icons/hi';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setCreateColOpen } from 'store/toastSlice';
import { format } from 'date-fns';
import Cards from 'components/MyCollection/Cards';
import MissingPage from 'pages/MissingPage';
import { useQuery } from '@tanstack/react-query';
import Notification from 'components/Notification';

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

  const dispatch = useAppDispatch();
  const createColOpen = useAppSelector((state) => state.toast.createColOpen);

  useEffect(() => {
    setTimeout(() => dispatch(setCreateColOpen(false)), 5000);
  }, [dispatch]);

  const { isLoading, error, data } = useQuery<Collection>({
    queryKey: ['collections', 'only', id],
    queryFn: () =>
      customAxios.get(`/api/collections/only/${id}`).then((res) => res.data),
  });

  if (isLoading) return <p>Loading...</p>;

  if (error) return <MissingPage />;

  return (
    <>
      <div className="space-y-16">
        <section className="flex flex-col w-full">
          <div className="h-64 relative">
            <span className="absolute top-0 left-0 bottom-0 right-0">
              <img
                className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full"
                src={`${process.env.REACT_APP_IMAGE}${data?.bannerImgName}`}
                alt="Collection banner"
              />
            </span>
          </div>
          <div className="absolute mt-32 ml-6 w-[160px] h-[160px]">
            <img
              className="w-full h-full object-cover rounded-full border-8 border-[#ffffff]"
              alt="Collection logo"
              src={`${process.env.REACT_APP_IMAGE}${data?.logoImgName}`}
            />
          </div>
        </section>

        <section className="px-8 space-y-3 text-[#04111D]">
          <div className="flex justify-between items-center">
            <h1 className="text-4xl font-bold">{data?.collectionName}</h1>
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
            By <span className="font-semibold">{data?.ownerName}</span>
          </h3>
          <div>
            <span className="text-lg">
              Items <span className="font-semibold">{data?.itemCount}</span>
            </span>
            <span className="font-bold">{' · '}</span>
            <span className="text-lg">
              Created{' '}
              <span className="font-semibold">
                {format(new Date(data?.createdDate!), 'MMM yyyy')}
              </span>
            </span>
            <span className="font-bold">{' · '}</span>
            <span className="text-lg">
              Chain <span className="font-semibold">{data?.coinName}</span>{' '}
            </span>
          </div>
          <p className="text-lg">{data?.description}</p>

          <div className="flex space-x-5">
            <div className="text-center">
              <div className="font-semibold text-2xl">
                {data?.totalVolume} ETH
              </div>
              <div className="text">total volume</div>
            </div>
            <div className="text-center">
              <div className="font-semibold text-2xl">
                {data?.lowestPrice} ETH
              </div>
              <div className="text">floor price</div>
            </div>
            <div className="text-center">
              <div className="font-semibold text-2xl">{data?.ownerCount}</div>
              <div className="text">owners</div>
            </div>
          </div>
        </section>
        {data?.itemCount ? (
          <Cards id={id!} />
        ) : (
          <section className="border text-[#04111D] flex justify-center items-center border-gray-300 mx-8 h-64 rounded-lg">
            <h2 className="text-3xl">No items to display</h2>
          </section>
        )}
        <Notification open={createColOpen} setOpen={setCreateColOpen}>
          <p className="flex items-center gap-1 text-emerald-700">
            <span>
              <BsCheckCircleFill className="h-7 w-7" />
            </span>{' '}
            Created!
          </p>
        </Notification>
      </div>
    </>
  );
}

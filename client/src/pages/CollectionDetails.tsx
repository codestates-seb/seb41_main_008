import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { BsCheckCircleFill } from 'react-icons/bs';
import { HiOutlineStar, HiShare } from 'react-icons/hi';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setCreateColOpen } from 'store/toastSlice';
import { format } from 'date-fns';
import MissingPage from 'pages/MissingPage';
import { useQuery, useInfiniteQuery } from '@tanstack/react-query';
import Notification from 'components/Notification';
import Header from 'components/Header/Header';
import Card from 'components/Card';
import { useInView } from 'react-intersection-observer';
import customAxios from 'utils/api/axios';
import axios from 'axios';
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
  const { ref, inView } = useInView();
  const dispatch = useAppDispatch();
  const createColOpen = useAppSelector((state) => state.toast.createColOpen);
  useEffect(() => {
    setTimeout(() => dispatch(setCreateColOpen(false)), 5000);
  }, [dispatch]);

  const { isLoading, error, data } = useQuery<Collection>({
    queryKey: ['collections', 'only', id],
    queryFn: () =>
      axios
        .get(`${process.env.REACT_APP_API_URL}/api/collections/only/${id}`)
        .then((res) => res.data),
  });
  const res: any = useInfiniteQuery({
    queryKey: ['infinite', id],
    queryFn: async ({ pageParam = 1 }) =>
      await customAxios
        .get(`/api/items/collections/${id}?page=${pageParam}&size=12`)
        .then((res) => res.data),
    getNextPageParam: (lastPage, allPages) => {
      console.log('lastPage', lastPage);
      console.log('allPages', allPages);
      return lastPage.data.length ? allPages.length + 1 : undefined;
    },
  });
  console.log('res.data', res.data);
  const isFetchingNextPage = res.isFetchingNextPage;

  useEffect(() => {
    if (inView) res.fetchNextPage();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [inView]);
  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  if (!data) return <MissingPage />;

  return (
    <>
      <Header />
      <div className={`${data?.itemCount === 0 ? 'h-screen' : 'min-h-screen'}`}>
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

        <section className="px-8 space-y-3 text-[#04111D] dark:text-white">
          <div className="h-10 w-full"></div>
          <div className="flex justify-between items-center">
            <h1 className="text-4xl font-bold">{data?.collectionName}</h1>
            <div className="space-x-3 flex dark:text-black">
              <button className="shadowBtn">
                <HiOutlineStar className="h-6 w-6 " />
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
          <p className="text-lg font-serif">{data?.description}</p>

          <div className="flex space-x-5">
            <div className="text-center">
              <div className="font-semibold text-2xl">
                {data?.totalVolume + ' ' + data?.coinName}
              </div>
              <div className="text">total volume</div>
            </div>
            <div className="text-center">
              <div className="font-semibold text-2xl">
                {data?.lowestPrice + ' ' + data?.coinName}
              </div>
              <div className="text">floor price</div>
            </div>
            <div className="text-center">
              <div className="font-semibold text-2xl">{data?.ownerCount}</div>
              <div className="text">owners</div>
            </div>
          </div>
        </section>

        <div className="mt-5 p-6 grid gap-4 grid-cols-6 max-xl:grid-cols-4 max-md:grid-cols-3 max-sm:grid-cols-2 rounded">
          {res.data &&
            res?.data.pages?.map((pages: any) => {
              return pages?.data.map((el: any) => {
                return (
                  <Card
                    key={el.itemId}
                    {...el}
                    data={res.data.pages[0].data}
                    filter={'Collected'}
                  />
                );
              });
            })}
        </div>

        <Notification open={createColOpen} setOpen={setCreateColOpen}>
          <p className="flex items-center gap-1 text-emerald-700">
            <span>
              <BsCheckCircleFill className="h-7 w-7" />
            </span>{' '}
            Created!
          </p>
        </Notification>
      </div>
      <div className="inline-block" ref={ref}>
        {isFetchingNextPage && 'Loading more...'}
      </div>
    </>
  );
}

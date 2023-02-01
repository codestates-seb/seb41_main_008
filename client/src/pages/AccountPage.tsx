import Card from '../components/Card';
import NoCard from '../components/Account/NoCard';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import ProfileDropdown from 'components/Profile/ProfileDropdown';
import Notification from 'components/Notification';
import { BsCheckCircleFill } from 'react-icons/bs';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setUpdateUserOpen } from 'store/toastSlice';
import Header from 'components/Header/Header';
import customAxios from 'utils/api/axios';
import { useQuery } from '@tanstack/react-query';
import MissingPage from './MissingPage';

export interface MemberInfo {
  memberId: number;
  nickname: string;
  bannerImageName: string;
  profileImageName: string;
  description: string;
}

interface UserType {
  member: MemberInfo;
  items: ItemsType[];
  collections: [];
}

interface ItemsType {
  itemDescription: string;
  itemId: number;
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  onSale: boolean;
  ownerId: number;
  ownerName: string;
}
const AccountPage = () => {
  const { memberId }: any = useParams();

  const [filter, setFilter] = useState<string>('Collected');

  const { isLoading, error, data } = useQuery<UserType>({
    queryKey: ['members', memberId],
    queryFn: () =>
      customAxios.get(`/api/members/${memberId}`).then((res) => res.data),
  });

  console.log(data);
  const onFilter = (filterType: string) => {
    setFilter(filterType);
  };

  const myId = window.localStorage.getItem('MEMBER_ID');
  const updateUserOpen = useAppSelector((state) => state.toast.updateUserOpen);
  const dispatch = useAppDispatch();

  useEffect(() => {
    setTimeout(() => dispatch(setUpdateUserOpen(false)), 5000);
  }, [dispatch]);

  if (isLoading) return <p>Loading...</p>;

  if (error) return <MissingPage />;

  return (
    <>
      <Header />
      <div className="flex flex-col w-full dark:bg-[#202225] dark:text-white">
        <div className="h-64 relative ">
          <span className="absolute top-0 left-0 bottom-0 right-0 ">
            <img
              className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full"
              src={data?.member.bannerImageName}
              alt=""
            />
          </span>
        </div>
        <div className="absolute  mt-32 ml-6 w-[160px] h-[160px] ">
          <img
            className="w-full h-full object-cover rounded-full border-8 border-[#ffffff]"
            alt=""
            src={data?.member.profileImageName}
          />
        </div>
        <div className="h-10 w-full"></div>
        <div className="p-10">
          <div className="w-full">
            <div className=" flex justify-between items-center">
              <div className="space-y-2">
                <h1 className="font-bold text-3xl">{data?.member.nickname}</h1>
                <p>{data?.member.description}</p>
              </div>
              {myId === memberId && <ProfileDropdown id={parseInt(myId!)} />}
            </div>
          </div>

          <div className="mt-5">
            <ul className="flex flex-row gap-5 text-lg font-semibold ">
              <button
                onClick={() => onFilter('Collected')}
                className={`${
                  filter === 'Collected' &&
                  'border-b-2 border-black dark:border-white'
                }`}
              >
                Collected
              </button>
              <button
                onClick={() => onFilter('Created')}
                className={`${
                  filter === 'Created' &&
                  'border-b-2 border-black dark:border-white'
                }`}
              >
                Created
              </button>
            </ul>
          </div>
          <div className="mt-5 grid gap-4 grid-cols-8 max-2xl:grid-cols-6 max-xl:grid-cols-4 max-md:grid-cols-3 max-sm:grid-cols-2 rounded">
            {filter === 'Collected' ? (
              data?.items.length !== 0 ? (
                data?.items.map((el: any) => {
                  return (
                    <Card
                      key={el.itemId}
                      data={data.items}
                      filter={filter}
                      {...el}
                    />
                  );
                })
              ) : (
                <NoCard />
              )
            ) : null}

            {filter === 'Created' ? (
              data?.collections.length !== 0 ? (
                data?.collections.map((el: any, index) => {
                  return <Card key={index} {...el} filter={filter} />;
                })
              ) : (
                <NoCard />
              )
            ) : null}
          </div>
        </div>
        {memberId === myId && (
          <Notification open={updateUserOpen} setOpen={setUpdateUserOpen}>
            <p className="flex items-center gap-1 text-emerald-700">
              <span>
                <BsCheckCircleFill className="h-7 w-7" />
              </span>{' '}
              Updated!
            </p>
          </Notification>
        )}
      </div>
    </>
  );
};
export default AccountPage;

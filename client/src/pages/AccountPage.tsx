import Card from '../components/Card';
import NoCard from '../components/Account/NoCard';
import { useParams } from 'react-router-dom';
import { getUserProFile } from 'utils/api/api';
import { useState, useEffect } from 'react';

interface UserType {
  member: {
    nickname: string;
    bannerImageName: string;
    profileImageName: string;
    description: string;
  };
  items: [];
  collections: [];
}

const AccountPage = () => {
  const { memberId }: any = useParams();
  const [data, setData] = useState<UserType>();
  const [filter, setFilter] = useState<string>('Collected');
  const [collection, setCollections] = useState<any>([]);
  console.log('data', data);
  console.log('filter', filter);
  console.log('collection', collection);
  useEffect(() => {
    getUserProFile(memberId).then((res) => {
      setCollections(res.data?.collections);
      setData(res.data);
    });
  }, [memberId]);

  const onFilter = (filterType: string) => {
    setFilter(filterType);
  };

  return (
    <div className="flex flex-col w-full">
      <div className="h-64 relative ">
        <span className="absolute top-0 left-0 bottom-0 right-0 ">
          <img
            className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full"
            src={
              `${process.env.REACT_APP_IMAGE}` + data?.member.bannerImageName
            }
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
      <div className="h-10 w-full "></div>
      <div className="p-10">
        <div className="w-full">
          <div className="font-bold text-3xl">{data?.member.nickname}</div>
          <div className="flex justify-between">
            <span>{data?.member.description}</span>
            <button className="border-2 p-2">Profile Edit</button>
          </div>
        </div>

        <div className="mt-5">
          <ul className="flex flex-row gap-5 text-lg font-semibold ">
            <button
              onClick={() => onFilter('Collected')}
              className={`${
                filter === 'Collected' && 'border-b-2 border-black'
              }`}
            >
              Collected
            </button>
            <button
              onClick={() => onFilter('Created')}
              className={`${filter === 'Created' && 'border-b-2 border-black'}`}
            >
              Created
            </button>
          </ul>
        </div>

        <div className="mt-5 grid gap-4 grid-cols-6 max-2xl:grid-cols-6 max-xl:grid-cols-4 max-md:grid-cols-3 max-sm:grid-cols-2 rounded">
          {filter === 'Collected' ? (
            data?.items.length !== 0 ? (
              data?.items.map((el: any) => {
                return <Card key={el.collectionId} {...el} filter={filter} />;
              })
            ) : (
              <NoCard />
            )
          ) : null}

          {filter === 'Created' ? (
            data?.collections.length !== 0 ? (
              data?.collections.map((el: any) => {
                return <Card key={el.collectionId} {...el} filter={filter} />;
              })
            ) : (
              <NoCard />
            )
          ) : null}
        </div>
      </div>
    </div>
  );
};
export default AccountPage;

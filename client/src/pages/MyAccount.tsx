import { useQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { IoSettingsSharp } from 'react-icons/io5';
import { Link } from 'react-router-dom';
import customAxios from 'utils/api/axios';

export interface Profile {
  memberId: number;
  nickname: string;
  description: string;
  profileImageName: string;
  bannerImageName: string;
}

export default function MyAccount() {
  const { isLoading, error, data } = useQuery<Profile>({
    queryKey: ['members', 'mypage'],
    queryFn: () =>
      customAxios.get('/api/members/mypage').then((res) => res.data.member),
  });

  const [banner, setBanner] = useState<string>('');
  const [logo, setLogo] = useState<string>('');

  //   useEffect(() => {
  //     if (myProfile) {
  //       setBanner(
  //         myProfile.member?.bannerImageName?.slice(0, 8) === 'https://'
  //           ? myProfile.member?.bannerImageName
  //           : process.env.REACT_APP_IMAGE + myProfile.member?.bannerImageName
  //       );

  //       setLogo(
  //         myProfile.member?.profileImageName?.slice(0, 8) === 'https://'
  //           ? myProfile.member?.profileImageName
  //           : process.env.REACT_APP_IMAGE + myProfile.member?.profileImageName
  //       );
  //     }
  //   }, [myProfile]);
  console.log(data);
  console.log(banner, logo);
  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  return (
    <div className="flex flex-col w-full">
      <div className="h-64 relative ">
        <span className="absolute top-0 left-0 bottom-0 right-0 ">
          <img
            className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full"
            src={
              data?.bannerImageName?.slice(0, 8) === 'https://'
                ? data?.bannerImageName
                : process.env.REACT_APP_IMAGE! + data?.bannerImageName
            }
            alt="Profile banner"
          />
        </span>
      </div>
      <div className="absolute mt-32 ml-6 w-[160px] h-[160px] ">
        <img
          className="w-full h-full object-cover rounded-full border-8 border-[#ffffff]"
          alt="Profile logo"
          src={
            data?.profileImageName?.slice(0, 8) === 'https://'
              ? data?.profileImageName
              : process.env.REACT_APP_IMAGE! + data?.profileImageName
          }
        />
      </div>
      <div className="h-10 w-full"></div>
      <div className="p-10">
        <div className="w-full">
          <div className=" flex justify-between items-center">
            <div className="space-y-2">
              <h1 className="font-bold text-3xl">{data?.nickname}</h1>
              <p>{data?.description}</p>
            </div>
            <Link to={'/account/profile'}>
              <IoSettingsSharp className="h-6 w-6" />
            </Link>
          </div>
        </div>

        {/* <div className="mt-5">
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
        </div> */}
      </div>
    </div>
  );
}

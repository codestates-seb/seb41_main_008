import { useQuery } from '@tanstack/react-query';
import ProfileBanner from 'components/Profile/ProfileBanner';
import ProfileBio from 'components/Profile/ProfileBio';
import ProfileLogo from 'components/Profile/ProfileLogo';
import { useState } from 'react';
import customAxios from 'utils/api/axios';
import { MemberInfo } from './AccountPage';
import Header from 'components/Header/Header';

export default function ProfilePage() {
  const { isLoading, error, data } = useQuery<MemberInfo>({
    queryKey: ['members', 'mypage'],
    queryFn: () =>
      customAxios.get('/api/members/mypage').then((res) => res.data.member),
    onSuccess: (data) => {
      setProfileLogo(data.profileImageName);
      setProfileBanner(data.bannerImageName);
      setNickname(data.nickname);
      setDesc(data.description);
    },
  });

  const [profileLogo, setProfileLogo] = useState<string>('');
  const [profileBanner, setProfileBanner] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [desc, setDesc] = useState<string>('');
  const [logoName, setLogoName] = useState<string>('');
  const [bannerName, setBannerName] = useState<string>('');

  return (
    <>
      <Header />
      <div className="max-w-2xl mx-auto p-6 ">
        <div className="flex flex-col items-center space-y-10">
          <h1 className="text-5xl font-bold">Profile details</h1>
          <span className="ml-auto text-sm">
            <span className="text-red-500 font-bold align-top">*</span> Required
            fields{' '}
          </span>

          {isLoading ? (
            <h5
              className="mt-3
          font-bold text-gray-500"
            >
              Uploading profile images...
            </h5>
          ) : error instanceof Error ? (
            <p className="text-red-500 font-semibold mt-3">
              An error occurred: {error.message}
            </p>
          ) : null}

          <ProfileLogo
            profileLogo={profileLogo}
            setProfileLogo={setProfileLogo}
            setLogoName={setLogoName}
          />
          <ProfileBanner
            profileBanner={profileBanner}
            setProfileBanner={setProfileBanner}
            setBannerName={setBannerName}
          />
          <ProfileBio
            profileImageName={logoName || profileLogo}
            bannerImageName={bannerName || profileBanner}
            id={data?.memberId}
            nickname={nickname}
            description={desc}
          />
        </div>
      </div>
    </>
  );
}

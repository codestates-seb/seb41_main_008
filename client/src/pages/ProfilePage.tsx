import { useQuery } from '@tanstack/react-query';
import ProfileBanner from 'components/Profile/ProfileBanner';
import ProfileBio from 'components/Profile/ProfileBio';
import ProfileLogo from 'components/Profile/ProfileLogo';
import { useEffect, useState } from 'react';
import customAxios from 'utils/api/axios';

interface Profile {
  memberId: number;
  nickname: string;
  description: string;
  profileImageName: string;
  bannerImageName: string;
}

export default function ProfilePage() {
  const { isLoading, error, data } = useQuery<Profile>({
    queryKey: ['profile'],
    queryFn: async () => {
      const res = await customAxios.get('/api/members/mypage');
      return res.data.member;
    },
  });

  const [profileLogo, setProfileLogo] = useState<string>('');
  const [profileBanner, setProfileBanner] = useState<string>('');

  useEffect(() => {
    if (data) {
      setProfileLogo(data.profileImageName);
      setProfileBanner(data.bannerImageName);
    }
  }, [data]);

  const [logoName, setLogoName] = useState<string>('');
  const [bannerName, setBannerName] = useState<string>('');

  return (
    <div className="max-w-2xl mx-auto p-6">
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
          profileImageName={logoName}
          bannerImageName={bannerName}
          id={data?.memberId}
        />
      </div>
    </div>
  );
}

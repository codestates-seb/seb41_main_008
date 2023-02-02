import { useQuery } from '@tanstack/react-query';
import ProfileBanner from 'components/Profile/ProfileBanner';
import ProfileBio from 'components/Profile/ProfileBio';
import ProfileLogo from 'components/Profile/ProfileLogo';
import { useEffect, useState } from 'react';
import customAxios from 'utils/api/axios';
import { UserType } from './AccountPage';
import Header from 'components/Header/Header';
import { useAppSelector } from 'hooks/hooks';
import { useNavigate } from 'react-router-dom';

export default function EditProfile() {
  const { isLogin } = useAppSelector((state) => state.login);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요합니다');
      navigate('/login');
    }
    return;
  }, [isLogin, navigate]);

  const id = window.localStorage.getItem('MEMBER_ID');

  const [profileLogo, setProfileLogo] = useState<string>('');
  const [profileBanner, setProfileBanner] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  const [desc, setDesc] = useState<string>('');
  const [logoName, setLogoName] = useState<string>('');
  const [bannerName, setBannerName] = useState<string>('');

  const { isLoading, error, data } = useQuery<UserType>({
    queryKey: ['members', id],
    queryFn: () =>
      customAxios.get(`/api/members/${id}`).then((res) => res.data),
    onSuccess: (data) => {
      setProfileLogo(data.member.profileImageName);
      setProfileBanner(data.member.bannerImageName);
      setNickname(data.member.nickname);
      setDesc(data.member.description);
    },
  });

  return (
    <>
      <Header />
      <div className="max-w-2xl mx-auto p-6 mt-20">
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
            id={data?.member.memberId}
            nickname={nickname}
            description={desc}
          />
        </div>
      </div>
    </>
  );
}

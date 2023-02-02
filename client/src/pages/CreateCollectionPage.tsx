import BannerImage from 'components/CreateCollection/BannerImage';
import CreateCollection from 'components/CreateCollection/CreateCollection';
import LogoImage from 'components/CreateCollection/LogoImage';
import Header from 'components/Header/Header';
import { useAppSelector } from 'hooks/hooks';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface Blockchain {
  name: string;
  id: number;
}

export default function CreateCollectionPage() {
  const [logoFile, setLogoFile] = useState<File | null>(null);
  const [bannerFile, setBannerFile] = useState<File | null>(null);
  const [logoString, setLogoString] = useState<string>('');
  const [bannerString, setBannerString] = useState<string>('');
  const [logoName, setLogoName] = useState<string>('');
  const [bannerName, setBannerName] = useState<string>('');
  const [selectedCoin, setSelectedCoin] = useState<Blockchain | null>(null);
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
          <h1 className="text-5xl font-bold">Create a Collection</h1>
          <span className="ml-auto text-sm">
            <span className="text-red-500 font-bold align-top">*</span> Required
            fields{' '}
          </span>
          <LogoImage
            logoFile={logoFile}
            setLogoFile={setLogoFile}
            logoString={logoString}
            setLogoString={setLogoString}
            setLogoName={setLogoName}
          />
          <BannerImage
            bannerFile={bannerFile}
            setBannerFile={setBannerFile}
            bannerString={bannerString}
            setBannerString={setBannerString}
            setBannerName={setBannerName}
          />
          <CreateCollection
            selectedCoin={selectedCoin}
            setSelectedCoin={setSelectedCoin}
            logoFile={logoFile}
            bannerFile={bannerFile}
            logoName={logoName}
            bannerName={bannerName}
          />
        </div>
      </div>
    </>
  );
}

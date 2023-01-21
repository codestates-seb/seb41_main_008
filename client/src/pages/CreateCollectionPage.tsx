import BannerImage from 'components/CreateCollection/BannerImage';
import CreateCollection from 'components/CreateCollection/CreateCollection';
import LogoImage from 'components/CreateCollection/LogoImage';
import { useState } from 'react';

interface Blockchain {
  name: string;
  id: number;
}

export default function CreateCollectionPage() {
  const [logoFile, setLogoFile] = useState<File | null>(null);
  const [bannerFile, setBannerFile] = useState<File | null>(null);
  const [logoString, setLogoString] = useState<string>('');
  const [bannerString, setBannerString] = useState<string>('');
  const [selectedCoin, setSelectedCoin] = useState<Blockchain | null>(null);

  return (
    <div className="max-w-2xl mx-auto p-6">
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
        />
        <BannerImage
          bannerFile={bannerFile}
          setBannerFile={setBannerFile}
          bannerString={bannerString}
          setBannerString={setBannerString}
        />
        <CreateCollection
          selectedCoin={selectedCoin}
          setSelectedCoin={setSelectedCoin}
          logoFile={logoFile}
          bannerFile={bannerFile}
        />
      </div>
    </div>
  );
}

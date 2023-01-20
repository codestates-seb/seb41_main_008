import BannerImage from 'components/CreateCollection/BannerImage';
import CreateCollection from 'components/CreateCollection/CreateCollection';
import LogoImage from 'components/CreateCollection/LogoImage';
import Select from 'components/Select/Select';
import { useState } from 'react';

interface Option {
  label: string;
  id: number;
}

export default function CreateCollectionPage() {
  const options = [
    { label: 'Solana', id: 1 },
    { label: 'Bitcoin', id: 2 },
    { label: 'Dogecoin', id: 3 },
    { label: 'Ethereum', id: 4 },
    { label: 'Ethereum Classic', id: 5 },
  ];

  const [logoFile, setLogoFile] = useState<File | null>(null);
  const [bannerFile, setBannerFile] = useState<File | null>(null);
  const [logoString, setLogoString] = useState<string>('');
  const [bannerString, setBannerString] = useState<string>('');
  const [value, setValue] = useState<Option>(options[0]);

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
        <Select options={options} value={value} setValue={setValue} />
        <CreateCollection logoFile={logoFile} bannerFile={bannerFile} />
      </div>
    </div>
  );
}

import BannerImage from 'components/CreateCollection/BannerImage';
import Input from 'components/CreateCollection/Input';
import LogoImage from 'components/CreateCollection/LogoImage';
import { useState } from 'react';

export default function CreateCollectionPage() {
  const [logoFile, setLogoFile] = useState<File | null>(null);
  const [bannerFile, setBannerFile] = useState<File | null>(null);

  return (
    <div className="max-w-2xl mx-auto p-6 flex justify-center">
      <div className="flex flex-col items-center space-y-10">
        <h1 className="text-5xl font-bold">Create a Collection</h1>
        <span className="ml-auto text-sm">
          <span className="text-red-500 font-bold align-top">*</span> Required
          fields{' '}
        </span>
        <LogoImage logoFile={logoFile} setLogoFile={setLogoFile} />
        <BannerImage bannerFile={bannerFile} setBannerFile={setBannerFile} />
        <Input logoFile={logoFile} bannerFile={bannerFile} />
      </div>
    </div>
  );
}

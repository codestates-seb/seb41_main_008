import BannerImage from 'components/CreateCollection/BannerImage';
import Input from 'components/CreateCollection/Input';
import LogoImage from 'components/CreateCollection/LogoImage';

export default function CreateCollectionPage() {
  return (
    <div className="max-w-2xl mx-auto p-6 flex justify-center">
      <div className="flex flex-col items-center space-y-10">
        <h1 className="text-5xl font-bold">Create a Collection</h1>
        <span className="ml-auto text-sm">
          <span className="text-red-500 font-bold align-top">*</span> Required
          fields{' '}
        </span>
        <LogoImage />
        <BannerImage />
        <Input />
      </div>
    </div>
  );
}

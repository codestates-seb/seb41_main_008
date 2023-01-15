import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { ChangeEvent, useEffect, useRef } from 'react';
import { BsImage } from 'react-icons/bs';
import { setLogoString } from 'store/logoSlice';

interface LogoFile {
  logoFile: File | null;
  setLogoFile: React.Dispatch<React.SetStateAction<File | null>>;
}

export default function LogoImage({ logoFile, setLogoFile }: LogoFile) {
  const dispatch = useAppDispatch();

  const logoString = useAppSelector((state) => state.logo.logoString);

  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file && file.type.substring(0, 5) === 'image') {
      setLogoFile(file);
    } else {
      setLogoFile(null);
    }
  };

  useEffect(() => {
    if (logoFile) {
      const reader = new FileReader();
      reader.readAsDataURL(logoFile);
      reader.onloadend = () => {
        dispatch(setLogoString(reader.result as string));
      };
    } else {
      dispatch(setLogoString(''));
    }
  }, [logoFile, dispatch]);

  return (
    <form className="flex flex-col items-center">
      <h3 className="font-bold text-lg">
        Logo image{' '}
        <span className="text-red-500 text-xl font-bold align-top">*</span>
      </h3>
      <p className="text-lg text-center">
        This image will also be used for navigation.{' '}
      </p>
      <input
        type="file"
        className="hidden"
        ref={fileInputRef}
        accept="image/*"
        onChange={handleChange}
      />
      {logoString ? (
        <img
          src={logoString}
          alt="logo"
          role="presentation"
          className="h-44 w-44 rounded-full object-cover mt-3 cursor-pointer"
          onClick={() => setLogoFile(null)}
        />
      ) : (
        <button
          onClick={(e) => {
            e.preventDefault();
            fileInputRef.current?.click();
          }}
          className="group relative border-2 border-gray-400 border-dashed rounded-full mt-3 w-44 h-44"
        >
          <BsImage className="h-20 w-20 text-gray-400  absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2" />
          <div className="rounded-full bg-black/60 w-[calc(100%-0.5rem)] h-[calc(100%-0.5rem)] absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 hidden group-hover:block" />
        </button>
      )}
    </form>
  );
}

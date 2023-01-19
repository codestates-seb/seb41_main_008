import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { BsImage } from 'react-icons/bs';
import { setBannerString } from 'store/bannerSlice';

interface BannerFile {
  bannerFile: File | null;
  setBannerFile: React.Dispatch<React.SetStateAction<File | null>>;
}

export default function BannerImage({ bannerFile, setBannerFile }: BannerFile) {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [bannerTypeError, setBannerTypeError] = useState(false);
  const [bannerSizeError, setBannerSizeError] = useState(false);

  const dispatch = useAppDispatch();
  const bannerString = useAppSelector((state) => state.banner.bannerString);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file && file.type.substring(0, 5) !== 'image') {
      setBannerTypeError(true);
    } else {
      setBannerTypeError(false);
    }

    if (file && file.size > 30000000) {
      setBannerSizeError(true);
    } else {
      setBannerSizeError(false);
    }

    if (
      file &&
      file.type.substring(0, 5) === 'image' &&
      file.size <= 30000000
    ) {
      setBannerFile(file);
    }

    if (!file) {
      setBannerFile(null);
    }
  };

  useEffect(() => {
    if (bannerFile) {
      const reader = new FileReader();
      reader.readAsDataURL(bannerFile);
      reader.onloadend = () => {
        dispatch(setBannerString(reader.result as string));
      };
    } else {
      dispatch(setBannerString(''));
    }
  }, [bannerFile, dispatch]);

  return (
    <form className="flex flex-col items-center">
      <h3 className="font-bold text-lg">
        Banner image{' '}
        <span className="text-red-500 text-xl font-bold align-top">*</span>
      </h3>
      <p className="text-lg text-center">
        This image will appear at the top of your collection page. Avoid
        including too much text in this banner image, as the dimensions change
        on different devices.
      </p>
      <input
        type="file"
        className="hidden"
        ref={fileInputRef}
        accept="image/*"
        onChange={handleChange}
      />
      {bannerString ? (
        <img
          src={bannerString}
          alt="logo"
          role="presentation"
          className="h-60 w-full rounded-xl object-cover mt-3 cursor-pointer"
          onClick={() => setBannerFile(null)}
        />
      ) : (
        <button
          onClick={(e) => {
            e.preventDefault();
            fileInputRef.current?.click();
          }}
          className="group relative border-2 border-gray-400 border-dashed mt-3 w-full h-60 rounded-xl"
        >
          <BsImage className="h-20 w-20 text-gray-400  absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2" />
          <div className="rounded-xl bg-black/60 w-[calc(100%-0.5rem)] h-[calc(100%-0.5rem)] absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 hidden group-hover:block" />
        </button>
      )}
      {bannerTypeError && (
        <div className="mt-3 text-center">
          <h5 className="font-bold text-gray-500">Unsupported file type</h5>
          <p className="text-red-500 font-semibold">
            File type must be image/*
          </p>
        </div>
      )}
      {bannerSizeError && (
        <div className="mt-2 text-center">
          <h5 className="font-bold text-gray-500">File too large</h5>
          <p className="text-red-500 font-semibold">
            File is larger than 30,000,000 bytes
          </p>
        </div>
      )}
    </form>
  );
}

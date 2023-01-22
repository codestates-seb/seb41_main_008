import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { BsImage } from 'react-icons/bs';

interface Logo {
  logoFile: File | null;
  setLogoFile: React.Dispatch<React.SetStateAction<File | null>>;
  logoString: string;
  setLogoString: React.Dispatch<React.SetStateAction<string>>;
}

export default function LogoImage({
  logoFile,
  setLogoFile,
  logoString,
  setLogoString,
}: Logo) {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [logoTypeError, setLogoTypeError] = useState(false);
  const [logoSizeError, setLogoSizeError] = useState(false);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file && file.type.substring(0, 5) !== 'image') {
      setLogoTypeError(true);
    } else {
      setLogoTypeError(false);
    }

    if (file && file.size > 30000000) {
      setLogoSizeError(true);
    } else {
      setLogoSizeError(false);
    }

    if (
      file &&
      file.type.substring(0, 5) === 'image' &&
      file.size <= 30000000
    ) {
      setLogoFile(file);
    }

    if (!file) {
      setLogoFile(null);
    }
  };

  useEffect(() => {
    if (logoFile) {
      const reader = new FileReader();
      reader.readAsDataURL(logoFile);
      reader.onloadend = () => {
        setLogoString(reader.result as string);
      };
    } else {
      setLogoString('');
    }
  }, [logoFile, setLogoString]);

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
      {logoTypeError && (
        <div className="mt-3 text-center">
          <h5 className="font-bold text-gray-500">Unsupported file type</h5>
          <p className="text-red-500 font-semibold">
            File type must be image/*
          </p>
        </div>
      )}
      {logoSizeError && (
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

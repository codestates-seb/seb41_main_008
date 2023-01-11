import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { BsImage } from 'react-icons/bs';

export default function BannerImage() {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [image, setImage] = useState<File | null>(null);
  const [preview, setPreview] = useState<string | null>(null);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file && file.type.substring(0, 5) === 'image') {
      setImage(file);
    } else {
      setImage(null);
    }
  };

  useEffect(() => {
    if (image) {
      const reader = new FileReader();
      reader.readAsDataURL(image);
      reader.onloadend = () => {
        setPreview(reader.result as string);
      };
    } else {
      setPreview(null);
    }
  }, [image]);

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
      {preview ? (
        <img
          src={preview}
          alt="logo"
          role="presentation"
          className="h-60 w-full rounded-xl object-cover mt-3 cursor-pointer"
          onClick={() => setImage(null)}
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
    </form>
  );
}

import { useMutation, useQueryClient } from '@tanstack/react-query';
import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import customAxios from 'utils/api/axios';

interface Banner {
  profileBanner: string;
  setBannerName: React.Dispatch<React.SetStateAction<string>>;
  setProfileBanner: React.Dispatch<React.SetStateAction<string>>;
}

export default function ProfileBanner({
  profileBanner,
  setProfileBanner,
  setBannerName,
}: Banner) {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [bannerFile, setBannerFile] = useState<File | undefined>();
  const [bannerTypeError, setBannerTypeError] = useState(false);
  const [bannerSizeError, setBannerSizeError] = useState(false);

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
  };

  const queryClient = useQueryClient();

  const { mutate, isLoading, error } = useMutation({
    mutationFn: (file: FormData) =>
      customAxios
        .post(`${process.env.REACT_APP_API_URL}/images`, file)
        .then((res) => res.data),
    onSuccess: (data) => {
      queryClient.invalidateQueries(['images'], { exact: true });
      setBannerName(data.imageName);
    },
  });

  useEffect(() => {
    if (bannerFile) {
      const formData = new FormData();
      formData.append('file', bannerFile);

      mutate(formData);

      const reader = new FileReader();
      reader.readAsDataURL(bannerFile);
      reader.onloadend = () => {
        setProfileBanner(reader.result as string);
      };
    }
  }, [bannerFile, setProfileBanner, mutate]);

  return (
    <form className="flex flex-col items-center w-full">
      <h3 className="font-bold text-lg">
        Banner image{' '}
        <span className="text-red-500 text-xl font-bold align-top">*</span>
      </h3>

      <input
        type="file"
        className="hidden"
        ref={fileInputRef}
        accept="image/*"
        onChange={handleChange}
      />

      <div className="group relative mt-3 w-full h-60 rounded-xl cursor-pointer">
        <img
          src={
            profileBanner?.slice(0, 8) === 'https://' ||
            profileBanner?.slice(0, 4) === 'data'
              ? profileBanner
              : process.env.REACT_APP_IMAGE + profileBanner
          }
          alt="Profile banner"
          role="presentation"
          className="h-full w-full rounded-xl object-cover cursor-pointer absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2"
          onClick={(e) => {
            e.preventDefault();
            fileInputRef.current?.click();
          }}
        />
        <div className="pointer-events-none rounded-xl bg-black/60 w-full h-full absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 hidden group-hover:block" />
      </div>

      {isLoading ? (
        <h5
          className="mt-3
        font-bold text-gray-500"
        >
          Uploading a profile banneer...
        </h5>
      ) : error instanceof Error ? (
        <p className="text-red-500 font-semibold mt-3">
          An error occurred: {error.message}
        </p>
      ) : null}
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

import { useMutation, useQueryClient } from '@tanstack/react-query';
import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import customAxios from 'utils/api/axios';

interface Logo {
  profileLogo: string;
  setLogoName: React.Dispatch<React.SetStateAction<string>>;
  setProfileLogo: React.Dispatch<React.SetStateAction<string>>;
}

export default function ProfileLogo({
  profileLogo,
  setProfileLogo,
  setLogoName,
}: Logo) {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [logoFile, setLogoFile] = useState<File | undefined>();
  const [logoTypeError, setLogoTypeError] = useState(false);
  const [logoSizeError, setLogoSizeError] = useState(false);
  console.log(profileLogo);
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
  };

  const queryClient = useQueryClient();

  const { mutate, isLoading, error } = useMutation({
    mutationFn: (file: FormData) =>
      customAxios
        .post(`${process.env.REACT_APP_API_URL}/images`, file)
        .then((res) => res.data),
    onSuccess: (data) => {
      queryClient.invalidateQueries(['images'], { exact: true });
      setLogoName(data.imageName);
    },
  });

  useEffect(() => {
    if (logoFile) {
      const formData = new FormData();
      formData.append('file', logoFile);

      mutate(formData);

      const reader = new FileReader();
      reader.readAsDataURL(logoFile);
      reader.onloadend = () => {
        setProfileLogo(reader.result as string);
      };
    }
  }, [logoFile, setProfileLogo, mutate]);

  return (
    <form className="flex flex-col items-center">
      <h3 className="font-bold text-lg">
        Logo image{' '}
        <span className="text-red-500 text-xl font-bold align-top">*</span>
      </h3>

      <input
        type="file"
        className="hidden"
        ref={fileInputRef}
        accept="image/*"
        onChange={handleChange}
      />

      <div className="relative group cursor-pointer rounded-full mt-3 w-44 h-44">
        <img
          src={
            profileLogo?.slice(0, 8) === 'https://' ||
            profileLogo?.slice(0, 4) === 'data'
              ? profileLogo
              : process.env.REACT_APP_IMAGE + profileLogo
          }
          alt="Profile Logo"
          role="presentation"
          className="h-full w-full rounded-full object-cover cursor-pointer absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2"
          onClick={(e) => {
            e.preventDefault();
            fileInputRef.current?.click();
          }}
        />
        <div className="pointer-events-none rounded-full bg-black/60 w-full h-full absolute top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2 hidden group-hover:block" />
      </div>

      {isLoading ? (
        <h5
          className="mt-3
        font-bold text-gray-500"
        >
          Uploading a profile logo...
        </h5>
      ) : error instanceof Error ? (
        <p className="text-red-500 font-semibold mt-3">
          An error occurred: {error.message}
        </p>
      ) : null}
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

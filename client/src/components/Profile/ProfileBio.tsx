import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { RxCross2 } from 'react-icons/rx';
import { useAppDispatch } from 'hooks/hooks';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { setOpen } from 'store/toastSlice';
import { useMutation, useQueryClient } from '@tanstack/react-query';

interface Bio {
  nickname: string;
  bio: string;
}

interface Props {
  id: number | undefined;
  profileImageName: string;
  bannerImageName: string;
}

type ProfileInfo = Omit<Props, 'id'> & Bio;

const schema = yup.object({
  nickname: yup.string().required('This field is required.'),
  bio: yup.string().required('This field is required.'),
});

export default function ProfileBio({
  profileImageName,
  bannerImageName,
  id,
}: Props) {
  const dispatch = useAppDispatch();
  const [nicknameFocus, setNicknameFocus] = useState(false);
  const [bioFocus, setBioFocus] = useState(false);
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Bio>({
    resolver: yupResolver(schema),
  });

  const queryClient = useQueryClient();
  const { mutate, isLoading, error } = useMutation({
    mutationFn: async (newProfile: ProfileInfo) => {
      const res = await customAxios.patch(`/api/members/${id}`, newProfile);
      return res.data;
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries(['profile']);
      navigate(`/account/${id}`);
    },
  });

  const onSubmit = async (data: Bio) => {
    dispatch(setOpen(true));

    mutate({
      nickname: data.nickname,
      bio: data.bio,
      profileImageName,
      bannerImageName,
    });
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="text-center w-full space-y-10"
    >
      <div className="space-y-3">
        <label htmlFor="name" className="mx-auto font-bold text-lg">
          Name{' '}
          <span className="text-red-500 text-xl font-bold align-top">*</span>
        </label>
        <div
          className={`border-2 duration-300 rounded-lg ${
            errors.nickname
              ? 'border-red-600'
              : nicknameFocus
              ? 'border-focused'
              : 'border-gray-300'
          }
          `}
        >
          <input
            type="text"
            {...register('nickname')}
            placeholder="Enter username"
            className="w-full rounded-lg p-3 text-lg group outline-none h-full"
            onFocus={() => setNicknameFocus(true)}
            onBlur={() => setNicknameFocus(false)}
          />
        </div>
        {errors.nickname && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.nickname.message}</span>
          </p>
        )}
      </div>

      <div className="space-y-3">
        <label htmlFor="bio" className="mx-auto font-bold text-lg">
          Bio{' '}
          <span className="text-red-500 text-xl font-bold align-top">*</span>
        </label>
        <div
          className={`border-2 rounded-lg duration-300 ${
            errors.bio
              ? 'border-red-600'
              : bioFocus
              ? 'border-focused'
              : 'border-gray-300'
          }`}
        >
          <textarea
            {...register('bio')}
            className="w-full overflow-hidden -mb-1 h-52 min-h-[52px] outline-none p-3 rounded-lg text-lg"
            onFocus={() => setBioFocus(true)}
            onBlur={() => setBioFocus(false)}
            placeholder="Tell the world your story!"
          />
        </div>
        {errors.bio && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.bio.message}</span>
          </p>
        )}
      </div>

      <input
        type="submit"
        className="bg-emerald-700 hover:opacity-90 cursor-pointer font-bold text-white rounded-lg px-5 py-3 text-lg"
        value="Save"
      />
      {isLoading ? (
        <h5
          className="
        font-bold text-gray-500"
        >
          Updating your profile...
        </h5>
      ) : error instanceof Error ? (
        <p className="text-red-500 font-semibold mt-3">
          An error occurred: {error.message}
        </p>
      ) : null}
    </form>
  );
}

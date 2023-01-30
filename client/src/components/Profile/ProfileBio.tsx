import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { RxCross2 } from 'react-icons/rx';
import { useAppDispatch } from 'hooks/hooks';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { setUpdateUserOpen } from 'store/toastSlice';
import { useMutation, useQueryClient } from '@tanstack/react-query';

interface Bio {
  nickname: string;
  description: string;
}

interface Props extends Bio {
  id: number | undefined;
  profileImageName: string;
  bannerImageName: string;
}

type ProfileInfo = Omit<Props, 'id'>;

export default function ProfileBio({
  profileImageName,
  bannerImageName,
  id,
  nickname,
  description,
}: Props) {
  const dispatch = useAppDispatch();
  const [nicknameFocus, setNicknameFocus] = useState(false);
  const [descFocus, setDescFocus] = useState(false);
  const navigate = useNavigate();

  const schema = yup.object().shape({
    nickname: yup.string().required('This field is required.'),
    description: yup.string().required('This field is required.'),
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<Bio>({
    defaultValues: {
      nickname,
      description,
    },
    resolver: yupResolver(schema),
  });

  useEffect(() => {
    reset({
      nickname,
      description,
    });
  }, [reset, nickname, description]);

  const queryClient = useQueryClient();
  const { mutate, isLoading, error } = useMutation({
    mutationFn: (newProfile: ProfileInfo) =>
      customAxios.patch(`/api/members/${id}`, newProfile),
    onSuccess: () => {
      queryClient.invalidateQueries(['members', 'mypage']);
      navigate('/account');
    },
  });

  const onSubmit = (data: Bio) => {
    dispatch(setUpdateUserOpen(true));

    mutate({
      nickname: data.nickname,
      description: data.description,
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
        <label htmlFor="nickname" className="mx-auto font-bold text-lg">
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
            defaultValue={nickname}
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
        <label htmlFor="description" className="mx-auto font-bold text-lg">
          Bio{' '}
          <span className="text-red-500 text-xl font-bold align-top">*</span>
        </label>
        <div
          className={`border-2 rounded-lg duration-300 ${
            errors.description
              ? 'border-red-600'
              : descFocus
              ? 'border-focused'
              : 'border-gray-300'
          }`}
        >
          <textarea
            {...register('description')}
            className="w-full overflow-hidden -mb-1 h-52 min-h-[52px] outline-none p-3 rounded-lg text-lg"
            onFocus={() => setDescFocus(true)}
            onBlur={() => setDescFocus(false)}
            placeholder="Tell the world your story!"
            defaultValue={description}
          />
        </div>
        {errors.description && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.description.message}</span>
          </p>
        )}
      </div>

      <input
        type="submit"
        className=" cursor-pointer font-bold BasicButton rounded-lg px-5 py-3 text-lg"
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

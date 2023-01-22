import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { RxCross2 } from 'react-icons/rx';
import { useAppDispatch } from 'hooks/hooks';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';
import customAxios from 'utils/api/axios';
import { setOpen } from 'store/toastSlice';
import Modal from 'components/Dialog/Modal';

interface Inputs {
  name: string;
  description: string;
}

interface Blockchain {
  name: string;
  id: number;
}

interface Collection {
  status: string;
  id: number;
}

interface Props {
  selectedCoin: Blockchain | null;
  setSelectedCoin: React.Dispatch<React.SetStateAction<Blockchain | null>>;
  logoFile: File | null;
  bannerFile: File | null;
  logoName: string;
  bannerName: string;
}

const schema = yup.object({
  name: yup.string().required('This field is required.'),
  description: yup.string().required('This field is required.'),
});

export default function CreateCollection({
  selectedCoin,
  setSelectedCoin,
  logoFile,
  bannerFile,
  logoName,
  bannerName,
}: Props) {
  const dispatch = useAppDispatch();

  const [nameFocus, setNameFocus] = useState(false);
  const [descFocus, setDescFocus] = useState(false);
  const [collection, setCollection] = useState<Collection>();

  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  const onSubmit = async (data: Inputs) => {
    dispatch(setOpen(true));

    if (logoFile && bannerFile) {
      try {
        const res = await customAxios.post('/api/collections', {
          coinId: selectedCoin?.id,
          name: data.name,
          description: data.description,
          logoImgName: logoName,
          bannerImgName: bannerName,
        });

        setCollection(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    }
  };

  useEffect(() => {
    if (collection) {
      navigate(`/collection/${collection.id}`);
    }
  }, [collection, navigate]);

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
            errors.name
              ? 'border-red-600'
              : nameFocus
              ? 'border-focused'
              : 'border-gray-300'
          }
          `}
        >
          <input
            type="text"
            {...register('name')}
            placeholder="Example: Treasures of the Sea"
            className="w-full rounded-lg p-3 text-lg group outline-none h-full"
            onFocus={() => setNameFocus(true)}
            onBlur={() => setNameFocus(false)}
          />
        </div>
        {errors.name && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.name.message}</span>
          </p>
        )}
      </div>

      <div className="space-y-3">
        <label htmlFor="description" className="mx-auto font-bold text-lg">
          Description{' '}
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
          />
        </div>
        {errors.description && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.description.message}</span>
          </p>
        )}
      </div>

      <div className="space-y-10">
        <Modal selectedCoin={selectedCoin} setSelectedCoin={setSelectedCoin} />
        <input
          type="submit"
          className="bg-emerald-700 disabled:cursor-not-allowed disabled:opacity-50 hover:opacity-90 cursor-pointer font-bold text-white rounded-lg px-5 py-3 text-lg"
          value="Create"
          disabled={!logoFile || !bannerFile || !selectedCoin}
        />
      </div>
    </form>
  );
}

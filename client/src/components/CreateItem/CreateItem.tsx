import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { RxCross2 } from 'react-icons/rx';
import { useAppDispatch } from 'hooks/hooks';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { setCreateItemOpen } from 'store/toastSlice';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import ItemModal from './ItemModal';
import {
  Inputs,
  SuccessResponse,
} from 'components/CreateCollection/CreateCollection';

export interface Collection {
  collectionName: string;
  collectionId: number;
  logoImgName: string;
}

interface Profile {
  collections?: Collection[];
}

interface Image {
  itemFile: File | null;
  itemName: string;
  selectedCol: Collection | undefined;
  setSelectedCol: React.Dispatch<React.SetStateAction<Collection | undefined>>;
}

interface ItemInfo {
  itemCollectionId: number | undefined;
  itemName: string;
  itemDescription: string;
  itemImgName: string;
}

const schema = yup.object({
  name: yup.string().required('This field is required.'),
  description: yup.string().required('This field is required.'),
});

export default function CreateItem({
  itemFile,
  itemName,
  selectedCol,
  setSelectedCol,
}: Image) {
  const dispatch = useAppDispatch();
  const [nameFocus, setNameFocus] = useState(false);
  const [descFocus, setDescFocus] = useState(false);
  const [collections, setCollections] = useState<Collection[] | []>([]);
  const [item, setItem] = useState<SuccessResponse>();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  const id = window.localStorage.getItem('MEMBER_ID');

  const { isLoading: loading, error: err } = useQuery<Profile>({
    queryKey: ['members', id],
    queryFn: () =>
      customAxios.get(`/api/members/${id}`).then((res) => res.data),
    onSuccess: (data) => {
      if (data.collections?.length) {
        setCollections(data.collections);
      }
    },
  });

  const queryClient = useQueryClient();
  const { mutate, isLoading, error } = useMutation({
    mutationFn: (item: ItemInfo) =>
      customAxios.post('/api/items', item).then((res) => res.data),
    onSuccess: (data) => {
      queryClient.invalidateQueries(['items'], { exact: true });
      setItem(data);
    },
  });

  const onSubmit = async (data: Inputs) => {
    dispatch(setCreateItemOpen(true));

    if (itemFile) {
      mutate({
        itemCollectionId: selectedCol?.collectionId,
        itemName: data.name,
        itemDescription: data.description,
        itemImgName: itemName,
      });
    }
  };

  useEffect(() => {
    if (item) {
      navigate(`/item/${item.id}`);
    }
  }, [item, navigate]);

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
            placeholder="Item name"
            className="w-full rounded-lg p-3 text-lg group outline-none h-full dark:bg-[#3d3d41]"
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
            className="w-full overflow-hidden -mb-1 h-52 min-h-[52px] outline-none p-3 rounded-lg text-lg dark:bg-[#3d3d41]"
            onFocus={() => setDescFocus(true)}
            onBlur={() => setDescFocus(false)}
            placeholder="Provide a detailed description of your item."
          />
        </div>
        {errors.description && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.description.message}</span>
          </p>
        )}
      </div>

      <ItemModal
        isLoading={loading}
        error={err instanceof Error ? err : null}
        collections={collections}
        selectedCol={selectedCol}
        setSelectedCol={setSelectedCol}
      />

      <input
        type="submit"
        className="bg-emerald-700 disabled:cursor-not-allowed disabled:opacity-50 hover:opacity-90 cursor-pointer font-bold text-white rounded-lg px-5 py-3 text-lg"
        value="Create"
        disabled={!itemFile || !selectedCol}
      />
      {isLoading ? (
        <h5
          className="
        font-bold text-gray-500"
        >
          Creating an item...
        </h5>
      ) : error instanceof Error ? (
        <p className="text-red-500 font-semibold mt-3">
          An error occurred: {error.message}
        </p>
      ) : null}
    </form>
  );
}

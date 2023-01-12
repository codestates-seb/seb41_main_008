import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { RxCross2 } from 'react-icons/rx';
import { BsCheckCircleFill } from 'react-icons/bs';
import { useAppSelector } from 'hooks/hooks';
import * as Toast from '@radix-ui/react-toast';
import { useEffect, useRef, useState } from 'react';
import { HiXCircle } from 'react-icons/hi';

interface Inputs {
  name: string;
  description: string;
}

const schema = yup.object({
  name: yup.string().required('This field is required.'),
  description: yup.string().required('This field is required.'),
});

export default function Input() {
  const logo = useAppSelector((state) => state.logo.url);
  const banner = useAppSelector((state) => state.banner.url);

  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  const timeRef = useRef(0);

  useEffect(() => {
    return () => clearTimeout(timeRef.current);
  }, []);

  const onSubmit = (data: Inputs) => {
    setOpen(false);
    window.clearTimeout(timeRef.current);
    timeRef.current = window.setTimeout(() => setOpen(true), 100);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="text-center w-full space-y-3"
    >
      <div className="space-y-3">
        <label htmlFor="name" className="mx-auto font-bold text-lg">
          Name{' '}
          <span className="text-red-500 text-xl font-bold align-top">*</span>
        </label>
        <div
          className={`border-2 rounded-lg ${
            errors.name ? 'border-red-600' : 'border-gray-300'
          }`}
        >
          <input
            type="text"
            {...register('name')}
            placeholder="Example: Treasures of the Sea"
            className="w-full outline-none rounded-lg p-3 text-lg"
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
          className={`border-2 rounded-lg ${
            errors.description ? 'border-red-600' : 'border-gray-300'
          }`}
        >
          <textarea
            {...register('description')}
            className="w-full outline-none overflow-hidden h-52 min-h-[3rem] rounded-lg p-3 text-lg"
          />
        </div>
        {errors.description && (
          <p className="text-red-600 flex items-center space-x-0.5">
            <RxCross2 className="h-6 w-6" />
            <span>{errors.description.message}</span>
          </p>
        )}
      </div>

      <Toast.Provider>
        <input
          type="submit"
          className="bg-emerald-700 hover:opacity-90 cursor-pointer font-bold text-white rounded-lg px-5 py-3 text-lg"
          value="Create"
        />
        <Toast.Root
          open={open}
          onOpenChange={setOpen}
          className="group ToastRoot"
          duration={3000}
        >
          <Toast.Description className="ToastDescription group-hover:opacity-80">
            {!logo || !banner ? (
              <p className="flex items-center gap-1 text-red-600">
                <span>
                  <HiXCircle className="h-7 w-7" />
                </span>{' '}
                Please select your image.
              </p>
            ) : (
              <p className="flex items-center gap-1 text-emerald-700">
                <span>
                  <BsCheckCircleFill className="h-7 w-7" />
                </span>{' '}
                Created!
              </p>
            )}
          </Toast.Description>
        </Toast.Root>

        <Toast.Viewport className="ToastViewport" />
      </Toast.Provider>
    </form>
  );
}

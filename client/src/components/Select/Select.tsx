/* eslint-disable */

import { useState } from 'react';
import { RxChevronDown } from 'react-icons/rx';

interface Option {
  label: string;
  id: number;
}

interface Select {
  options: Option[];
  value: Option;
  setValue: React.Dispatch<React.SetStateAction<Option>>;
}

export default function Select({ options, value, setValue }: Select) {
  const [open, setOpen] = useState<boolean>(false);

  // const selectOption = (option: Option) => {
  //   setValue(option);
  //   console.log(option);
  //   setOpen(false);
  // };
  console.log(value);

  return (
    <div className="relative w-full">
      <div
        onClick={() => {
          setOpen((prev) => !prev);
        }}
        onBlur={() => setOpen(false)}
        tabIndex={0}
        className="focus:border-focused duration-500 p-3 text-lg rounded-lg border-2 border-gray-300 flex justify-between"
      >
        <span>{value?.label}</span>
        <span>
          <RxChevronDown className="h-6 w-6" />
        </span>
      </div>

      <ul
        className={`${open ? 'block' : 'hidden'} 
          " w-full shadow-md bg-white left-0 top-[calc(100%+4px)] absolute text-lg rounded-lg border-2 border-gray-300"`}
      >
        {options.map((option) => (
          <li
            className="hover:bg-emerald-700 hover:cursor-pointer px-3 py-1.5 hover:text-white"
            key={option.id}
            onClick={() => setValue(option)}
          >
            {option.label}
          </li>
        ))}
      </ul>
    </div>
  );
}

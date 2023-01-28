/* eslint-disable */
import React, { FC } from 'react';
import { IoIosArrowDown } from 'react-icons/io';
import { IoIosArrowUp } from 'react-icons/io';
import { useState } from 'react';
import { useRef } from 'react';

const DayDropdown: FC<{
  setOption: React.Dispatch<React.SetStateAction<string>>;
  onOptionClick: (option: string) => void;
}> = ({ setOption, onOptionClick }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const showDropDown = isExpanded;
  const showDropUP = !isExpanded;
  const options = ['DAY', 'WEEK', 'MONTH'];
  return (
    <div className="relative">
      <button
        onClick={() => setIsExpanded(!isExpanded)}
        className=" flex items-center rounded text-white px-5 py-1 hover:bg-green-400 bg-green-500"
      >
        <div className="mr-1">ALL</div>
        <div>
          {showDropDown && <IoIosArrowUp />}
          {showDropUP && <IoIosArrowDown />}
        </div>
      </button>
      {isExpanded && (
        <div className="absolute bg-white px-1 py-1 rounded border border-gray-400 mt-2 w-30">
          <ul>
            {options.map((time) => (
              <li
                onClick={() => {
                  onOptionClick(time);
                  setOption(time);
                }}
                className="hover:bg-green-200 px-2 py-1"
                key={time}
              >
                {time}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default DayDropdown;

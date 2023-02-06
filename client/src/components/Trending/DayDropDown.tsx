import React, { FC } from 'react';
import Day from '../../assets/day/1D.png';
import Week from '../../assets/day/7D.png';
import Month from '../../assets/day/1M.png';

const CoinFilter: FC<{
  setOption: React.Dispatch<React.SetStateAction<string>>;
  onOptionClick: (option: string) => void;
}> = ({ setOption, onOptionClick }) => {
  const options = [
    { id: 'day', name: Day },
    { id: 'week', name: Week },
    { id: 'month', name: Month },
  ];

  return (
    <div className="">
      <div>
        <div className="">
          <div className="flex space-x-2 ">
            {options.map((time) => (
              <button
                onClick={() => {
                  onOptionClick(time.id);
                  setOption(time.id);
                }}
                className=" dark:bg-[#c2c2c210] hover:animate-bounce hover:shadow-inner rounded-full border border-gray-200 w-10 h-10  px-2 py-1"
                key={time.id}
              >
                <img className="" src={time.name} alt="coin" />
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoinFilter;

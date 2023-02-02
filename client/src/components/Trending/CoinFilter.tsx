import React, { FC } from 'react';
import { useEffect } from 'react';
import BtcLogo from '../../assets/coins/btc.png';
import EthLogo from '../../assets/coins/eth.png';
import EtcLogo from '../../assets/coins/etc.png';
import SolLogo from '../../assets/coins/sol.png';
import DogeLogo from '../../assets/coins/doge.png';

const CoinFilter: FC<{
  setCoin: React.Dispatch<React.SetStateAction<number>>;
  onCoinClick: (coinId: number) => void;
}> = ({ setCoin, onCoinClick }) => {
  const coins = [
    { id: 1, name: SolLogo },
    { id: 2, name: BtcLogo },
    { id: 3, name: DogeLogo },
    { id: 4, name: EthLogo },
    { id: 5, name: EtcLogo },
  ];

  return (
    <div className="">
      <div>
        <div className="">
          <div className="flex space-x-2 ">
            {coins.map((coin) => (
              <button
                onClick={() => {
                  onCoinClick(coin.id);
                  setCoin(coin.id);
                }}
                className=" dark:bg-[#c2c2c210] hover:animate-bounce hover:shadow-inner rounded-full border border-gray-200 w-10 h-10  px-2 py-1"
                key={coin.id}
              >
                <img className="" src={coin.name} alt="coin" />
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoinFilter;

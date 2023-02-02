/* eslint-disable */
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
    { id: 4, name: 'https://seeklogo.com/images/E/ethereum-classic-etc-logo-362F4BB64C-seeklogo.com.png' },
    { id: 5, name: EtcLogo },
  ];

  return (
    <div className="">
      <div>
        <div className="w-7 ">
          <div className="flex">
            {coins.map((coin) => (
              <button 
                onClick={() => {
                  onCoinClick(coin.id);
                  setCoin(coin.id);
                }}
                className="hover:bg-rose-200  w-5 h-5 px-2 py-1"
                key={coin.id}
              >
                <img  className="w-full h-full " src = {coin.name} ></img>
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoinFilter;

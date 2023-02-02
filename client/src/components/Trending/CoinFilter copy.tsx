/* eslint-disable */
import React, { FC } from 'react';
import { useEffect } from 'react';
import BtcLogo from '../../assets/coins/btc.png';
import EthLogo from '../../assets/coins/eth.png';
import EtcLogo from '../../assets/coins/etc.png';
import SolLogo from '../../assets/coins/sol.png';
import DogeLogo from '../../assets/coins/doge.png';

const CoinFilter: FC<{
  setCoin: React.Dispatch<React.SetStateAction<string>>;
  onCoinClick: (coinId: string) => void;
}> = ({ setCoin, onCoinClick }) => {
  const coins = ['1', '2', '3', '4', '5'];

  return (
    <div className="">
      <div>
        <div className="w-7 ">
          <div className="flex">
            {coins.map((coinId) => (
              <button
                onClick={() => {
                  onCoinClick(coinId);
                  setCoin(coinId);
                }}
                className="hover:bg-rose-200 px-2 py-1"
                key={coinId}
              >
                {coinId}
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CoinFilter;

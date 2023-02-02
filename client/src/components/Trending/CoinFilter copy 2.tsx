/* eslint-disable */
import React from 'react';
import { useEffect } from 'react';
import BtcLogo from '../../assets/coins/btc.png';
import EthLogo from '../../assets/coins/eth.png';
import EtcLogo from '../../assets/coins/etc.png';
import SolLogo from '../../assets/coins/sol.png';
import DogeLogo from '../../assets/coins/doge.png';


function CoinFilter() {
  return (
    <div className="inline-flex">
      <button className=" flex items-center shadow-inner hover:shadow-gray-500/50  px-5 py-1 border-y border-gray-300 bg-inherit">
        <div className="w-7 ">
          <img src={BtcLogo} alt="BtcLogo" />
        </div>
      </button>
      <button className=" flex items-center shadow-inner hover:shadow-cyan-500/50  px-5 py-1 border-y border-gray-300 bg-inherit">
        <div className="w-3 ">
          <img src={EthLogo} alt="EthLogo" />
        </div>
      </button>
      <button className=" flex items-center shadow-inner hover:shadow-cyan-500/50 px-5 py-1 border-y border-gray-300 bg-inherit">
        <div className="w-3 ">
          <img src={EtcLogo} alt="EtcLogo" />
        </div>
      </button>
      <button className=" flex items-center  shadow-inner hover:shadow-cyan-500/50 px-5 py-1 border-y border-gray-300 bg-inherit">
        <div className="w-3 ">
          <img src={SolLogo} alt="SolLogo" />
        </div>
      </button>
      <button className=" flex items-center rounded-r-lg shadow-inner hshadow-lg hover:shadow-cyan-500/50 px-5 py-1  border-y border-r border-gray-300 bg-inherit">
        <div className="w-3 ">
          <img src={DogeLogo} alt="DogeLogo" />
        </div>
      </button>
    </div>
  );
}

export default CoinFilter;

/* eslint-disable */
import React from 'react';
import {useEffect} from 'react';


function CoinFilter () {
  return (
    <div className="inline-flex">
      <button className=" flex items-center rounded-l-lg  text-white px-5 py-1 border border-gray-100 hover:bg-rose-200 bg-rose-300">
        <div className="mr-1"> ALL chain</div>
      </button>
      <button className=" flex items-center  text-white px-5 py-1 border border-gray-100 hover:bg-rose-200 bg-rose-300">
        <div className="mr-1">BTC</div>
      </button>
      <button className=" flex items-center  text-white px-5 py-1 border border-gray-100 hover:bg-rose-200 bg-rose-300">
        <div className="mr-1">ETH</div>
      </button>
      <button className=" flex items-center  text-white px-5 py-1 border border-gray-100 hover:bg-rose-200 bg-rose-300">
        <div className="mr-1">SOL</div>
      </button>

      <button className=" flex items-center rounded-r-lg text-white px-5 py-1 border border-gray-100 hover:bg-rose-200 bg-rose-300">
        <div className="mr-1">DOGE</div>
      </button>
    </div>
  );
};

export default CoinFilter;

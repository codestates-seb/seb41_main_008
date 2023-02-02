/* eslint-disable */
import { Link } from 'react-router-dom';
import EthLogo from '../../assets/icons/eth-logo.png';
import customAxios from 'utils/api/axios';
import { getRaingkingData } from 'utils/api/api';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { AxiosError } from 'axios';
import '../Trending/Coin.css';

export interface Ranking {
  rank: number;
  collectionId: number;
  collectionName: string;
  logoImgName: string;
  coinId: number;
  coinName: string;
  coinImage: string;
  totalVolume: number;
  highestPrice: number;
}

interface RankingData extends Array<Ranking> {}

const CoinChart = ({ coinId }: { coinId: number }) => {
  const [data, setData] = useState<RankingData>();
  // const { time } = useParams();
  console.log(coinId);
  console.log(coinId);

  // useEffect(() => {
  //   getRaingkingData(time).then((res) => setData(res.data));
  // }, [time]);

  useEffect(() => {
    const getCoinData = async () => {
      try {
        const res = await customAxios.get(`/api/ranking/coin/${coinId}`);
        setData(res.data);

        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getCoinData();
  }, [coinId]);

  return (
    <div>
      <div className="top-collections">
        <div className="tCbox">
          {data?.map((topData) => {
            return (
              <div>
                <Link to={`/collection/${topData.collectionId}`}>
                  <div className="inTCbox">
                    <div className="left"></div>
                    <span>{topData.rank}</span>
                    <div className="middle">
                      <div className="topImgAvatar">
                        <img
                          src={`${process.env.REACT_APP_IMAGE}${topData?.logoImgName}`}
                          alt=" "
                        />
                      </div>
                      <div className="mText">
                        <div className="topCollName">
                          {topData.collectionName}
                        </div>
                        <div className="priceTopColl">
                          <div className="fPtC">HighestPrice: </div>
                          <div className="pTopColl">
                            <img src={topData.coinImage} alt="EthLogo" />
                            {topData.highestPrice}
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="right">
                      <span>{topData.coinName}</span>
                      <div className="pTopColl">
                        <img src={topData.coinImage} alt="EthLogo" />{' '}
                        {topData.totalVolume.toFixed(3)}
                      </div>
                    </div>
                  </div>
                </Link>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default CoinChart;

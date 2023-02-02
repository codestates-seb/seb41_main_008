/* eslint-disable */
import { Link } from 'react-router-dom';
import EthLogo from '../../assets/icons/eth-logo.png';
import customAxios from 'utils/api/axios';
import { getRaingkingData } from 'utils/api/api';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { AxiosError } from 'axios';
import '../Trending/Trending.css';

export interface Ranking {
  rank: number;
  collectionId: number;
  collectionName: string;
  coinId: number;
  coinName: string;
  totalVolume: number;
  highestPrice: number;
  logoImgName: string;
  coinImage: string;
}

interface RankingData extends Array<Ranking> {}

const TrendingChart = ({ option }: { option: string }) => {
  const [data, setData] = useState<RankingData>();
  // const { time } = useParams();
  console.log(option);
  console.log(option.toLowerCase().trim());


  // useEffect(() => {
  //   getRaingkingData(time).then((res) => setData(res.data));
  // }, [time]);

  useEffect(() => {
    const getRaingkingData = async () => {
      try {
        const res = await customAxios.get(
          `/api/ranking/time/${option.toLowerCase()}`
        );
        setData(res.data);

        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getRaingkingData();
  }, [option]);

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
                        {topData.totalVolume.toFixed(2)}
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

export default TrendingChart;

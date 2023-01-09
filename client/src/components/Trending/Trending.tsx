import { Link } from 'react-router-dom';
import EthLogo from '../../assets/icons/eth-logo.png';
import TrendingData from '../Trending/TrendingData.json';
import '../Trending/Trending.css';

const TrendingChart = () => {
  return (
    <div>
      <div className="top-collections">
        <div className="tCbox">
          {TrendingData.map((topData) => {
            return (
              <div key={topData.id}>
                <Link to="">
                  <div className="inTCbox">
                    <div className="left">
                      <span>{topData.id}</span>
                    </div>
                    <div className="middle">
                      <div className="topImgAvatar">
                        <img src={topData.img} alt="" />
                      </div>
                      <div className="mText">
                        <div className="topCollName">
                          {topData.collectionName}
                        </div>
                        <div className="priceTopColl">
                          <div className="fPtC">Floor price:</div>
                          <div className="pTopColl">
                            <img src={EthLogo} alt="EthLogo" />{' '}
                            {topData.floorPrice}
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="right">
                      <span>{topData.sellChart}</span>
                      <div className="pTopColl">
                        <img src={EthLogo} alt="EthLogo" />{' '}
                        {topData.totalVolume}
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

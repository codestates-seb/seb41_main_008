/* eslint-disable */
import { BORED_APE, LISTINGS, PROPERTIES } from './data';
import ETHIcon from '../../assets/icons/PurchaseIcons/ETH';
import EyeIcon from '../../assets/icons/PurchaseIcons/Eye';
import HeartIcon from '../../assets/icons/PurchaseIcons/Heart';
import OfferIcon from '../../assets/icons/PurchaseIcons/Offer';
import TimeIcon from '../../assets/icons/PurchaseIcons/Time';
import './Buy.sass';
import SearchIcon from '../../assets/icons/PurchaseIcons/Search';
import BuyAndCartButton from '../CartButton/BuyAndCartButton';
import CountdownTimer from './CountDownTime/CountDown';
import { getItemsData } from 'utils/api/api';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { useAppSelector } from 'hooks/hooks';
import SellModal from 'components/CartingModal/SellModal';
const asset = BORED_APE[0];
const ETH_TO_USD = 1549.95;

export interface ItemProps {
  coinId: number;
  coinName: string;
  collectionId: string;
  itemDescription: string;
  itemId: number;
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  onSale: boolean;
  trueownerId: number;
  ownerName: string;
  priceHistory: null;
  tradeHistory: null;
  withdrawFee: number;
}
const Asset = () => {
  const [data, setData] = useState<ItemProps>();
  const { itemId } = useParams();
  const { cartItems } = useAppSelector((state) => state.cart);
  console.log(cartItems);
  console.log(data);
  useEffect(() => {
    getItemsData(itemId).then((res) => setData(res.data));
  }, [itemId]);

  return (
    <div className="asset">
      <div className="container">
        <div className="asset__grid">
          <div className="asset__grid__item">
            <img src={asset.image} className="asset__image" alt="" />
            <div className="card">
              <div className="card__header">
                <SearchIcon />
                Description
              </div>
              <div className="card__body">
                <div className="asset__properties"></div>
                <span>testestestestest</span>
              </div>
            </div>
          </div>
          <div className="asset__grid__item asset__grid__item--expanded">
            <h2>#{asset.tokenId}</h2>
            <div className="asset__meta">
              <div className="asset__meta__item">
                Owned by <a>monkey</a>
              </div>
              <div className="asset__meta__item">
                <EyeIcon /> 0 views
              </div>
              <div className="asset__meta__item">
                <HeartIcon /> 0 favorites
              </div>
            </div>
            <div className="card">
              <div className="card__header">
                <TimeIcon />
                Sale ends january 31, 2023 at 23:59 UTC+9
              </div>
              <CountdownTimer />

              <div className="card__body">
                <div>
                  <div className="label">Current price</div>
                  <div className="asset__price">
                    <ETHIcon />
                    <span>85.986</span>
                    ($128,009.94)
                  </div>
                </div>
                <BuyAndCartButton data={data} />
              </div>
            </div>
            <div className="card">
              <div className="card__header">
                <OfferIcon />
                Offer
              </div>
              <div className="card__body">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Price</th>
                      <th>USD Price</th>
                      <th>Commission</th>
                      <th>From</th>
                      <th>To</th>
                    </tr>
                  </thead>
                  <tbody>
                    {LISTINGS.map((a) => (
                      <tr key={a.id}>
                        <td>
                          <div className="price">
                            <ETHIcon />
                            {a.price}
                          </div>
                        </td>
                        <td>${(a.price * ETH_TO_USD).toFixed(2)}</td>
                        <td>{a.expiration}</td>
                        <td>{a.from}</td>
                        <td>누구</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Asset;

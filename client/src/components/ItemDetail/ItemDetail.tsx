/* eslint-disable */
import { AxiosError } from 'axios';
import customAxios from 'utils/api/axios';
import ETHIcon from '../../assets/icons/PurchaseIcons/ETH';
import EyeIcon from '../../assets/icons/PurchaseIcons/Eye';
import HeartIcon from '../../assets/icons/PurchaseIcons/Heart';
import OfferIcon from '../../assets/icons/PurchaseIcons/Offer';
import TimeIcon from '../../assets/icons/PurchaseIcons/Time';
import './Buy.sass';
import { SlGraph } from 'react-icons/sl';
import { TbFileDescription } from 'react-icons/tb';
import BuyAndCartButton from '../CartButton/BuyAndCartButton';
import CountdownTimer from './CountDownTime/CountDown';
import { getItemsData } from 'utils/api/api';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { useAppSelector } from 'hooks/hooks';
import SellModal from 'components/CartingModal/SellModal';

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
  tradeHistory: ItemsData[];
  withdrawFee: number;
  logoImgName: string;
  collectionName: string;
}
interface ItemsData {
  sellerId: number;
  sellerName: string;
  buyerId: number;
  transPrice: number;
  coinName: string;
  transData: number;
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

  useEffect(() => {
    const getItemsData = async () => {
      try {
        const res = await customAxios.get(`/api/items/${itemId}`);
        setData(res.data);
        console.log(res);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getItemsData();
  }, [itemId]);

  return (
    <div className="asset">
      <div className="container">
        <div className="asset__grid">
          <div className="asset__grid__item">
            <img
              src={`${process.env.REACT_APP_IMAGE}${data?.itemImageName}`}
              className="asset__image"
              alt=""
            />
            <div className="card">
              <div className="card__header">
                <TbFileDescription />
                Description
              </div>
              <div className="card__body">
                <div className="asset__properties"></div>
                <div>{data?.itemDescription}</div>
              </div>
            </div>

            <div className="card">
              <div className="card__header">
                <SlGraph />
                Price History
              </div>

              <div className="card__body">
                <div className="asset__properties"></div>
                {/* <div>{data?.priceHistory}</div> */}
              </div>
            </div>
          </div>
          <div className="asset__grid__item asset__grid__item--expanded">
            <h2>#{data?.itemId}</h2>
            <div className="text-4xl font-bold">{data?.collectionName}</div>
            <div className="asset__meta">
              <div className="asset__meta__item">
                Owned by <a>{data?.ownerName}</a>
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
                    <span>{data?.itemPrice}</span>
                  </div>
                </div>
                <BuyAndCartButton data={data} />
              </div>
            </div>
            <div className="card">
              <div className="card__header">
                <OfferIcon />
                Trade History
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
                    {data?.tradeHistory.map((el: any) => (
                      <tr key={el}>
                        <td>
                          <div className="price">
                            <ETHIcon />
                            {el.sellerId}
                          </div>
                        </td>
                        <td>${}</td>
                        <td>{el.coinName}</td>
                        <td>{el.sellerId}</td>
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

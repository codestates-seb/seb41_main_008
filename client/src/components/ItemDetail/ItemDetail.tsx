import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
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
import Footer from 'components/Layout/Footer';
import { useQuery } from '@tanstack/react-query';
import MissingPage from 'pages/MissingPage';
import Header from 'components/Header/Header';
import Rechart from './Rechart';
import { date } from 'yup';

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
  priceHistory: PriceData[];
  tradeHistory: ItemsData[];
  withdrawFee: number;
  logoImgName: string;
  collectionName: string;
}
interface ItemsData {
  sellerId: number;
  sellerName: string;
  buyerId: number;
  buyerName: string;
  transPrice: number;
  coinName: string;
  transDate: number;
}

interface PriceData {
  transPrice: number;
  transDate: number;
}

const ButtonWrapper = styled.div`
  div {
    border-radius: 8px;
  }
`;

const Asset = () => {
  const { itemId } = useParams();

  const { isLoading, error, data } = useQuery<ItemProps>({
    queryKey: ['items', itemId],
    queryFn: () =>
      customAxios.get(`/api/items/${itemId}`).then((res) => res.data),
  });

  if (isLoading) return <p>Loading...</p>;

  if (error) return <MissingPage />;

  return (
    <>
      <Header />
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
                  <div style={{ width: 460, height: 400 }}>
                    <Rechart data={data} />
                  </div>
                </div>
              </div>
            </div>
            <div className="asset__grid__item asset__grid__item--expanded">
              <h2>#{data?.itemId}</h2>
              <div className="text-4xl font-bold">{data?.collectionName}</div>
              <div className="asset__meta">
                <div className="asset__meta__item">
                  Owned by{' '}
                  <Link to={`/collection/${itemId}`}>{data?.ownerName}</Link>
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
                  <ButtonWrapper>
                    <BuyAndCartButton data={data} />
                  </ButtonWrapper>
                </div>
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
                      <th>Commission</th>
                      <th>From</th>
                      <th>To</th>
                      <th>Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data?.tradeHistory.map((item) => (
                      <tr key={item.buyerId}>
                        <td>
                          <div className="price">
                            <ETHIcon />
                            {item.transPrice}
                          </div>
                        </td>
                        <td>{data?.withdrawFee}</td>
                        <td>{item.coinName}</td>
                        <td>{item.sellerName}</td>
                        <td>{item.buyerName}</td>
                        <td>{item.transDate}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Asset;

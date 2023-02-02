import { Link, useParams } from 'react-router-dom';
import { AxiosError } from 'axios';
import styled from 'styled-components';
import customAxios from 'utils/api/axios';
import EyeIcon from '../../assets/icons/PurchaseIcons/Eye';
import HeartIcon from '../../assets/icons/PurchaseIcons/Heart';
import OfferIcon from '../../assets/icons/PurchaseIcons/Offer';
import TimeIcon from '../../assets/icons/PurchaseIcons/Time';
import './Buy.sass';
import { SlGraph } from 'react-icons/sl';
import { TbFileDescription } from 'react-icons/tb';
import BuyAndCartButton from '../CartButton/BuyAndCartButton';
import CountdownTimer from './CountDownTime/CountDown';
import { useState, useEffect } from 'react';
import Rechart from './Rechart';
import Header from 'components/Header/Header';
import Footer from 'components/Layout/Footer';
import Notification from 'components/Notification';
import { BsCheckCircleFill } from 'react-icons/bs';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { setCreateItemOpen } from 'store/toastSlice';

export interface ItemProps {
  coinId: number;
  coinName: string;
  ownerId: Number;
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
  collectionName: string | number;
  coinImage: string;
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
  const [data, setData] = useState<ItemProps>();
  const { itemId } = useParams();
  const createItemOpen = useAppSelector((state) => state.toast.createItemOpen);
  const dispatch = useAppDispatch();

  useEffect(() => {
    setTimeout(() => dispatch(setCreateItemOpen(false)), 5000);

    const getItemsData = async () => {
      try {
        const res = await customAxios.get(`/api/items/${itemId}`);
        setData(res.data);
      } catch (error) {
        const err = error as AxiosError;
      }
    };

    getItemsData();
  }, [itemId, dispatch]);

  return (
    <div>
      <div className="asset mt-9 ">
        <div className="container">
          <div className="flex justify-between max-2xl:flex-col">
            <div className="w-full h-full   ">
              <div className="flex p-1 h-12 w-full rounded-t-lg">
                <img src={data?.coinImage} alt="EthLogo" />
              </div>
              <div className="w-[600px] h-[650px] max-2xl:w-full">
                <img
                  src={`${process.env.REACT_APP_IMAGE}${data?.itemImageName}`}
                  className=" h-full w-full"
                  alt="nftImage"
                />
              </div>
            </div>

            <div className="border border-blue-500 w-full">
              <div className=" ">
                <h2>{data?.collectionName}</h2>
                <div className="text-4xl font-bold">{data?.itemName}</div>
                <div className="asset__meta">
                  <div className="asset__meta__item">
                    Owned by{' '}
                    <Link to={`/account/${data?.ownerId}`}>
                      {data?.ownerName}
                    </Link>
                  </div>
                  <div className="asset__meta__item">
                    <EyeIcon /> 0 views
                  </div>
                  <div className="asset__meta__item">
                    <HeartIcon /> 0 favorites
                  </div>
                </div>

                <div className="w-full">
                  <div className=" w-full">
                    <div>
                      <div className="label">Current price</div>
                      <div className="asset__price">
                        <img
                          className=" w-4 h-4"
                          src={data?.coinImage}
                          alt="EthLogo"
                        />{' '}
                        <span>
                          {data?.itemPrice} {data?.coinName}
                        </span>
                      </div>
                    </div>
                    <ButtonWrapper>
                      <BuyAndCartButton data={data} />
                    </ButtonWrapper>
                  </div>
                </div>
              </div>

              <div className="h-[500px] ">
                <div className="">
                  <OfferIcon />
                  Trade History
                </div>
                <div className="table-auto overflow-scroll w-full">
                  <table className="table h-[450px]">
                    <thead>
                      <tr>
                        <th>Price</th>
                        <th>Commission</th>
                        <th>Coin</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Date</th>
                      </tr>
                    </thead>
                    <tbody className="">
                      {data?.tradeHistory.map((item) => (
                        <tr key={item.buyerId}>
                          <td>
                            <div className="price">
                              <img
                                className="w-3 h-3"
                                src={data?.coinImage}
                                alt="EthLogo"
                              />
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

          <div className="flex w-[450px] flex-col ">
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
                <div style={{ width: 430, height: 400 }}>
                  <Rechart data={data} />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Header />
      <Footer />
      <Notification open={createItemOpen} setOpen={setCreateItemOpen}>
        <p className="flex items-center gap-1 text-emerald-700">
          <span>
            <BsCheckCircleFill className="h-7 w-7" />
          </span>{' '}
          Created!
        </p>
      </Notification>
    </div>
  );
};

export default Asset;

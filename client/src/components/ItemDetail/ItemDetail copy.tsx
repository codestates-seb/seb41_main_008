/* eslint-disable */
import { Link } from 'react-router-dom';
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
  buyerName: string;
  transPrice: number;
  coinName: string;
  transData: string;
}

export const Asset = () => {
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
        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getItemsData();
  }, [itemId]);

  

  return <span>{data?.itemPrice}</span>;
};

export default Asset;

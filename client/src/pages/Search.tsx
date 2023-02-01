/* eslint-disable */
import customAxios from 'utils/api/axios';
import { getSearchdata } from 'utils/api/api';
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { AxiosError } from 'axios';


export interface Search {
  collections: collectionsData[];
  items: itemsData[];
  pageInfo:pageData[];
}

interface collectionsData {
  collectionId: number;
  collectionName: string;
  logoImgName: number;
  bannerImgName: string;
}

interface itemsData {
  itemPrice: number;
  onSale: boolean;
  itemId:number;
  itemImageName:string;
  itemName:string;
  collectionId: number;
  collectionName: string;
  coinId: number;
  coinName: string
  coinImage : string
}

interface pageData {
  totalElements: number;
  totalPages: number;
  size: number;
  page: number;
}

const Search = () => {
  const [data, setData] = useState<Search>();
  const { keyword, page, size } = useParams();
  
  useEffect(() => {
    getSearchdata(keyword, page, size).then((res) => setData(res.data));
  }, [keyword, page, size]);

  useEffect(() => {
    const getSearchdata = async () => {
      try {
        const res = await customAxios.get(
          `/api/search?keyword=${keyword}&page=${page}&size=${size}`
        );
        setData(res.data);
        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getSearchdata();
  }, [keyword, page, size]);

  return <div></div>;
};

export default Search;

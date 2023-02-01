/* eslint-disable */
import customAxios from 'utils/api/axios';
import { getSearchdata } from 'utils/api/api';
import { useParams, useSearchParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { AxiosError } from 'axios';

export interface Search {
  collections: collectionsData[];
  items: itemsData[];
  pageInfo: pageData[];
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
  itemId: number;
  itemImageName: string;
  itemName: string;
  collectionId: number;
  collectionName: string;
  coinId: number;
  coinName: string;
  coinImage: string;
}

interface pageData {
  totalElements: number;
  totalPages: number;
  size: number;
  page: number;
}

const Search = () => {
  const [data, setData] = useState<Search>();
  const [searchParams] = useSearchParams();
  const query = searchParams.get('q');

  return <div>search results</div>;
};

export default Search;

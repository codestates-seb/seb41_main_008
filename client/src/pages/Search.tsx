/* eslint-disable */
import { useSearchParams } from 'react-router-dom';
import axios from 'axios';
import { useQuery } from '@tanstack/react-query';
import Header from 'components/Header/Header';
import ColResults from 'components/Search/ColResults';
import { SearchCol } from './MyCollection';

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

// interface pageData {
//   totalElements: number;
//   totalPages: number;
//   size: number;
//   page: number;
// }

interface Data {
  data: itemsData[];
}

interface Results {
  collections: SearchCol[];
  items: Data;
}

const Search = () => {
  const [searchParams] = useSearchParams();
  const query = searchParams.get('q');

  const { isLoading, error, data } = useQuery<Results>({
    queryKey: ['search', { keyword: query }],
    queryFn: () =>
      axios
        .get(
          `${process.env.REACT_APP_API_URL}/api/search?keyword=${query}&page=1&size=6`
        )
        .then((res) => res.data),
  });

  console.log(data);
  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p> An error has occurred + {error.message}</p>;

  return (
    <div>
      <Header />
      <ColResults cols={data?.collections} />
    </div>
  );
};

export default Search;

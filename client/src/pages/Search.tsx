import { useSearchParams } from 'react-router-dom';
import axios from 'axios';
import Header from 'components/Header/Header';
import ColResults from 'components/Search/ColResults';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useInView } from 'react-intersection-observer';
import { useEffect } from 'react';
import ItemCard from 'components/ItemCard';

export interface Item {
  itemImageName: string;
  itemName: string;
  itemPrice: number;
  itemId: number;
  coinName: string;
  coinImage: string;
  collectionName: string;
}

export default function Search() {
  const [searchParams] = useSearchParams();
  const query = searchParams.get('q');

  const { status, data, error, isFetchingNextPage, fetchNextPage } =
    useInfiniteQuery(
      ['search', { keyword: query }],
      ({ pageParam = 1 }) =>
        axios
          .get(
            `${process.env.REACT_APP_API_URL}/api/search?keyword=${query}&page=${pageParam}&size=15`
          )
          .then((res) => res.data),

      {
        getNextPageParam: (lastPage, allPages) => {
          // return lastPage ? allPages.length + 1 : undefined;
          // console.log(lastPage, allPages);
          return lastPage.items.hasNext ? allPages.length + 1 : undefined;
        },
      }
    );
  const { ref, inView } = useInView();
  console.log(data);
  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView, fetchNextPage]);

  return status === 'loading' ? (
    <>
      <Header />
      <p className="mt-20">Loading...</p>
    </>
  ) : status === 'error' && error instanceof Error ? (
    <>
      <Header />
      <span className="mt-20">Error: {error.message}</span>
    </>
  ) : (
    <>
      <Header />
      <div className="mt-32 px-8">
        <em className="text-lg">Results for {query}</em>
        <ColResults cols={data?.pages[0].collections} />
        <h5 className="ml-3.5 mt-8 font-bold text-lg">items</h5>
        {/* <p className="ml-3.5">We coudln{"'"} find any items.</p> */}
        {data?.pages?.map((page, idx) => (
          <section
            key={idx}
            className="py-1.5 grid grid-cols-2 gap-y-3 gap-x-3 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5"
          >
            {page.items.data.map((item: Item) => (
              <ItemCard
                key={item.itemId}
                itemImageName={process.env.REACT_APP_IMAGE + item.itemImageName}
                itemName={item.itemName}
                itemPrice={item.itemPrice}
                itemId={item.itemId}
                coinName={item.coinName}
                coinImage={item.coinImage}
                collectionName={item.collectionName}
              />
            ))}
          </section>
        ))}
        <p className="inline-block" ref={ref}>
          {isFetchingNextPage && 'Loading more...'}
        </p>
      </div>
    </>
  );
}

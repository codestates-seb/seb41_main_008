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
            `${process.env.REACT_APP_API_URL}/api/search?keyword=${query}&page=${pageParam}&size=12`
          )
          .then((res) => res.data),

      {
        getNextPageParam: (lastPage, allPages) => {
          return lastPage.length ? allPages.length + 1 : undefined;
        },
      }
    );
  const { ref, inView } = useInView();

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
        {data?.pages?.map((page, idx) => (
          <>
            <em className="text-lg">Results for {query}</em>
            <ColResults cols={page.collections} />
            <h5 className="ml-3.5 mt-8 font-bold text-lg">
              {page.items.data.length} items
            </h5>
            {page.items.data.length ? (
              <>
                <section
                  key={idx}
                  className="py-1.5 grid grid-cols-2 gap-y-3 gap-x-3 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5"
                >
                  {page.items.data.map((item: Item) => (
                    <ItemCard
                      key={item.itemId}
                      itemImageName={
                        process.env.REACT_APP_IMAGE + item.itemImageName
                      }
                      itemName={item.itemName}
                      itemPrice={item.itemPrice}
                      itemId={item.itemId}
                      coinName={item.coinName}
                      coinImage={item.coinImage}
                      collectionName={item.collectionName}
                    />
                  ))}
                </section>
              </>
            ) : (
              <p className="ml-3.5">We coudln{"'"} find any items.</p>
            )}
          </>
        ))}
        <p className="inline-block" ref={ref}>
          {isFetchingNextPage && 'Loading more...'}
        </p>
      </div>
    </>
  );
}

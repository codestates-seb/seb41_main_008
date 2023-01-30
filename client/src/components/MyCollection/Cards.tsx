/* eslint-disable */

import { useInView } from 'react-intersection-observer';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import customAxios from 'utils/api/axios';
import ItemCard, { Item } from 'components/ItemCard';

export default function Cards({ id }: { id: string }) {
  const { status, data, error, isFetchingNextPage, fetchNextPage } =
    useInfiniteQuery(
      ['items', 'collections', id],
      ({ pageParam = 1 }) =>
        customAxios
          .get(`/api/items/collections/${id}?page=${pageParam}&size=18`)
          .then((res) => res.data.data),

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
  console.log(data);

  return status === 'loading' ? (
    <p>Loading...</p>
  ) : status === 'error' && error instanceof Error ? (
    <span>Error: {error.message}</span>
  ) : (
    <div className="px-8">
      {data?.pages.map((page, idx) => (
        <section
          key={idx}
          className="py-1.5 grid grid-cols-3 gap-y-3 gap-x-3 sm:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6"
        >
          {page.map((item: Item) => (
            <ItemCard
              key={item.itemId}
              itemImageName={process.env.REACT_APP_IMAGE + item.itemImageName}
              itemName={item.itemName}
              itemPrice={item.itemPrice}
              itemId={item.itemId}
              coinName={item.coinName}
              coinImage={item.coinImage}
              onSale={item.onSale}
              ownerId={item.ownerId}
            />
          ))}
        </section>
      ))}
      <p className="inline-block" ref={ref}>
        {isFetchingNextPage && 'Loading more...'}
      </p>
    </div>
  );
}

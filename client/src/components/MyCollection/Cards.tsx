/* eslint-disable */

import { useInView } from 'react-intersection-observer';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import customAxios from 'utils/api/axios';
import Card from 'components/Card';
import axios from 'axios';

export default function Cards({ id }: { id: string }) {
  const { status, data, error, isFetchingNextPage, fetchNextPage } =
    useInfiniteQuery(
      ['photos'],
      async ({ pageParam = 1 }) => {
        const res = await axios.get(
          'https://api.unsplash.com/photos/?client_id=SpTi-Now1Qi7BMcG3T1Uv84bU0y0w2uzLx1PWV3wz5g&per_page=12&page=' +
            pageParam
        );
        return res.data;
      },
      //   async ({ pageParam = 1 }) => {
      //     const res = await customAxios.get(
      //       `api/items/collections/${id}?size=4&page=` + pageParam
      //     );
      //     return res.data;
      //   },
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
    <>
      {data?.pages.map((page, idx) => (
        <section
          key={idx}
          className="grid grid-cols-1 gap-y-10 gap-x-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8"
        >
          {page.map((photo: any) => (
            <article
              key={photo.id}
              className="aspect-square overflow-hidden rounded-lg"
            >
              <img
                src={photo.urls.raw + '&w=500&auto=format'}
                alt="item"
                className="object-cover w-full h-full"
              />
            </article>
          ))}
        </section>
      ))}

      <p className="inline-block" ref={ref}>
        {isFetchingNextPage && 'Loading more...'}
      </p>
    </>
  );
}

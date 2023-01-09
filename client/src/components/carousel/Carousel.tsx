import axios, { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Collection from './Collection';

const Container = styled.div`
  padding: 1.5rem;
  display: grid;
  grid-auto-flow: column;
  grid-auto-columns: calc((100% - 4rem) / 5);
  gap: 1rem;
`;

interface urls {
  full: string;
}

interface Collections {
  likes: number;
  color: string;
  urls: urls;
  id: string;
  width?: number;
}

export default function Carousel({
  title,
  page,
}: {
  title: string;
  page: string;
}) {
  const [collection, setCollection] = useState<Collections[]>();

  useEffect(() => {
    const getCollection = async () => {
      try {
        const res = await axios.get(
          `https://api.unsplash.com/photos/?client_id=SpTi-Now1Qi7BMcG3T1Uv84bU0y0w2uzLx1PWV3wz5g&page=${page}&per_page=5`
        );
        setCollection(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err.response?.data);
      }
    };

    getCollection();
  }, [page]);

  return (
    <div>
      <h1 className="px-[1.5rem] text-xl font-bold">{title}</h1>
      <div>
        <Container>
          {collection?.map((piece) => (
            <Collection
              key={piece.id}
              url={piece.urls?.full}
              price={piece.likes}
              name={piece.color}
              volume={piece.width}
            />
          ))}
        </Container>
      </div>
    </div>
  );
}

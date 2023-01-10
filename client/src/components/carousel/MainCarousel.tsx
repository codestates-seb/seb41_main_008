import { useEffect, useState } from 'react';
import axios, { AxiosError } from 'axios';
import styled from 'styled-components';
import MainCollection from './MainCollection';

export interface urls {
  full: string;
}

export interface Collections {
  likes: number;
  color: string;
  urls: urls;
  id: string;
  width?: number;
}

const Container = styled.div`
  padding: 1.5rem;
  display: grid;
  grid-auto-flow: column;
  grid-auto-columns: calc((100% - 3rem) / 4);
  gap: 1rem;
`;

export default function MainCarousel() {
  const [collection, setCollection] = useState<Collections[]>();

  useEffect(() => {
    const getCollection = async () => {
      try {
        const res = await axios.get(
          'https://api.unsplash.com/photos/?client_id=SpTi-Now1Qi7BMcG3T1Uv84bU0y0w2uzLx1PWV3wz5g&per_page=4'
        );
        setCollection(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err.response?.data);
      }
    };

    getCollection();
  }, []);

  return (
    <div>
      <h1 className="text-center font-black text-4xl my-10">
        Explore, collect, and sell NFTs
      </h1>
      <Container>
        {collection?.map((piece) => (
          <MainCollection
            key={piece.id}
            url={piece.urls?.full}
            price={piece.likes}
            name={piece.color}
          />
        ))}
      </Container>
    </div>
  );
}

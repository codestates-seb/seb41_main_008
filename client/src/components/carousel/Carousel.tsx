import axios, { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Collection from './Collection';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper';

interface urls {
  raw: string;
}

interface Collections {
  likes: number;
  color: string;
  urls: urls;
  id: string;
  width?: number;
}

const Container = styled.div`
  padding: 2rem;
  margin-top: 2rem;
  .swiper-button-prev,
  .swiper-button-next {
    color: white;
    font-weight: 900;
    border-radius: 50%;
    padding: 1.5rem;
    background-color: rgb(0 0 0 / 0.5);
  }

  .swiper-button-prev:hover,
  .swiper-button-next:hover {
    color: black;
    background-color: white;
    box-shadow: rgb(0 0 0 / 15%) 0px 4px 10px;
  }

  .swiper-button-prev::after,
  .swiper-button-next::after {
    font-size: 1.5rem;
  }
`;

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
          `https://api.unsplash.com/photos/?client_id=SpTi-Now1Qi7BMcG3T1Uv84bU0y0w2uzLx1PWV3wz5g&page=${page}&per_page=10`
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
    <Container>
      <h1 className="text-2xl font-bold mb-4">{title}</h1>
      <Swiper
        spaceBetween={15}
        loop={true}
        loopFillGroupWithBlank={true}
        navigation={true}
        modules={[Navigation]}
        breakpoints={{
          0: {
            slidesPerView: 1,
            slidesPerGroup: 1,
          },
          600: {
            slidesPerView: 2,
            slidesPerGroup: 2,
          },
          768: {
            slidesPerView: 3,
            slidesPerGroup: 3,
          },
          1024: {
            slidesPerView: 4,
            slidesPerGroup: 4,
          },
          1200: {
            slidesPerView: 5,
            slidesPerGroup: 5,
          },
        }}
        className="rounded-md"
      >
        {collection?.map((piece) => (
          <SwiperSlide key={piece.id} className="py-2">
            <Collection
              url={piece.urls?.raw + '&w=500&auto=format'}
              price={piece.likes}
              name={piece.color}
              volume={piece.width}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </Container>
  );
}

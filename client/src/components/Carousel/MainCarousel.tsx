import axios from 'axios';
import styled from 'styled-components';
import MainCollection from './MainCollection';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation, Autoplay } from 'swiper';
import { useQuery } from '@tanstack/react-query';

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

export default function MainCarousel() {
  const { isLoading, error, data } = useQuery<Collections[]>({
    queryKey: [{ per_page: 12 }],
    queryFn: () =>
      axios
        .get(
          'https://api.unsplash.com/photos/?client_id=SpTi-Now1Qi7BMcG3T1Uv84bU0y0w2uzLx1PWV3wz5g&per_page=12'
        )
        .then((res) => res.data),
  });

  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  return (
    <Container>
      <h1 className="text-center font-black text-4xl my-10">
        Explore, collect, and sell NFTs
      </h1>
      <Swiper
        spaceBetween={15}
        loop={true}
        loopFillGroupWithBlank={true}
        navigation={true}
        autoplay={{
          delay: 5000,
          disableOnInteraction: false,
          pauseOnMouseEnter: true,
        }}
        modules={[Navigation, Autoplay]}
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
        }}
        className="rounded-2xl"
      >
        {data?.map((collection) => (
          <SwiperSlide key={collection.id}>
            <MainCollection
              url={collection.urls?.raw + '&w=500&auto=format'}
              price={collection.likes}
              name={collection.color}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </Container>
  );
}

import axios from 'axios';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper';
import { useQuery } from '@tanstack/react-query';
import { ColInfo } from './MainCarousel';
import Collection from './Collection';

interface Info extends ColInfo {
  description: string;
}

export default function Carousel() {
  const { isLoading, error, data } = useQuery<Info[]>({
    queryKey: ['collections', 'main', { page: 1, size: 10 }],
    queryFn: () =>
      axios
        .get(
          `${process.env.REACT_APP_API_URL}/api/collections/main?page=2&size=10`
        )
        .then((res) => res.data),
  });
  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  return (
    <div className="p-[2rem] mt-[2rem] space-y-5">
      <h1 className="font-bold text-3xl">Notable Collections</h1>
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
        className="carousel rounded-md"
      >
        {data?.map((col) => (
          <SwiperSlide
            className="bg-gray-200 group rounded-md"
            key={col.collectionId}
          >
            <Collection
              id={col.collectionId}
              name={col.collectionName}
              logo={process.env.REACT_APP_IMAGE + col.logoImgName}
              coin={col.coinImage}
              description={col.description}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}

import axios from 'axios';
import MainCollection from './MainCollection';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation, Autoplay } from 'swiper';
import { useQuery } from '@tanstack/react-query';

export interface ColInfo {
  collectionId: number;
  collectionName: string;
  logoImgName: string;
  coinImage: string;
}

export default function MainCarousel() {
  const { isLoading, error, data } = useQuery<ColInfo[]>({
    queryKey: ['collections', 'main', { page: 1, size: 12 }],
    queryFn: () =>
      axios
        .get(
          `${process.env.REACT_APP_API_URL}/api/collections/main?page=1&size=12`
        )
        .then((res) => res.data),
  });

  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

  return (
    <div className="py-[4rem] px-[2rem]">
      <h1 className="text-indigo-900 text-center font-black text-4xl my-10 dark:text-white">
        NFTeam{"'"}s ONE-STOP NFT Trade Shop
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
        className="rounded-2xl main-carousel"
      >
        {data?.map((col) => (
          <SwiperSlide key={col.collectionId}>
            <MainCollection
              id={col.collectionId}
              name={col.collectionName}
              logo={process.env.REACT_APP_IMAGE + col.logoImgName}
              coin={col.coinImage}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}

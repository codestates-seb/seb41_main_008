import { Navigation } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { SearchCol } from 'pages/MyCollection';
import ColResult from './ColResult';

export default function ColResults({
  cols,
}: {
  cols: SearchCol[] | undefined;
}) {
  return (
    <div className="mt-5 space-y-2">
      <h5 className="font-bold text-lg ml-3.5">{cols?.length} collections</h5>
      {cols?.length ? (
        <Swiper
          spaceBetween={15}
          loop={cols?.length >= 4 && true}
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
          }}
          className="main-carousel rounded-2xl overflow-hidden"
        >
          {cols?.map((col) => (
            <SwiperSlide className="group rounded-2xl" key={col.collectionId}>
              <ColResult
                collectionId={col.collectionId}
                logoImgName={col.logoImgName}
                bannerImgName={col.bannerImgName}
                collectionName={col.collectionName}
              />
            </SwiperSlide>
          ))}
        </Swiper>
      ) : (
        <p className="ml-3.5">We couldn{"'"}t find any collections.</p>
      )}
    </div>
  );
}

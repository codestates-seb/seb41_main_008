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
    <div className="mt-20 p-[2rem] space-y-2">
      <h5 className="font-bold">{cols?.length} collections</h5>
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
    </div>
  );
}

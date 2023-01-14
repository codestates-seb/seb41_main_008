import Carousel from '../components/carousel/Carousel';
import MainCarousel from '../components/carousel/MainCarousel';
import Footer from 'components/Layout/Footer';
import Top from '../components/Trending/Top';
import customAxios from 'utils/api/axios';
// import BuyAndCartButton from 'components/CartButton/BuyAndCartButton';
// import styled from 'styled-components';
// const Wrapper = styled.div`
//   div {
//     border-radius: 16px;
//     width: 100%;
//   }
// `;
const MainPage = () => {
  const test = async () => {
    console.log('click');
    try {
      const res = await customAxios.post('/api/collections', {
        coinId: '1',
        name: '새로운 컬렉션2',
        description: '올뉴 컬렉션2',
        logoImgName: 'imgimg2',
        bannerImgName: 'imgimgimg2',
      });
      console.log(res);
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <div>
      {/* <Wrapper>
        <BuyAndCartButton />
      </Wrapper> */}
      <button onClick={test}>testtest</button>
      <MainCarousel />
      <Top />
      <Carousel title="Notable collections" page="3" />
      <Carousel title="Top collector buys today" page="5" />
      <Carousel title="Photography NFT spotlight" page="7" />
      <Footer />
    </div>
  );
};
export default MainPage;

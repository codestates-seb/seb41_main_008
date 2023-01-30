import Header from 'components/Header/Header';
import styled from 'styled-components';
import { useState, useEffect } from 'react';
import { getCoinOrderInfo } from 'utils/api/api';
import { useNavigate } from 'react-router-dom';
import { BsCheckCircle } from 'react-icons/bs';
const OrderInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0px auto;
  height: 100%;
  width: 550px;
  border: 1px solid gray;
  padding: 20px;
  border-radius: 25px;
  gap: 30px;
`;
interface OrderInfoType {
  nickname: string;
  approvedAt: string;
  coinImage: string;
  coinName: string;
  totalPrice: number;
  coinCount: number;
  buyerId: string;
}
const SuccessPaymentPage = () => {
  const navigate = useNavigate();
  const [orderInfo, setOrderInfo] = useState<OrderInfoType>();
  const tid = localStorage.getItem('tid');
  useEffect(() => {
    getCoinOrderInfo(tid).then((res) => setOrderInfo(res.data));
  }, [tid]);
  console.log(orderInfo);
  return (
    <>
      <Header />
      <OrderInfoWrapper>
        <header className="flex justify-between flex-col items-center w-full border-b-2">
          <div className="flex flex-col justify-center items-center text-emerald-600 mb-5 font-bold">
            <h1 className="text-4xl">Payment successful!</h1>
            <h3 className="text-xl">Check your wallet</h3>
          </div>
          <div className="flex justify-between items-center w-full">
            <div>닉네임: {orderInfo?.nickname}</div>
            <div>ID: {orderInfo?.buyerId}</div>
          </div>
        </header>
        <section className="flex flex-col justify-start w-full gap-1">
          <div className="font-bold text-xl">Status</div>
          <div className="flex items-center justify-start font-semibold">
            <BsCheckCircle className="text-emerald-600 mr-1" /> Successful
          </div>
          <div className="font-bold text-xl">Date</div>
          <div className="font-semibold">{orderInfo?.approvedAt}</div>
          <div className="flex border-2 rounded-xl p-2">
            <div className="flex items-center justify-center w-10 h-10 rounded-full ">
              <img
                src={orderInfo?.coinImage}
                alt="coinImage"
                className="w-full h-full "
              />
            </div>
            <div className="">
              <div>Amount paid: {orderInfo?.totalPrice}₩ </div>
              <div className="flex">
                <div>You got {orderInfo?.coinCount}</div>
                <div>{orderInfo?.coinName}</div>
              </div>
            </div>
          </div>
          <div>
            <div></div>
          </div>
        </section>
        <footer className="w-full">
          <button
            className="BasicButton p-2 w-full rounded-xl"
            onClick={() => navigate('/')}
          >
            Home
          </button>
        </footer>
      </OrderInfoWrapper>
    </>
  );
};
export default SuccessPaymentPage;

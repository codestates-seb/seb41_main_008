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
  height: 500px;
  width: 550px;
  padding: 20px;
  border-radius: 25px;
  gap: 30px;
  position: fixed;
  margin: 0px auto;
  left: 0px;
  right: 0px;
  top: 50%;
  transform: translateY(-50%);
  box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px,
    rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px,
    rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;
`;
interface OrderInfoType {
  nickname: string;
  approvedAt: string;
  coinImage: string;
  coinName: string;
  totalPrice: number;
  coinCount: number;
  buyerId: string;
  orderId: number;
}
const SuccessPaymentPage = () => {
  const navigate = useNavigate();
  const [orderInfo, setOrderInfo] = useState<OrderInfoType>();
  const tid = localStorage.getItem('tid');
  useEffect(() => {
    getCoinOrderInfo(tid).then((res) => setOrderInfo(res.data));
  }, [tid]);
  console.log(orderInfo);
  if (localStorage.getItem('theme') === 'dark') {
    let body: any = document.getElementsByTagName('body');
    console.log(body);
    body.className += 'class';
  }

  return (
    <>
      <Header />
      <OrderInfoWrapper className="dark:text-black ">
        <header className="flex justify-between flex-col items-center w-full border-b-2 ">
          <div className="flex flex-col justify-center items-center text-emerald-600 mb-5 font-bold">
            <h1 className="text-4xl">Payment successful!</h1>
            <h3 className="text-xl">Check your wallet</h3>
          </div>
          <div className="flex justify-between items-center w-full font-semibold">
            <div>Nickname: {orderInfo?.nickname}</div>
            <div>User ID: {orderInfo?.buyerId}</div>
          </div>
        </header>
        <section className="flex flex-col justify-start w-full gap-1">
          <div className="font-bold text-xl">Status</div>
          <div className="flex items-center justify-start font-semibold">
            <BsCheckCircle className="text-emerald-600 mr-1" /> Successful
          </div>
          <div className="font-bold text-xl">Order Date</div>
          <div className="font-semibold">{orderInfo?.approvedAt}</div>
          <div className="font-bold text-xl">Order Id</div>
          <div className="font-semibold">{orderInfo?.orderId}</div>
          <div className="flex items-center justify-items-start border-2 rounded-xl p-2">
            <div className="flex items-center justify-center w-10 h-10 rounded-full ">
              <img
                src={orderInfo?.coinImage}
                alt="coinImage"
                className="w-full h-full "
              />
            </div>
            <div className="font-semibold ml-4">
              <div>
                Amount paid: {orderInfo?.totalPrice?.toLocaleString()}₩{' '}
              </div>
              <div className="flex">
                <div className="mr-1">
                  You got {orderInfo?.coinCount.toLocaleString()}
                </div>
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
            className="BasicButton p-2 w-full rounded-xl font-semibold"
            onClick={() => navigate('/', { replace: true })}
          >
            Home
          </button>
        </footer>
      </OrderInfoWrapper>
    </>
  );
};
export default SuccessPaymentPage;

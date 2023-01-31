import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { closePayment } from 'store/modalSlice';
import { clearCart } from 'store/cartSlice';
import { ModalBack } from './CartingModal';
import { useState, useEffect, useRef, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCoinPrice, transAction } from 'utils/api/api';
import CartItems from './CartItems';
const TransActionContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: fixed;
  background-color: white;
  width: 550px;
  height: 550px;
  border-radius: 20px;
  padding: 20px;
  z-index: 70;
  margin: 0px auto;
  top: 50%;
  left: 0;
  right: 0;
  transform: translateY(-50%);

  @media screen and (max-width: 764px) {
    width: 100%;
  }
`;

const TransActionModal = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [coinPrice, setCoinPrice] = useState(0);
  const { cartItems } = useAppSelector((state) => state.cart);
  const { paymentOpen } = useAppSelector((state) => state.modal);
  const cartId = localStorage.getItem('CART_ID');
  const memberId = localStorage.getItem('MEMBER_ID');
  const ref = useRef<HTMLDivElement>(null);

  const itemInfo = cartItems.map((el: any) => {
    return {
      itemId: el.itemId,
      sellerId: el.ownerId,
      coinId: el.coinId,
      transPrice: el.itemPrice,
    };
  });
  const totalPrice = useMemo(
    () =>
      cartItems
        .map((el: any) => el.itemPrice)
        .reduce((prev, curr) => prev + curr, 0),
    [cartItems]
  );
  console.log(cartItems);
  console.log('최적화 필요', totalPrice);

  const transActionHandler = () => {
    transAction({ cartId, itemInfo })
      .then((res) => {
        localStorage.setItem('CART_ID', res.data.cartId);
        dispatch(clearCart());
        dispatch(closePayment());
        navigate(`/account/${memberId}`);
      })
      .catch((err) => {
        if (err) {
          console.log(err);
          alert('코인이 부족합니다');
        }
      });
  };

  useEffect(() => {
    getCoinPrice(cartItems[0]?.coinName)
      .then((res: any) => setCoinPrice(res.data[0].trade_price))
      .catch((err) => console.log(err));
  }, [cartItems]);

  const modalClose = (e: MouseEvent) => {
    if (paymentOpen && ref.current?.contains(e.target as Node)) {
      dispatch(closePayment());
    }
  };
  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  });
  return (
    <>
      {paymentOpen && <ModalBack zIndex={'60'} ref={ref} />}
      {paymentOpen && (
        <TransActionContainer>
          <header className="flex justify-between items-center w-full font-bold p-4 border-b-2">
            <div className="w-full text-center">Payment for NFT</div>
            <button onClick={() => dispatch(closePayment())}>x</button>
          </header>
          <section className="overflow-auto h-full w-full">
            <ul className="w-full">
              {cartItems.map((el: any) => {
                return <CartItems key={el.itemId} {...el} />;
              })}
            </ul>
          </section>
          <footer className="flex  flex-col  w-full text-center font-semibold gap-2 border-t-2">
            <div className="">
              Total amount: {totalPrice} {cartItems[0]?.coinName}
            </div>
            <div>Total price: {(totalPrice * coinPrice).toLocaleString()}₩</div>
            <button
              className="BasicButton font-bold p-4 rounded-xl"
              onClick={transActionHandler}
            >
              Complete Payment
            </button>
          </footer>
        </TransActionContainer>
      )}
    </>
  );
};
export default TransActionModal;

import styled from 'styled-components';
import { useAppSelector, useAppDispatch } from 'hooks/hooks';
import { closePayment } from 'store/modalSlice';
import { clearCart } from 'store/cartSlice';
import { ModalBack } from './CartingModal';
import { useState, useEffect } from 'react';
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
  height: 450px;
  border-radius: 20px;
  padding: 20px;
  z-index: 70;
  margin: 0px auto;
  top: 50%;
  left: 0;
  right: 0;
  transform: translateY(-50%);
`;

const TransActionModal = () => {
  const dispatch = useAppDispatch();
  const [coinPrice, setCoinPrice] = useState(0);
  const { cartItems } = useAppSelector((state) => state.cart);
  const { paymentOpen } = useAppSelector((state) => state.modal);
  const cartId = localStorage.getItem('CART_ID');
  const itemInfo = cartItems.map((el: any) => {
    return {
      itemId: el.itemId,
      sellerId: el.ownerId,
      coinId: el.coinId,
      transPrice: el.itemPrice,
    };
  });
  console.log(cartId);
  console.log(itemInfo);
  const totalPrice = cartItems
    .map((el: any) => el.itemPrice)
    .reduce((prev, curr) => prev + curr, 0);
  console.log(cartItems);
  console.log(totalPrice);

  useEffect(() => {
    getCoinPrice(cartItems[0]?.coinName)
      .then((res) => setCoinPrice(res[0].trade_price))
      .catch((err) => console.log(err));
  });
  return (
    <>
      {paymentOpen && <ModalBack zIndex={'60'} />}
      {paymentOpen && (
        <TransActionContainer>
          <header className="flex justify-between items-center w-full font-bold p-4 border-b-2">
            <div className="w-full text-center">Payment for NFT</div>
            <button onClick={() => dispatch(closePayment())}>x</button>
          </header>
          <section className="overflow-auto h-full">
            <ul className="">
              {cartItems.map((el: any) => {
                return <CartItems key={el.itemId} {...el} />;
              })}
            </ul>
          </section>
          <footer>
            <div>총가격{totalPrice}</div>
            <button
              className="bg-emerald-600 text-white font-bold p-4 rounded-xl"
              onClick={() =>
                transAction({ cartId, itemInfo }).then((res) => {
                  localStorage.setItem('CART_ID', res.data.cartId);
                  dispatch(clearCart());
                })
              }
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

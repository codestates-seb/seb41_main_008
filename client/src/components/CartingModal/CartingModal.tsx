import styled from 'styled-components';
import CartItems from './CartItems';
import { useAppSelector, useAppDispatch } from '../../hooks/hooks';
import { closeModal, openPayment } from '../../store/modalSlice';
import { clearCart } from 'store/cartSlice';
import { useState, useEffect, useRef } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { cartSaveHandler, getCoinPrice } from '../../utils/api/api';
interface ModalContainerProps {
  visible: boolean;
}
interface ModalBackProps {
  zIndex?: string;
}
/**카팅모달 오픈시 뒷배경을 검정색으로 만들어주는 컴포넌트 */
export const ModalBack = styled.div<ModalBackProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  margin: auto;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: ${(props) => (props.zIndex ? props.zIndex : '10')}
    /**숫자로 props를 받아보자 */;
`;
const ModalContainer = styled.div<ModalContainerProps>`
  z-index: 60;
  display: flex;
  flex-direction: column;
  position: fixed;
  width: 400px;
  height: 750px;
  right: 28px;
  top: 30px;

  overflow: auto;
  border-radius: 20px;
  background-color: #ffffff;
  transition: 0.5s;
  box-shadow: 0 0 5px;
  /**opacity를 같이 활용하기위해 visibility를 사용 */
  visibility: ${(props) => (props.visible ? 'visible' : 'hidden')};
  opacity: ${(props) => (props.visible ? '1' : '0')};
  transform: ${(props) =>
    props.visible ? 'translateX(0px)' : 'translateX(380px)'};
  @media screen and (max-width: 1024px) {
    position: fixed;
    bottom: 0;

    width: 100%;
    height: auto;
    border-radius: 20px 20px 0px 0px;
    right: 0;
    transform: ${(props) =>
      props.visible ? 'translateY(0px)' : 'translateY(300px)'};
  }
`;

const CartingModal = () => {
  const dispatch = useAppDispatch();
  const ref = useRef<HTMLDivElement>(null);
  const [coinPrice, setCoinPrice] = useState(0);

  const { cartItems } = useAppSelector((state) => state.cart);
  const { isOpen } = useAppSelector((state) => state.modal);
  const { isLogin } = useAppSelector((state) => state.login);
  /**여러개 트랜잭션 보낼떄 이거보내주면됨*/
  const cartItemsItemId = cartItems.map((el: any) => el.itemId);

  const totalPrice = cartItems
    .map((el: any) => el.itemPrice)
    .reduce((prev, curr) => prev + curr, 0);

  /**모달 오픈시 모달창영역밖 클릭했을떄 모달닫는 기능*/
  const modalClose = (e: MouseEvent) => {
    if (isOpen && ref.current?.contains(e.target as Node)) {
      dispatch(closeModal());
    }
  };
  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  });

  useEffect(() => {
    getCoinPrice(cartItems[0]?.coinName)
      .then((res) => setCoinPrice(res[0].trade_price))
      .catch((err) => console.log(err));
  }, [cartItems]);

  const purchaseHandler = () => {
    if (!isLogin) {
      alert('로그인 먼저 해주세요.');
      dispatch(closeModal());
      window.location.replace('/login');
    } else {
      cartSaveHandler({
        cartId: Number(localStorage.getItem('CART_ID')),
        itemIdList: cartItemsItemId,
        totalPrice: 150,
      });
      dispatch(closeModal());
      dispatch(openPayment());
    }
  };
  return (
    <>
      {isOpen && <ModalBack ref={ref} zIndex={'50'} />}
      <ModalContainer visible={isOpen}>
        <header className="flex justify-between items-center font-semibold text-2xl p-4 border-b-2">
          <span>Your cart</span>
          <FontAwesomeIcon
            className="cursor-pointer"
            icon={faXmark}
            onClick={() => dispatch(closeModal())}
          />
        </header>
        {cartItems.length > 0 ? (
          <section className="p-4 border-b-2 overflow-auto">
            <div className="flex justify-between font-bold">
              <div>{cartItems.length} items</div>
              <button onClick={() => dispatch(clearCart())}>Clear all</button>
            </div>
            <ul className="flex flex-col gap-2 h-auto overflow-hidden ">
              {cartItems.map((el: any) => {
                return <CartItems key={el.itemId} {...el} />;
              })}
            </ul>
          </section>
        ) : (
          <div className="flex justify-center items-center w-full h-full text-gray-600 font-bold text-opacity-40 text-3xl border-b-2">
            Add items to get NFT
          </div>
        )}

        {cartItems.length !== 0 && (
          <footer className="flex justify-between p-4">
            <div>Total price</div>
            <div className="flex flex-col">
              <div>
                {totalPrice} {cartItems[0].coinName}
              </div>
              <div>{Number(totalPrice * coinPrice).toLocaleString()}원</div>
            </div>
          </footer>
        )}
        <div className="flex justify-center mt-5 p-4">
          <button
            disabled={cartItems.length === 0 ? true : false}
            className={`${'bg-emerald-600 text-white px-10 py-3 rounded-lg mb-5 w-full'} ${
              cartItems.length > 0 ? 'bg-opacity-100' : 'bg-opacity-50 '
            }`}
            onClick={purchaseHandler}
          >
            Complete purchase
          </button>
        </div>
      </ModalContainer>
    </>
  );
};
export default CartingModal;

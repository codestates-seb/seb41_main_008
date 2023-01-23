import styled from 'styled-components';
import CartItems from './CartItems';
import { useAppSelector, useAppDispatch } from '../../hooks/hooks';
import { closeModal } from '../../store/modalSlice';
import { clearCart } from 'store/cartSlice';
import { useEffect, useRef } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { cartSaveHandler } from '../../utils/api/api';
interface ModalContainerProps {
  visible: boolean;
}
/**카팅모달 오픈시 뒷배경을 검정색으로 만들어주는 컴포넌트 */
export const ModalBack = styled.div`
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
  z-index: 60;
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
  const { cartItems } = useAppSelector((state) => state.cart);
  /**여러개 트랜잭션 보낼떄 이거보내주면됨*/
  const cartItemsItemId = cartItems.map((el: any) => el.itemId);
  // console.log(cartItemsItemId);
  const ref = useRef<HTMLDivElement>(null);
  const { isOpen } = useAppSelector((state) => state.modal);
  const dispatch = useAppDispatch();

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

  return (
    <>
      {isOpen && <ModalBack ref={ref}></ModalBack>}
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
          <section className="p-4 border-b-2">
            <div className="flex justify-between font-bold">
              <div>{cartItems.length} items</div>
              <button onClick={() => dispatch(clearCart())}>Clear all</button>
            </div>
            <ul className="flex flex-col gap-2 h-auto overflow-auto ">
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
              <div>9999999ETH</div>
              <div>달러가격</div>
            </div>
          </footer>
        )}
        <div className="flex justify-center mt-5 p-4">
          <button
            disabled={cartItems.length === 0 ? true : false}
            className={`${'bg-emerald-600 text-white px-10 py-3 rounded-lg mb-5 w-full'} ${
              cartItems.length > 0 ? 'bg-opacity-100' : 'bg-opacity-50 '
            }`}
            onClick={() =>
              cartSaveHandler({
                cartId: 6,
                itemIdList: cartItemsItemId,
                totalPrice: 1,
              })
            }
          >
            Complete purchase
          </button>
        </div>
      </ModalContainer>
    </>
  );
};
export default CartingModal;

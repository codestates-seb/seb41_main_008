import styled from 'styled-components';
import CartItems from './CartItems';
import { useAppSelector, useAppDispatch } from '../../hooks/hooks';
import { closeModal, viewModal } from '../../store/modalSlice';
import { useEffect, useRef } from 'react';
interface ModalContainerProps {
  visible: boolean;
}
/**카팅모달 오픈시 뒷배경을 검정색으로 만들어주는 컴포넌트 */
const ModalBack = styled.div`
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
  z-index: 50;
`;
export const ModalContainer = styled.div<ModalContainerProps>`
  z-index: 60;
  display: flex;
  flex-direction: column;
  position: fixed;
  padding: 20px;
  width: 350px;
  height: 550px;
  right: 28px;
  top: 30px;
  border-radius: 20px;
  background-color: white;
  transition: 0.7s;
  box-shadow: 0 0 5px;
  /**opacity를 같이 활용하기위해 visibility를 사용 */
  visibility: ${(props) => (props.visible ? 'visible' : 'hidden')};
  opacity: ${(props) => (props.visible ? '1' : '0')};
  transform: ${(props) =>
    props.visible ? 'translateX(0px)' : 'translateX(380px)'};
`;

/**카팅모달의 구역을 나눠주기 위한 컴포넌트 */
const Line = styled.hr`
  margin-block-start: 0.5rem;
  margin-block-end: 0.5rem;
  width: 350px;
  margin-left: -20px;
`;

const CartingModal = () => {
  const ref = useRef<HTMLDivElement>(null);
  const isOpen = useAppSelector(viewModal);
  const dispatch = useAppDispatch();

  /**60~71 모달 오픈시 모달창영역밖 클릭했을떄 모달닫는 기능. *target 타입 수정필요 */
  const modalClose = (e: any) => {
    if (isOpen && ref.current?.contains(e.target)) {
      dispatch(closeModal());
    }
  };

  useEffect(() => {
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  }, [isOpen]);

  return (
    <>
      {isOpen && <ModalBack ref={ref}></ModalBack>}
      <ModalContainer visible={isOpen}>
        <div className="flex justify-between items-center">
          <span>Your cart</span>
          <button onClick={() => dispatch(closeModal())}>x</button>
        </div>
        <Line />
        <div className="flex justify-between py-4">
          <div>3 items</div>
          <button>Clear all</button>
        </div>
        <ul className="h-72 overflow-auto">
          <CartItems />
          <CartItems />
          <CartItems />
          <CartItems />
          <li>카팅모달 axios로 데이터 불러와보기</li>
          fixed witn overflow 구글링해서 제대로 처리하기
        </ul>
        <Line />
        <div className="flex justify-between py-2">
          <div>Total price</div>
          <div className="flex flex-col">
            <div>9999999ETH</div>
            <div>달러가격</div>
          </div>
        </div>
        <div className="flex justify-center mt-5">
          <button className="bg-[#2081e2] text-white px-10 py-3 rounded-lg">
            Complete purchase
          </button>
        </div>
      </ModalContainer>
    </>
  );
};
export default CartingModal;

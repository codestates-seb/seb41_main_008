import styled from 'styled-components';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { addTocart } from 'store/cartSlice';
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  color: white;
  width: 100%;
  height: 100%;
`;
const BuyAndCartButton = ({ data }: any) => {
  const { cartItems } = useAppSelector((state) => state.cart);
  console.log(cartItems);
  const dispatch = useAppDispatch();
  const buynowHandler = () => {
    /**buynow 관련로직 작성 */
    alert('buynow');
  };
  const cartHandler = () => {
    if (!cartItems.map((el: any) => el.itemId).includes(data.itemId)) {
      dispatch(addTocart(data));
    } else {
      console.log('이미 담긴 물건입니다');
    }
    /**장바구니담는 로직작성 */
    alert('carted');
  };
  return (
    <ButtonWrapper>
      <div className="grow bg-emerald-700 hover:bg-emerald-600 ">
        <button className="h-full w-full p-2" onClick={cartHandler}>
          Add to Cart
        </button>
      </div>
      <div className="grow-0 bg-emerald-700 hover:bg-emerald-600 ">
        <button
          className="border-l-2 h-full w-full p-2 "
          onClick={buynowHandler}
        >
          Buynow
        </button>
      </div>
    </ButtonWrapper>
  );
};
export default BuyAndCartButton;

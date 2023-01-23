import styled from 'styled-components';
import { useAppDispatch, useAppSelector } from 'hooks/hooks';
import { addTocart } from 'store/cartSlice';
import { openSell } from 'store/modalSlice';
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  color: white;
  width: 100%;
  height: 100%;
`;
const BuyAndCartButton = ({ data }: any) => {
  const { cartItems } = useAppSelector((state) => state.cart);
  const memberId = Number(localStorage.getItem('memberId'));
  const dispatch = useAppDispatch();

  const buynowHandler = () => {
    /**buynow 관련로직 작성 */
    alert('buynow');
  };

  const cartHandler = () => {
    if (!cartItems.map((el: any) => el.itemId).includes(data.itemId)) {
      dispatch(addTocart(data));
      alert('carted');
    } else {
      alert('이미 담긴 물건입니다');
    }
    /**장바구니담는 로직작성 */
  };

  const forSaleHandler = (itemId: number) => {
    /**판매하기 기능 로직작성 */
    dispatch(openSell(itemId));
  };

  /**NFTownerId 와 memberId가 같은경우에만 sell버튼 활성화 밑 둘이 같다면 addTocart는 랜더링x + onSale true일경우 addTocart버튼랜더링 및  false일 경우 아예랜더x*/
  return (
    <ButtonWrapper>
      {data?.onSale && data?.ownerId !== memberId ? (
        <div className="grow bg-emerald-700 hover:bg-emerald-600 ">
          <button
            className="h-full w-full p-2 text-lg font-semibold"
            onClick={cartHandler}
          >
            Add to Cart
          </button>
        </div>
      ) : null}
      {data?.onSale && data?.ownerId !== memberId ? (
        <div className="grow-0 bg-emerald-700 hover:bg-emerald-600 ">
          <button
            className="border-l-2 h-full w-full p-2 text-lg font-semibold"
            onClick={buynowHandler}
          >
            Buynow
          </button>
        </div>
      ) : null}
      {data?.onSale === false && data?.ownerId === memberId ? (
        <div className="grow bg-emerald-700 hover:bg-emerald-600 ">
          <button
            className="border-l-2 h-full w-full p-2 text-lg font-semibold"
            onClick={() => forSaleHandler(data.itemId)}
          >
            List for sale
          </button>
        </div>
      ) : null}
    </ButtonWrapper>
  );
};
export default BuyAndCartButton;

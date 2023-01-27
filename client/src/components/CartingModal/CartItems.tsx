import { removeCart } from '../../store/cartSlice';
import { useAppDispatch } from '../../hooks/hooks';
interface CartItemsProps {
  itemId: number;
  coinName: string;
  collectionName: string;
  itemImageName: string;
  itemPrice: number;
  itemName: string;
}
const CartItems = ({
  itemId,
  itemName,
  coinName,
  collectionName,
  itemImageName,
  itemPrice,
}: CartItemsProps) => {
  const dispatch = useAppDispatch();
  return (
    <li className="flex p-2 items-center hover:bg-[#eef1f1] rounded-lg hover:shadow-md">
      <a href={`/items/${itemId}`}>
        <div className="flex w-[100px] h-[80px] mr-4">
          <img
            className="rounded-lg"
            src={process.env.REACT_APP_IMAGE + itemImageName}
            alt=""
          />
        </div>
      </a>
      <div className="flex flex-col w-full font-bold">
        <div>{collectionName}</div>
        <div>{itemName}</div>
      </div>
      <div>
        <div className="flex ">
          <div>{itemPrice}</div>
          <div>{coinName}</div>
        </div>
        <button
          onClick={() => {
            dispatch(removeCart(itemId));
          }}
        >
          삭제
        </button>
      </div>
    </li>
  );
};
export default CartItems;

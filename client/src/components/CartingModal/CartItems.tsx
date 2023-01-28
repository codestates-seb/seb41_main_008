import { removeCart } from '../../store/cartSlice';
import { useAppDispatch } from '../../hooks/hooks';
import { FiTrash } from 'react-icons/fi';
import { closeModal, closePayment } from 'store/modalSlice';
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
    <li className="flex p-2 items-center hover:bg-[#eef1f1] rounded-lg hover:shadow-md font-bold">
      <a
        href={`/items/${itemId}`}
        onClick={() => {
          dispatch(closeModal());
          dispatch(closePayment());
        }}
      >
        <div className="flex mr-4">
          <img
            className="rounded-lg"
            src={process.env.REACT_APP_IMAGE + itemImageName}
            alt=""
          />
        </div>
      </a>
      <div className="flex flex-col w-full ">
        <div>{collectionName}</div>
        <div>{itemName}</div>
      </div>
      <div className="flex flex-col justify-center items-center">
        <div className="flex">
          <div>{itemPrice.toLocaleString()}</div>
          <div>{coinName}</div>
        </div>
        <button
          onClick={() => {
            dispatch(removeCart(itemId));
          }}
        >
          <FiTrash />
        </button>
      </div>
    </li>
  );
};
export default CartItems;

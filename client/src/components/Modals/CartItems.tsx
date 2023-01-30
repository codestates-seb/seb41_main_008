import { removeCart } from '../../store/cartSlice';
import { useAppDispatch } from '../../hooks/hooks';
import { FiTrash } from 'react-icons/fi';
import { RiErrorWarningLine } from 'react-icons/ri';
import { closeModal, closePayment } from 'store/modalSlice';
interface CartItemsProps {
  itemId: number;
  coinName: string;
  collectionName: string;
  itemImageName: string;
  itemPrice: number;
  itemName: string;
  soldoutArr: string[];
}
const CartItems = ({
  soldoutArr,
  itemId,
  itemName,
  coinName,
  collectionName,
  itemImageName,
  itemPrice,
}: CartItemsProps) => {
  const dispatch = useAppDispatch();
  const itemSoldout = () => {
    if (soldoutArr) {
      return soldoutArr.filter((el: any) => el === itemId)[0];
    }
  };
  return (
    <li
      className={`${
        itemSoldout() ? 'opacity-50' : ''
      } ${'flex p-2 items-center hover:bg-[#eef1f1] rounded-lg hover:shadow-md font-bold w-full'}`}
    >
      <a
        className="w-3/12"
        href={`/items/${itemId}`}
        onClick={() => {
          dispatch(closeModal());
          dispatch(closePayment());
        }}
      >
        <div className="flex  w-16 h-16">
          <img
            className="rounded-lg"
            src={process.env.REACT_APP_IMAGE + itemImageName}
            alt=""
          />
        </div>
      </a>

      <div className="flex flex-col w-6/12">
        <div className="">{collectionName}</div>
        <div>{itemName}</div>
        {itemSoldout() && (
          <div className="flex justify-center items-center text-red-700 bg-red-200 w-24 text-sm opacity-100 rounded-full ">
            <RiErrorWarningLine className="mr-1 " />
            Soldout
          </div>
        )}
      </div>

      <div className="flex flex-col justify-center items-center w-3/12 ">
        <div className="flex justify-center items-center ">
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

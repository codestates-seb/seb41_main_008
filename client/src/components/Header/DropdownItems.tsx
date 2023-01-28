import { useNavigate } from 'react-router-dom';
import { useAppSelector } from 'hooks/hooks';
import { logout, cartSaveHandler } from 'utils/api/api';
import { MdCreateNewFolder, MdNightlight } from 'react-icons/md';
import { IoIosCreate } from 'react-icons/io';
const DropdownItems = () => {
  const navigate = useNavigate();
  const { isLogin } = useAppSelector((state) => state.login);
  const { cartItems } = useAppSelector((state) => state.cart);
  const cartId = Number(localStorage.getItem('CART_ID'));
  const cartItemsItemId = cartItems.map((el: any) => el.itemId);

  const onRoute = (route: string) => {
    if (!isLogin) {
      alert('로그인이 필요합니다.');
      navigate('/login');
    } else {
      navigate(route);
    }
  };
  const logoutHandler = () => {
    logout();
    cartSaveHandler({ cartId, itemIdList: cartItemsItemId, totalPrice: 1 });
  };
  return (
    <div className="absolute">
      <ul className="w-48 shadow-2xl rounded-xl bg-white">
        <li className=" shadow hover:border-y-2 ">
          {/**Link 태그를 안쓴이유: Link를 쓰게된다면 로그인이 안된상태에서 메뉴를 클릭할시 to={''} 에 할당된 주소로 먼저 이동하고 로그인페이지로 이동하게됨. 컬렉션생성,NFT생성의경우 로그인이 필수이기때문에 Link를 사용안하였음. */}
          <button
            onClick={() => onRoute('/collections')}
            className="flex justify-between w-full h-full py-4 px-2 items-center"
          >
            <MdCreateNewFolder className="grow-0" />
            <span className="grow">Create Collection</span>
          </button>
        </li>
        <li className="shadow hover:border-y-2">
          <button
            onClick={() => onRoute('/asset/create')}
            className="flex justify-between w-full h-full py-4 px-2 items-center"
          >
            <IoIosCreate className="grow-0" />
            <span className="grow">Create NFT</span>
          </button>
        </li>
        {isLogin && (
          <li className="py-4">
            <button
              onClick={logoutHandler}
              className="flex justify-between w-full h-full px-2 items-center"
            >
              Logout
            </button>
          </li>
        )}
        <li className="rounded-b-xl py-4 shadow hover:border-y-2">
          <button className="flex justify-between w-full h-full px-2 items-center">
            <MdNightlight className="grow-0" />
            <span className="grow">Night Mode</span>
          </button>
        </li>
      </ul>
    </div>
  );
};
export default DropdownItems;

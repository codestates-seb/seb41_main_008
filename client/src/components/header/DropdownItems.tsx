import { useNavigate } from 'react-router-dom';
import { useAppSelector } from 'hooks/hooks';
const DropdownItems = () => {
  const navigate = useNavigate();
  const { isLogin } = useAppSelector((state) => state.login);
  const onRoute = (route: string) => {
    if (!isLogin) {
      alert('로그인이 필요합니다.');
      navigate('/login');
    } else {
      navigate(route);
    }
  };
  return (
    <div className="absolute">
      <ul className="w-48 shadow-2xl rounded-xl bg-white">
        {/* <li className="rounded-t-xl py-4 shadow hover:border-b-2">
          <button onClick={() => onRoute('/account')}>
            My Account
          </button>
        </li> */}
        <li className="py-4 shadow hover:border-y-2 ">
          {/**Link 태그를 안쓴이유: Link를 쓰게된다면 로그인이 안된상태에서 메뉴를 클릭할시 to={''} 에 할당된 주소로 먼저 이동하고 로그인페이지로 이동하게됨. 컬렉션생성,NFT생성의경우 로그인이 필수이기때문에 Link를 사용안하였음. */}
          <button onClick={() => onRoute('/collections')}>
            Create Collection
          </button>
        </li>
        <li className="py-4 shadow hover:border-y-2">
          <button onClick={() => onRoute('/item')}>Create NFT</button>
        </li>
        <li className="rounded-b-xl py-4 shadow hover:border-y-2">
          Night Mode
        </li>
      </ul>
    </div>
  );
};
export default DropdownItems;

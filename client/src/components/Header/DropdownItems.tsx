import { useNavigate } from 'react-router-dom';
import { useAppSelector } from 'hooks/hooks';
import { logout } from 'utils/api/api';
import { useQuery } from '@tanstack/react-query';
import customAxios from 'utils/api/axios';
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
  const { isSuccess } = useQuery({
    queryKey: ['auth', 'logout'],
    queryFn: () =>
      customAxios.get('/auth/logout', {
        headers: {
          RefreshToken: localStorage.getItem('REFRESH_TOKEN'),
        },
      }),
  });

  if (isSuccess) {
    window.localStorage.clear();
    window.location.replace('/');
    window.location.reload();
  }

  return (
    <div className="absolute">
      <ul className="w-48 shadow-2xl rounded-xl bg-white">
        <li
          className="dropdown-menu-item rounded-t-xl"
          // onClick={() => onRoute('/collections')}
        >
          {/**Link 태그를 안쓴이유: Link를 쓰게된다면 로그인이 안된상태에서 메뉴를 클릭할시 to={''} 에 할당된 주소로 먼저 이동하고 로그인페이지로 이동하게됨. 컬렉션생성,NFT생성의경우 로그인이 필수이기때문에 Link를 사용안하였음. */}
          <button className="dropdown-munu-btn">Create Collection</button>
        </li>
        <li className="dropdown-menu-item">
          <button
            className="dropdown-munu-btn"
            onClick={() => onRoute('/asset/create')}
          >
            Create NFT
          </button>
        </li>
        {isLogin && (
          <li className="dropdown-menu-item">
            <button className="dropdown-munu-btn" onClick={logout}>
              Logout
            </button>
          </li>
        )}
        <li className="rounded-b-xl dropdown-menu-item">
          <button className="dropdown-munu-btn">Night Mode</button>
        </li>
      </ul>
    </div>
  );
};
export default DropdownItems;

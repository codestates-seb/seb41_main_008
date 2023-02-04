import styled from 'styled-components';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAppSelector } from 'hooks/hooks';
import { logout } from 'utils/api/api';
import { MdCreateNewFolder, MdNightlight } from 'react-icons/md';
import { IoIosCreate, IoMdSunny } from 'react-icons/io';
import { RiLogoutBoxRLine } from 'react-icons/ri';

type ToggleType = {
  isOn: boolean;
};
const ToggleContainer = styled.div<ToggleType>`
  display: flex;
  width: 64px;
  height: 24px;
  background-color: ${(props) => (props.isOn ? 'rgb(32, 129, 226)' : 'gray')};
  border-radius: 15px;
  align-items: center;
  padding: 10px;
`;
const ToggleCircle = styled.input<ToggleType>`
  position: relative;
  background-color: white;
  outline: none;
  width: 15px;
  height: 15px;
  appearance: none;
  border-radius: 9999px;
  transition: 0.2s ease-out;
  right: ${(props) => (props.isOn ? '-35px' : '5px')};
`;
const DropdownItems = () => {
  const navigate = useNavigate();
  const { isLogin } = useAppSelector((state) => state.login);
  // const [dark, setDark] = useState(false);
  const [isOn, setIsOn] = useState(false);

  const onChangeToggle = () => {
    setIsOn(!isOn);
  };
  const darkMode = () => {
    /**다크모드 off */
    if (localStorage.getItem('theme') === 'dark') {
      document.documentElement.classList.remove('dark');
      localStorage.removeItem('theme');
    } else {
      /**다크모드 on */
      document.documentElement.classList.add('dark');
      localStorage.setItem('theme', 'dark');
    }
  };
  useEffect(() => {
    /**로컬스토리지 다크모드 여부에따라 보여질 첫화면 */
    if (localStorage.getItem('theme') === 'dark') {
      document.documentElement.classList.add('dark');
    }
  }, []);

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
  };
  return (
    <div className="absolute">
      <ul className="w-56 shadow-2xl rounded-xl bg-white dark:bg-[#303339] dark:text-white ">
        <li className="border-b-2 dark:border-gray-600">
          {/**Link 태그를 안쓴이유: Link를 쓰게된다면 로그인이 안된상태에서 메뉴를 클릭할시 to={''} 에 할당된 주소로 먼저 이동하고 로그인페이지로 이동하게됨. 컬렉션생성,NFT생성의경우 로그인이 필수이기때문에 Link를 사용안하였음. */}
          <button
            onClick={() => onRoute('/collections')}
            className="dropdown-style"
          >
            <MdCreateNewFolder className="" />
            <span className="">Create Collection</span>
          </button>
        </li>
        <li className="border-b-2 dark:border-gray-600">
          <button
            onClick={() => onRoute('/asset/create')}
            className="dropdown-style"
          >
            <IoIosCreate />
            <span>Create NFT</span>
          </button>
        </li>
        {isLogin && (
          <li className="border-b-2 dark:border-gray-600">
            <button onClick={logoutHandler} className="dropdown-style">
              <RiLogoutBoxRLine />
              <span>Logout</span>
            </button>
          </li>
        )}
        <li className="rounded-b-xl ">
          <label htmlFor="night-mode" className="dropdown-style">
            {isOn ? <MdNightlight /> : <IoMdSunny />}
            <span>Night Mode</span>
            <ToggleContainer isOn={isOn}>
              <ToggleCircle
                onClick={darkMode}
                isOn={isOn}
                id="night-mode"
                type="checkbox"
                role="switch"
                onChange={onChangeToggle}
              />
            </ToggleContainer>
          </label>
        </li>
      </ul>
    </div>
  );
};
export default DropdownItems;

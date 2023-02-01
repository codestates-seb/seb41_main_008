import styled from 'styled-components';
import DropdownItems from './DropdownItems';
import { useAppSelector, useAppDispatch } from '../../hooks/hooks';
import { Link, useNavigate } from 'react-router-dom';
import { faCartShopping, faWallet } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { openModal, openWallet } from '../../store/modalSlice';
import { useQuery } from '@tanstack/react-query';
import customAxios from 'utils/api/axios';
import { UserType } from 'pages/AccountPage';

const DropdownList = styled.li`
  position: relative;
  padding-top: 8px;
  padding-bottom: 8px;
  div {
    top: 45px;
    visibility: hidden;
    transition: 0.3s all;
    opacity: 0;
    right: -50px;
  }
  &:hover {
    div {
      top: 50px;
      visibility: visible;
      opacity: 1;
    }
  }
`;

interface Props {
  isScrolled: boolean;
  home: boolean | undefined;
}

const Dropdown = ({ isScrolled, home }: Props) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { cartItems } = useAppSelector((state) => state.cart);
  const { isLogin } = useAppSelector((state) => state.login);
  const walletState = useAppSelector((state) => state.modal.walletOpen);
  const id = window.localStorage.getItem('MEMBER_ID');
  const { data } = useQuery<UserType>({
    queryKey: ['members', id],
    queryFn: () => customAxios.get(`api/members/${id}`).then((res) => res.data),
  });

  return (
    <nav>
      <ul className="flex items-center justify-center mr-8 ml-2 gap-5">
        <DropdownList className="max-[640px]:hidden">
          {isLogin ? (
            <>
              <button
                onClick={() => navigate(`/account/${data?.member.memberId}`)}
                className="w-8 h-8"
              >
                <img
                  className="object-cover w-full h-full rounded-full"
                  src={data?.member.profileImageName}
                  alt="Profile pic"
                />
              </button>
              <DropdownItems />
            </>
          ) : (
            <Link
              to={'/signup'}
              className={`p-2 ${walletState && 'text-black'} ${
                !isScrolled && home && 'text-white'
              }`}
            >
              {' '}
              Signup
            </Link>
          )}
        </DropdownList>
        {!isLogin && (
          <li className="max-[640px]:hidden">
            {!isLogin && (
              <Link
                to={'/login'}
                className={`p-2 ${walletState && 'text-black'} ${
                  !isScrolled && home && 'text-white'
                }`}
              >
                <span>Login</span>
              </Link>
            )}
          </li>
        )}
        <li>
          <button
            onClick={() => {
              if (!isLogin) {
                alert('로그인이 필요합니다.');
              } else {
                dispatch(openWallet());
              }
            }}
          >
            <FontAwesomeIcon className=" dark:text-white" icon={faWallet} />
          </button>
        </li>
        <li className="flex flex-col relative justify-center w-full leading-6">
          <button
            className="p-2"
            onClick={() => {
              dispatch(openModal());
            }}
          >
            <FontAwesomeIcon
              className=" dark:text-white"
              icon={faCartShopping}
            />
          </button>
          <div
            className={`${
              cartItems.length > 0 && 'animate-bounce'
            } ${'flex justify-center items-center w-5 h-5 rounded-full absolute right-0 -top-2 text-white bg-blue-500 font-bold text-sm '}`}
          >
            {cartItems.length}
          </div>
        </li>
      </ul>
    </nav>
  );
};
export default Dropdown;

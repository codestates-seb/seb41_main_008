import {
  faGreaterThan,
  faHand,
  faPaintBrush,
  faRightToBracket,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { useAppSelector } from '../../hooks/hooks';
import { logout } from 'utils/api/api';

const MobileDropdownItems = () => {
  const { isLogin } = useAppSelector((state) => state.login);
  const memberId = localStorage.getItem('memberId');
  return (
    <>
      <Link
        to={isLogin ? '/' : '/login'}
        className="flex justify-between  hover:text-blue-600 "
      >
        <div className="flex items-center p-6 ">
          <FontAwesomeIcon icon={faRightToBracket} className="mr-2" />
          {isLogin ? (
            <button onClick={logout} className="font-bold text-xl">
              Logout
            </button>
          ) : (
            <div className="font-bold text-xl">Login</div>
          )}
        </div>
        <div className="p-6">
          <FontAwesomeIcon icon={faGreaterThan} />
        </div>
      </Link>

      {isLogin ? null : (
        <Link
          to={'/signup'}
          className="flex justify-between hover:text-blue-600"
        >
          <div className="flex items-center p-6">
            <FontAwesomeIcon icon={faHand} className="mr-2" />
            <div className="font-bold text-xl">Signup</div>
          </div>
          <div className="p-6">
            <FontAwesomeIcon icon={faGreaterThan} />
          </div>
        </Link>
      )}

      <Link
        to={`/account/${memberId}`}
        className="flex justify-between hover:text-blue-600"
      >
        <div className="flex items-center p-6">
          <FontAwesomeIcon icon={faUser} className="mr-2" />
          <div className="font-bold text-xl">Mypage</div>
        </div>
        <div className="p-6">
          <FontAwesomeIcon icon={faGreaterThan} />
        </div>
      </Link>

      {isLogin && (
        <Link
          to={'/collections'}
          className="flex justify-between hover:text-blue-600"
        >
          <div className="flex items-center p-6">
            <FontAwesomeIcon icon={faPaintBrush} className="mr-2" />
            <div className="font-bold text-xl">Create Collection</div>
          </div>
          <div className="p-6">
            <FontAwesomeIcon icon={faGreaterThan} />
          </div>
        </Link>
      )}

      {isLogin && (
        <Link to={'/item'} className="flex justify-between hover:text-blue-600">
          <div className="flex items-center p-6">
            <FontAwesomeIcon icon={faPaintBrush} className="mr-2" />
            <div className="font-bold text-xl">Create NFT</div>
          </div>
          <div className="p-6">
            <FontAwesomeIcon icon={faGreaterThan} />
          </div>
        </Link>
      )}
    </>
  );
};
export default MobileDropdownItems;

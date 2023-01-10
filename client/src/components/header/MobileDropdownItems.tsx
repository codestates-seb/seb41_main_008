import {
  faGreaterThan,
  faHand,
  faPaintBrush,
  faRightToBracket,
  faUser,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { accessToken } from '../../utils/token';
import { logout } from 'utils/api/api';
const MobileDropdownItems = () => {
  return (
    <>
      <Link
        to={accessToken ? '/' : '/login'}
        className="flex justify-between border-b-2"
      >
        <div className="flex items-center p-6">
          <FontAwesomeIcon icon={faRightToBracket} className="mr-2" />
          {accessToken ? (
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

      {accessToken ? null : (
        <Link to={'/signup'} className="flex justify-between border-b-2">
          <div className="flex items-center p-6">
            <FontAwesomeIcon icon={faHand} className="mr-2" />
            <div className="font-bold text-xl">Signup</div>
          </div>
          <div className="p-6">
            <FontAwesomeIcon icon={faGreaterThan} />
          </div>
        </Link>
      )}
      <Link to={'/'} className="flex justify-between border-b-2">
        <div className="flex items-center p-6">
          <FontAwesomeIcon icon={faUser} className="mr-2" />
          <div className="font-bold text-xl">Mypage</div>
        </div>
        <div className="p-6">
          <FontAwesomeIcon icon={faGreaterThan} />
        </div>
      </Link>
      <Link to={'/'} className="flex justify-between border-b-2">
        <div className="flex items-center p-6">
          <FontAwesomeIcon icon={faPaintBrush} className="mr-2" />
          <div className="font-bold text-xl">Create</div>
        </div>
        <div className="p-6">
          <FontAwesomeIcon icon={faGreaterThan} />
        </div>
      </Link>
    </>
  );
};
export default MobileDropdownItems;

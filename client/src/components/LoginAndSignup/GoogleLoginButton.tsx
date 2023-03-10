import { useGoogleLogin } from '@react-oauth/google';
import styled from 'styled-components';
import { googleLogin } from 'store/loginSlice';
import { useAppDispatch } from 'hooks/hooks';
import { useNavigate } from 'react-router-dom';
const GoogleButton = styled.button`
  background-color: white;
  padding: 8px;
  border-radius: 8px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
`;
export type Props = {
  isSignup: boolean;
};
const GoogleLoginButton = ({ isSignup }: Props) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const login = useGoogleLogin({
    onSuccess: (codeResponse: any) => {
      dispatch(googleLogin(codeResponse.access_token)).then((res: any) => {
        if (res.error) {
          alert('탈퇴한 회원은 30일간 회원가입 할 수 없습니다.');
        } else {
          navigate('/');
        }
      });
      // .then(() =>
      //   navigate('/')
      // );
    },
  });
  return (
    <GoogleButton onClick={() => login()} type="button">
      <svg
        aria-hidden="true"
        className="mr-1"
        width="18"
        height="18"
        viewBox="0 0 18 18"
      >
        <path
          d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18Z"
          fill="#4285F4"
        ></path>
        <path
          d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17Z"
          fill="#34A853"
        ></path>
        <path
          d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07Z"
          fill="#FBBC05"
        ></path>
        <path
          d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3Z"
          fill="#EA4335"
        ></path>
      </svg>
      <span>{isSignup ? 'Sign up with Google' : 'Login with Google'}</span>
    </GoogleButton>
  );
};
export default GoogleLoginButton;

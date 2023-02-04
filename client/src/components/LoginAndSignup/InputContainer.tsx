/* eslint-disable */
import Button from './Button';
import GoogleLoginButton from 'components/LoginAndSignup/GoogleLoginButton';
import { useState } from 'react';
import { ErrorMessage } from '@hookform/error-message';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useAppDispatch } from '../../hooks/hooks';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../../store/loginSlice';
import { signup } from '../../store/signupSlice';
import { AiOutlineInfoCircle } from 'react-icons/ai';
import { GoogleOAuthProvider } from '@react-oauth/google';
import { getMyProFile } from 'utils/api/api';
type Props = {
  isSignup: boolean;
};
interface FormValue {
  nickname: string;
  email: string;
  password: string;
}
const InputContainer = ({ isSignup }: Props) => {
  const [unAuth, setUnAuth] = useState(false);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValue>();

  const unAuthHandler = () => {
    setUnAuth(true);
    setTimeout(() => {
      setUnAuth(false);
    }, 1000);
  };

  const onClickSubmit: SubmitHandler<FormValue> = (data) => {
    if (data.nickname) {
      dispatch(signup(data)).then(() => navigate('/login'));
    } else if (!data.nickname) {
      dispatch(login(data)).then((res) => {
        if (res.payload.status === 401) {
          unAuthHandler();
        }
        if (res.meta.requestStatus === 'fulfilled') {
          getMyProFile();
          // .then(() => {
          //   navigate('/', { replace: true });
          // });
        }
      });
    }
  };

  const onError = (error: {}) => {
    console.log(error);
  };
  return (
    <>
      {unAuth && (
        <div className="flex justify-start items-center fixed top-14 text-center  rounded-3xl p-4 bg-black bg-opacity-70 text-white font-semibold left-1/2 -translate-x-1/2 w-96 ">
          <AiOutlineInfoCircle color="red" size="20" />
          <span className="grow">Please check your Email or Password</span>
        </div>
      )}

      <form
        className="flex flex-col p-10  gap-2 shadow-[0px_20px_30px_-10px_rgb(38,57,77)] rounded-md bg-transparent"
        onSubmit={handleSubmit(onClickSubmit, onError)}
      >
        <h1 className="mb-10 font-bold text-3xl inputTextStyle flex-1 text-center">
          {isSignup ? 'Signup' : 'Login'}
        </h1>
        {isSignup ? (
          <p className="font-bold text-center inputTextStyle">
            Sign up and have your own NFT
          </p>
        ) : (
          <p className="font-bold inputTextStyle">
            Let`s go on a trip to the world of NFT
          </p>
        )}

        <GoogleOAuthProvider
          clientId={`${process.env.REACT_APP_GOOGLE_CLIENT_ID}`}
        >
          <GoogleLoginButton isSignup={isSignup} />
        </GoogleOAuthProvider>

        {isSignup && (
          <label htmlFor="nickname" className="font-bold inputTextStyle ">
            NickName
          </label>
        )}
        {isSignup && (
          <input
            id="nickname"
            className="border-b-2 w-full p-1 border-black  focus:outline-none bg-transparent font-bold "
            required
            {...register('nickname', {
              minLength: {
                value: 2,
                message: 'Please enter at least 2 characters.',
              },
            })}
          />
        )}
        {isSignup && (
          <ErrorMessage
            errors={errors}
            name="nickname"
            render={({ message }) => (
              <span className="errorMsg">{message}</span>
            )}
          />
        )}
        <label htmlFor="email" className="font-bold inputTextStyle ">
          Email
        </label>
        <input
          id="email"
          className="border-b-2 w-full p-1 border-black focus:outline-none bg-transparent font-bold placeholder-black inputTextStyle"
          required
          placeholder="ex) codestates@gmail.com"
          {...register('email', {
            pattern: {
              value: /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/,
              message: 'Please enter your email correctly.',
            },
          })}
        />
        <ErrorMessage
          errors={errors}
          name="email"
          render={({ message }) => <span className="errorMsg">{message}</span>}
        />
        <label htmlFor="password" className="font-bold inputTextStyle ">
          Password
        </label>
        <input
          id="password"
          className="border-b-2 w-full p-1 border-black mb-5 focus:outline-none bg-transparent "
          type="password"
          required
          {...register('password', {
            pattern: {
              value:
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/g,
              message: 'Please check the password form.',
            },
          })}
        />

        <ErrorMessage
          errors={errors}
          name="password"
          render={({ message }) => <span className="errorMsg">{message}</span>}
        />

        <p className="text-xs w-[300px] font-semibold inputTextStyle">
          Passwords must contain at least 8 characters, including at least 1
          letter and 1 special character.
        </p>

        <Button>{isSignup ? 'Signup' : 'Login'}</Button>

        <div className="flex justify-between mt-5">
          <span className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
            {isSignup ? (
              <span>Already have account ?</span>
            ) : (
              <span>Don’t have an account?</span>
            )}
          </span>
          {isSignup ? (
            <Link to={'/login'} className="inputTextStyle">
              Login
            </Link>
          ) : (
            <Link to={'/signup'} className="inputTextStyle">
              Sign up
            </Link>
          )}
        </div>
      </form>
    </>
  );
};
export default InputContainer;

import Button from './Button';
import GoogleLogin from 'components/Oauth2.0/GoogleLogin';
import { useState } from 'react';
import { ErrorMessage } from '@hookform/error-message';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useAppSelector, useAppDispatch } from '../../hooks/hooks';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../../store/loginSlice';
import { signup } from '../../store/signupSlice';
import { AiOutlineInfoCircle } from 'react-icons/ai';
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
  const { isFetching } = useAppSelector((state) => state.login);
  console.log(isFetching);

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
          window.location.replace('/');
        }
      });
    }
  };
  const onError = (error: {}) => {
    console.log(error);
  };
  return (
    <div className="">
      {unAuth && (
        <div className="flex justify-start items-center fixed top-14 text-center  rounded-3xl p-4 bg-black bg-opacity-70 text-white font-semibold left-1/2 -translate-x-1/2 w-3/12 ">
          <AiOutlineInfoCircle color="red" size="20" />
          <span className="grow">Please check your Email or Password</span>
        </div>
      )}

      <form
        className="flex flex-col p-10 bg-white  gap-2 shadow-[0px_20px_30px_-10px_rgb(38,57,77)] rounded-md bg-transparent"
        onSubmit={handleSubmit(onClickSubmit, onError)}
      >
        <h1 className="mb-10 font-bold text-3xl text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)] flex-1 text-center">
          {isSignup ? 'Signup' : 'Login'}
        </h1>
        {isSignup ? (
          <p className="font-bold text-center text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
            Sign up and have your own NFT
          </p>
        ) : (
          <p className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
            Let`s go on a trip to the world of NFT
          </p>
        )}
        <GoogleLogin />
        {isSignup && (
          <label
            htmlFor="nickname"
            className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)] "
          >
            NickName
          </label>
        )}
        {isSignup && (
          <input
            id="nickname"
            className="border-b-2 w-72 p-1 border-black  focus:outline-none bg-transparent font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]"
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
              <span className="text-center bg-red-100 border-red-400 border-2 rounded text-red-500 drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
                {message}
              </span>
            )}
          />
        )}
        <label
          htmlFor="email"
          className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)] "
        >
          Email
        </label>
        <input
          id="email"
          className="border-b-2 w-72 p-1 border-black focus:outline-none bg-transparent font-bold placeholder-black text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]"
          required
          placeholder="ex) kanye123@gmail.com"
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
          render={({ message }) => (
            <span className="text-center bg-red-100 border-red-400 border-2 rounded text-red-600 drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
              {message}
            </span>
          )}
        />
        <label
          htmlFor="password"
          className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)] "
        >
          Password
        </label>
        <input
          id="password"
          className="border-b-2 w-72 p-1 border-black mb-5 focus:outline-none bg-transparent "
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
          render={({ message }) => (
            <span className="text-center bg-red-100 border-red-400 border-2 rounded text-red-500 drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
              {message}
            </span>
          )}
        />
        {isSignup && (
          <p className="text-xs w-[300px] text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
            Passwords must contain at least 8 characters, including at least 1
            letter and 1 special character.
          </p>
        )}
        <Button bgColor="">{isSignup ? 'Signup' : 'Login'}</Button>
        <div className="flex justify-between mt-5">
          <span className="font-bold text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]">
            {isSignup ? (
              <span>Already have account ?</span>
            ) : (
              <span>Don’t have an account?</span>
            )}
          </span>
          {isSignup ? (
            <Link
              to={'/login'}
              className="text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]"
            >
              Login
            </Link>
          ) : (
            <Link
              to={'/signup'}
              className="text-white drop-shadow-[0_5px_3px_rgba(0,0,0,0.4)]"
            >
              Sign up
            </Link>
          )}
        </div>
      </form>
    </div>
  );
};
export default InputContainer;

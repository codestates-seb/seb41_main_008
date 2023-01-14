import customAxios from 'utils/api/axios';
const GoogleLogin = () => {
  const googleLogin = () => {
    customAxios
      .get(
        'http://ec2-3-35-204-189.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google'
      )
      .then((res) => console.log(res));
  };
  return (
    <button
      type="button"
      onClick={googleLogin}
      className="bg-white rounded-lg font-bold p-2"
    >
      Google
    </button>
  );
};
export default GoogleLogin;

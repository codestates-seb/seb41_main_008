import InputContainer from '../components/LoginAndSignup/InputContainer';

const LoginPage = () => {
  return (
    <div className="flex justfy-center items-stretch min-h-screen ">
      <video
        autoPlay
        muted
        loop
        className="fixed z-10 h-screen w-full object-cover	"
      >
        <source src="videos/video2.mp4" />
      </video>
      <div className="absolute z-30 flex h-full w-full justify-center items-center">
        <InputContainer isSignup={false} />
      </div>
    </div>
  );
};
export default LoginPage;

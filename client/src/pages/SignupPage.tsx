import InputContainer from '../components/LoginAndSignup/InputContainer';

const SignupPage = () => {
  return (
    <div className="flex justfy-center items-stretch min-h-screen ">
      <video
        autoPlay
        muted
        loop
        className="fixed z-10 h-screen w-full object-cover	"
      >
        <source src="videos/video1.mp4" />
      </video>
      <div className=" top-0 left-0 z-20 h-full w-full bg-gray-900 opacity-40"></div>
      <div className="absolute z-30 flex h-full w-full justify-center items-center">
        <InputContainer isSignup={true} />
      </div>
    </div>
  );
};
export default SignupPage;

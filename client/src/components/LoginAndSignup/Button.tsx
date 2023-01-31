interface Props {
  children: React.ReactNode;
  bgColor?: string;
}

const Button = ({ children, bgColor }: Props) => {
  return (
    <button
      className={`${
        bgColor ? bgColor : 'bg-white text-black '
      } rounded-xl p-2 font-bold text-white`}
    >
      {children}
    </button>
  );
};
export default Button;

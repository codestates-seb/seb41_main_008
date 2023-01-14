const BuynowButton = () => {
  const buynowHandler = () => {
    /**buynow 관련로직 작성 */
    alert('buynow');
  };
  return (
    <div className="grow-0 bg-emerald-700 hover:bg-emerald-600 rounded-br-2xl">
      <button className="border-l-2 h-full w-full p-2 " onClick={buynowHandler}>
        Buynow
      </button>
    </div>
  );
};
export default BuynowButton;

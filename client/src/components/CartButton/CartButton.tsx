const CartButton = () => {
  const cartHandler = () => {
    /**장바구니담는 로직작성 */
    alert('carted');
  };
  return (
    <div className="rounded-bl-xl grow bg-emerald-700 hover:bg-emerald-600 ">
      <button className="h-full w-full p-2" onClick={cartHandler}>
        Add to Cart
      </button>
    </div>
  );
};
export default CartButton;

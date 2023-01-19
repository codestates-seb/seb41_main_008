const CartItems = () => {
  return (
    <li className="flex p-2 items-center hover:bg-[#eef1f1] rounded-lg hover:shadow-md">
      <div className="flex w-[100px] h-[80px] mr-4">
        <img
          className="rounded-lg"
          src="https://i.seadn.io/gcs/files/3c7a925a04b6c17c37395151433d9bea.png?auto=format&w=750"
          alt=""
        />
      </div>
      <div className="flex flex-col w-full ">
        <div className="font-bold">특정nft번호</div>
        <div>특정nft컬렉션이름</div>
      </div>
      <div>
        <div>price</div>
        <button>삭제</button>
      </div>
    </li>
  );
};
export default CartItems;

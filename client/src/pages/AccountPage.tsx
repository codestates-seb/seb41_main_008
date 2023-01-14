import Card from '../components/Card';
const AccountPage = () => {
  return (
    <div className="flex flex-col w-full">
      <div className="h-64 relative ">
        <span className="absolute top-0 left-0 bottom-0 right-0 ">
          <img
            className="absolute top-0 left-0 bottom-0 right-0 object-cover max-w-full max-h-full min-w-full min-h-full h-0 w-0"
            src="https://i.seadn.io/gae/6J0osUqHJKoXWlnNvGr7131EETmpX7lWJJfBWcvIoIzZNUpX65N-0TdR0GsNBZdcmWdo-Y1D2Kj4DPsKBbNwvGwq2micjwwkbgEvTg?auto=format&w=1920"
            alt=""
          />
        </span>
      </div>
      <div className="absolute  mt-32 ml-6 w-[160px] h-[160px] ">
        <img
          className="w-full h-full object-cover rounded-full border-8 "
          alt=""
          src="https://i.seadn.io/gae/6J0osUqHJKoXWlnNvGr7131EETmpX7lWJJfBWcvIoIzZNUpX65N-0TdR0GsNBZdcmWdo-Y1D2Kj4DPsKBbNwvGwq2micjwwkbgEvTg?auto=format&w=1920"
        />
      </div>
      <div className="h-10 w-full "></div>
      <div className="p-10">
        <div className="w-6/12">
          <div className="font-bold text-3xl">닉네임</div>
          <div className="">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Iure rerum,
            exercitationem, voluptatem laborum praesentium nobis
          </div>
        </div>
        <div className="mt-5">
          <ul className="flex flex-row gap-5 text-lg font-semibold ">
            <li className=" border-b-2 border-black">Created</li>
            <li>Collected</li>
          </ul>
        </div>
        <div className="mt-5 grid gap-4 grid-cols-6 max-2xl:grid-cols-6 max-xl:grid-cols-4 max-md:grid-cols-3 max-sm:grid-cols-2  rounded">
          <Card />
          <Card />
          <Card />
          <Card />
          <Card />
          <Card />
          <Card />
          <Card />
        </div>
      </div>
    </div>
  );
};
export default AccountPage;

import { AxiosError } from 'axios';
import DropMenu from 'components/DropdownMenu/DropMenu';
import HoverCardOpen from 'components/HoverCard/HoverCard';
import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import customAxios from 'utils/api/axios';
import { useAppSelector } from 'hooks/hooks';
export default function MyCollectionPage() {
  const { isLogin } = useAppSelector((state) => state.login);
  const navigate = useNavigate();
  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요합니다');
      navigate('/login');
    }
  }, []);
  useEffect(() => {
    const getMyCollections = async () => {
      try {
        const res = await customAxios.get('/api/members/mypage');

        console.log(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err);
      }
    };

    getMyCollections();
  }, []);

  return (
    <div className="max-w-7xl p-6 mx-auto">
      <h1 className="text-5xl font-bold mb-12">My Collections</h1>
      <div className="max-w-sm md:max-w-7xl mx-auto flex flex-col items-center md:block">
        <div className="mb-6">
          <p className="text-lg text-center md:text-justify">
            Create, curate, and manage collections of unique NFTs to share and
            sell.
            <HoverCardOpen />
          </p>
        </div>
        <div className="space-x-5 flex items-center">
          <Link
            to="/collection/create"
            className="hover:opacity-80 bg-emerald-700 px-7 py-5 text-xl font-bold rounded-xl text-white"
          >
            Create a collection
          </Link>
          <DropMenu />
        </div>
      </div>
      <section></section>
    </div>
  );
}

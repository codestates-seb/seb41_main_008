import { useQuery } from '@tanstack/react-query';
import DropMenu from 'components/MyCollection/DropMenu';
import HoverCardOpen from 'components/MyCollection/HoverCard';
import { Link } from 'react-router-dom';
import customAxios from 'utils/api/axios';

interface Collection {
  collectionId: number;
  collectionName: string;
  logoImgName: string;
  bannerImgName: string;
}

export default function MyCollection() {
  const { isLoading, error, data } = useQuery<Collection[]>({
    queryKey: ['myCollections'],
    queryFn: async () => {
      const res = await customAxios.get('/api/members/mypage');
      return res.data.collections;
    },
  });

  if (isLoading) return <p>Loading...</p>;

  if (error instanceof Error)
    return <p>An error has occurred: + {error.message}</p>;

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

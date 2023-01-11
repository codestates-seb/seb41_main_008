import Dropdown from 'components/DropdownMenu/DropdownMenu';
import HoverCardOpen from 'components/HoverCard/HoverCard';
import { Link } from 'react-router-dom';

export default function MyCollectionPage() {
  return (
    <div className="max-w-7xl p-6 m-auto">
      <h1 className="text-5xl font-bold mb-12">My Collections</h1>
      <div className="max-w-sm md:max-w-7xl mx-auto flex flex-col items-center md:block">
        <div className="mb-6 ">
          <p className="text-lg text-center md:text-justify">
            Create, curate, and manage collections of unique NFTs to share and
            sell.
            <HoverCardOpen />
          </p>
        </div>
        <div className="space-x-5 flex items-center">
          <Link
            to="/collection/create"
            className="hover:opacity-80 bg-brand-color px-7 py-5 text-xl font-bold rounded-xl text-white"
          >
            Create a collection
          </Link>
          <Dropdown />
        </div>
      </div>
    </div>
  );
}

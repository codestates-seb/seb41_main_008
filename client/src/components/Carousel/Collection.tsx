import { Link } from 'react-router-dom';
import { Props } from './MainCollection';

interface ColProp extends Props {
  description: string;
}

export default function Collection({
  id,
  name,
  coin,
  logo,
  description,
}: ColProp) {
  return (
    <Link to={`/collection/${id.toString()}`} className="relative">
      <img
        src={coin}
        alt="Coin"
        className="h-6 w-6 absolute top-1.5 left-1.5"
      />
      <img
        src={logo}
        alt="collection"
        className="rounded-t-md h-3/4 w-full object-cover"
      />
      <div className="px-3.5 pt-3.5 space-y-2 sm:space-y-0 md:pt-1 h-1/4">
        <h3 className="font-bold">{name}</h3>
        <p className="text-gray-400 font-bold w-full truncate">{description}</p>
      </div>
    </Link>
  );
}

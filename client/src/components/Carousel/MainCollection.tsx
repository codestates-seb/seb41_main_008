import { Link } from 'react-router-dom';

export interface Props {
  name: string;
  coin: string;
  logo: string;
  id: number;
}

export default function MainCollection({ id, name, coin, logo }: Props) {
  return (
    <Link
      to={`/collection/${id.toString()}`}
      className="relative group overflow-hidden bg-transparent inline-block rounded-2xl aspect-square"
    >
      <div className="absolute z-10 w-full h-1/3 bottom-0 bg-gradient-to-t from-black" />
      <img
        src={logo}
        alt="collection"
        className="group-hover:scale-110 w-full h-full duration-500 object-cover"
      />
      <h3 className="w-full truncate pl-1.5 pb-1.5 z-20 absolute bottom-0 font-bold text-lg text-white">
        {name}
      </h3>
      <img
        src={coin}
        alt="Coin"
        className="h-6 w-6 absolute top-1.5 left-1.5"
      />
    </Link>
  );
}

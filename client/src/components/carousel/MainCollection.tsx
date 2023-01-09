interface Piece {
  name: string;
  price: number;
  url: string;
  volume?: number;
}

export default function MainCollection({ url, price, name }: Piece) {
  return (
    <div className="relative group cursor-pointer overflow-hidden rounded-2xl">
      <div className="absolute z-10 w-full h-1/3 bottom-0 bg-gradient-to-t from-black" />
      <img
        src={url}
        alt="collection"
        className="group-hover:scale-105 aspect-square duration-500 object-cover"
      />
      <div className="z-20 absolute bottom-0 pl-2 pb-1">
        <h3 className="font-bold text-lg text-white">{name}</h3>
        <p className="text-white">Floor: {price / 10} ETH</p>
      </div>
    </div>
  );
}

interface Piece {
  name: string;
  price: number;
  url: string;
  volume?: number;
}

export default function Collection({ url, price, name, volume }: Piece) {
  return (
    <div className="rounded-md aspect-square bg-white cursor-pointer shadow-md hover:shadow-lg  hover:-translate-y-2 duration-300">
      <img
        src={url}
        alt="collection"
        className="rounded-t-md h-2/3 w-full object-cover"
      />
      <div className="px-3.5 pt-2 space-y-2">
        <h3 className="font-bold">{name}</h3>
        <div className="flex justify-between">
          <div>
            <h2 className="uppercase text-gray-400 text-xs font-bold">floor</h2>
            <span className="font-bold">{price} ETH</span>
          </div>
          <div>
            <h2 className="uppercase text-gray-400 text-xs font-bold">
              total volume
            </h2>
            <span className="font-bold">{volume} ETH</span>
          </div>
        </div>
      </div>
    </div>
  );
}

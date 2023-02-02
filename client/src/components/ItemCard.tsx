import { Item } from 'pages/Search';
import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function ItemCard({
  itemImageName,
  itemName,
  itemPrice,
  itemId,
  coinName,
  coinImage,
  collectionName,
}: Item) {
  const [show, setShow] = useState(false);

  return (
    <Link
      className="inline-block group"
      to={`/item/${itemId}`}
      onMouseEnter={() => setShow(true)}
      onMouseLeave={() => setShow(false)}
    >
      <article
        className="relative aspect-[3/4] cursor-pointer shadow-md group-hover:shadow-lg duration-500
                   group rounded-md"
      >
        <img
          src={coinImage}
          alt="Coin"
          className={`z-10 absolute top-1.5 left-1.5 w-8 h-8 duration-300 ${
            show ? 'opacity-100' : 'opacity-0'
          }`}
        />
        <div className="overflow-hidden w-full rounded-t-md absolute h-3/5">
          <img
            src={itemImageName}
            alt="collection"
            className="h-full w-full object-cover group-hover:scale-[115%]
                       duration-500"
          />
        </div>
        <div className="h-2/5 absolute bottom-0 w-full">
          <div className="p-4 font-bold">
            <h3 className="text-sm sm:text-base truncate">{itemName}</h3>
            <h3 className="text-sm sm:text-base truncate mb-1 sm:mb-3 md:mb-6">
              {collectionName}
            </h3>
            <h3 className="text-xs sm:text-sm truncate text-[#707A83]">
              Last sale: {itemPrice + ' ' + coinName}{' '}
            </h3>
          </div>
        </div>
      </article>
    </Link>
  );
}

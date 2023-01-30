import { useQuery } from '@tanstack/react-query';
import { MemberInfo } from 'pages/AccountPage';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import customAxios from 'utils/api/axios';

export interface Item {
  itemImageName: string;
  itemName: string;
  itemPrice: string;
  itemId: number;
  coinName: string;
  coinImage: string;
  onSale: boolean;
  ownerId: number;
}

export default function ItemCard({
  itemImageName,
  itemName,
  itemPrice,
  itemId,
  coinName,
  coinImage,
  onSale,
  ownerId,
}: Item) {
  const [show, setShow] = useState(false);
  const { data } = useQuery<MemberInfo>({
    queryKey: ['member', 'mypage'],
    queryFn: () =>
      customAxios.get('/api/member/mypage').then((res) => res.data.member),
  });

  return (
    <Link
      className="inline-block"
      to={`/items/${itemId}`}
      onMouseEnter={() => setShow(true)}
      onMouseLeave={() => setShow(false)}
    >
      <article
        className="relative aspect-[3/5] cursor-pointer shadow-md
    group rounded-2xl"
      >
        <img
          src={coinImage}
          alt="Coin"
          className={`z-10 absolute top-1.5 left-1.5 w-8 h-8 duration-300 ${
            show ? 'opacity-100' : 'opacity-0'
          }`}
        />
        <div className="overflow-hidden rounded-t-2xl absolute h-[70%]">
          <img
            src={itemImageName}
            alt="collection"
            className="h-full rounded-t-2xl w-full object-cover group-hover:scale-[115%]
        duration-500"
          />
        </div>
        <div className="h-[30%] absolute bottom-0 w-full">
          <div className="text-xs md:text-sm lg:text-base xl:text-lg justify-between md:justify-evenly flex flex-col py-2.5 md:py-2 spacy-y- px-2 h-2/3 font-bold">
            <h3 className="truncate leading-none text-[#707A83]">{itemName}</h3>
            <h3 className="truncate leading-none">
              {itemPrice + ' ' + coinName}{' '}
            </h3>
          </div>
          {!onSale ? null : ownerId === data?.memberId ? (
            <button
              onClick={(e) => e.preventDefault()}
              className={`${
                !show ? 'opacity-0' : 'opacity-100'
              } text-xs md:text-sm lg:text-base xl:text-lg flex items-center w-full h-1/3 justify-center duration-300 BasicButton rounded-b-2xl font-bold`}
            >
              <p>List for sale</p>
            </button>
          ) : (
            <button
              onClick={(e) => e.preventDefault()}
              className={`${
                !show ? 'opacity-0' : 'opacity-100'
              } text-xs md:text-sm lg:text-base xl:text-lg flex items-center w-full h-1/3 justify-center duration-300 BasicButton rounded-b-2xl font-bold`}
            >
              <p>Add to cart</p>
            </button>
          )}
        </div>
      </article>
    </Link>
  );
}

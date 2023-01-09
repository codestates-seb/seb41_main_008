import { useEffect, useState } from 'react';
import axios, { AxiosError } from 'axios';

interface user {
  firstName: string;
  lastName: string;
  avatar: string;
  id: string;
}

export default function Carousel() {
  const [collection, setCollection] = useState<user[]>();

  useEffect(() => {
    const test = async () => {
      try {
        const res = await axios.get(
          'https://random-data-api.com/api/v2/users?size=12'
        );
        setCollection(res.data);
      } catch (error) {
        const err = error as AxiosError;
        console.log(err.response?.data);
      }
    };

    test();
  }, []);

  return (
    <div className="p-3 grid grid-cols-444 grid-flow-row overflow-x-scroll">
      {collection?.map((col) => (
        <div className="" key={col.id}>
          <img src={col.avatar} alt="collection" className="w-full" />
        </div>
      ))}
    </div>
  );
}

import { Link } from 'react-router-dom';
import { SearchCol } from 'pages/MyCollection';

export default function ColResult({
  collectionId,
  logoImgName,
  bannerImgName,
  collectionName,
}: SearchCol) {
  return (
    <Link
      to={`/collection/${collectionId}`}
      className="relative w-full h-full bg-gray-100 rounded-2xl overflow-hidden aspect-square inline-block"
    >
      <img
        src={process.env.REACT_APP_IMAGE + bannerImgName}
        alt="Collection banner"
        className="h-[70%] w-full object-cover -mb-3"
      />

      <img
        src={process.env.REACT_APP_IMAGE + logoImgName}
        alt="Collection logo"
        className="h-20 w-20 sm:h-16 duration-300 group-hover:scale-110 sm:w-16 xl:h-20 xl:w-20 rounded-lg shadow-xl text-lg sm:text-base xl:text-lg border-4 border-white object-cover absolute left-3"
      />

      <h3 className="text-gray-600 font-bold w-3/5 text-lg left-28 sm:left-[84px] xl:left-28 absolute mt-12 sm:mt-8 xl:mt-12 truncate">
        {collectionName}
      </h3>
    </Link>
  );
}

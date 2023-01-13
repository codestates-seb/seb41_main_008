/* eslint-disable */

import { Link } from 'react-router-dom';

export default function MissingPage() {
  return (
    <div className="flex flex-col justify-center space-y-5 sm:space-y-10 h-[calc(100vh-74.667px)] items-center max-w-2xl p-6 mx-auto">
      <h1 className="font-bold text-3xl sm:text-5xl text-center">
        This page <br className="sm:hidden" />
        is lost.
      </h1>
      <p className="text-lg sm:text-2xl text-center text-gray-500">
        We've explored deep and wide, <br className="sm:hidden" /> but we can't
        find the page you were looking for.
      </p>
      <Link
        to="/"
        className="bg-emerald-700 w-fit hover:opacity-80 rounded-xl py-3 px-5 sm:py-5 sm:px-7 font-bold text-base sm:text-xl text-white"
      >
        Navigate back home
      </Link>
    </div>
  );
}

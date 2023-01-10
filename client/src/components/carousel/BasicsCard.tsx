/* eslint-disable */

export default function BasicsCard({ url }: { url: string }) {
  return (
    <div className="rounded-md aspect-square cursor-pointer shadow-md hover:shadow-lg hover:-translate-y-2 duration-300">
      <img
        src={url}
        alt="collection"
        className="rounded-t-md h-2/3 w-full object-cover"
      />
      <div className="h-1/3">
        <h5>What is NFT?</h5>
      </div>
    </div>
  );
}

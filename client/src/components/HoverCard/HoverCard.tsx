import * as HoverCard from '@radix-ui/react-hover-card';
import { BiInfoCircle } from 'react-icons/bi';

export default function HoverCardOpen() {
  return (
    <HoverCard.Root>
      <HoverCard.Trigger asChild>
        <button className="ml-2 align-top">
          <BiInfoCircle className="h-7 w-7 text-gray-500" />
        </button>
      </HoverCard.Trigger>
      <HoverCard.Portal>
        <HoverCard.Content
          align="end"
          sideOffset={5}
          className="HoverCardContent"
        >
          <HoverCard.Arrow className="HoverCardArrow" />
          <p className="font-bold">
            Collections can be created either directly on OpenSea or imported
            from an existing smart contract. You can also mint on other services
            like Rarible or Mintable and import the items to OpenSea.{' '}
            <a
              href="https://opensea.io/blog/announcements/introducing-the-collection-manager/"
              className="text-brand-color"
              target="_blank"
              rel="noreferrer"
            >
              Learn more about creating NFTs for free on OpenSea
            </a>
          </p>
        </HoverCard.Content>
      </HoverCard.Portal>
    </HoverCard.Root>
  );
}

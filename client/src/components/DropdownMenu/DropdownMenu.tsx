import { BiDotsVerticalRounded } from 'react-icons/bi';
import * as DropdownMenu from '@radix-ui/react-dropdown-menu';

export default function Dropdown() {
  return (
    <DropdownMenu.Root>
      <DropdownMenu.Trigger asChild>
        <button className="hover:box-shadow border-2 border-gray-200 p-[22px] rounded-xl">
          <BiDotsVerticalRounded className="h-6 w-6" />
        </button>
      </DropdownMenu.Trigger>

      <DropdownMenu.Portal>
        <DropdownMenu.Content
          sideOffset={10}
          onCloseAutoFocus={(e) => e.preventDefault()}
          className="DropdownMenuContent"
          align="end"
        >
          <a
            href="https://studio.manifold.xyz/"
            target="_blank"
            rel="noreferrer"
          >
            <DropdownMenu.Item className="DropdownMenuItem">
              Mint with Manifold Studio
            </DropdownMenu.Item>
          </a>

          <a
            href="https://rarible.com/create/start"
            target="_blank"
            rel="noreferrer"
          >
            <DropdownMenu.Item className="DropdownMenuItem">
              Mint on Rarible
            </DropdownMenu.Item>
          </a>

          <a href="https://www.mintbase.io/" target="_blank" rel="noreferrer">
            <DropdownMenu.Item className="DropdownMenuItem">
              Mint on Mintbase
            </DropdownMenu.Item>
          </a>

          <a href="https://mintable.app/" target="_blank" rel="noreferrer">
            <DropdownMenu.Item className="DropdownMenuItem">
              Mint on Mintable
            </DropdownMenu.Item>
          </a>

          <a href="https://zora.co/" target="_blank" rel="noreferrer">
            <DropdownMenu.Item className="DropdownMenuItem">
              Mint on Zora
            </DropdownMenu.Item>
          </a>
        </DropdownMenu.Content>
      </DropdownMenu.Portal>
    </DropdownMenu.Root>
  );
}

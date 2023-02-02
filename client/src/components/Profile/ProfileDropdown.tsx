import * as DropdownMenu from '@radix-ui/react-dropdown-menu';
import { Link } from 'react-router-dom';
import { IoSettingsSharp } from 'react-icons/io5';
import Alert from './Alert';

export default function ProfileDropdown({ id }: { id: string }) {
  return (
    <DropdownMenu.Root>
      <DropdownMenu.Trigger asChild>
        <button>
          <IoSettingsSharp className="h-6 w-6" />
        </button>
      </DropdownMenu.Trigger>

      <DropdownMenu.Portal>
        <DropdownMenu.Content
          sideOffset={10}
          onCloseAutoFocus={(e) => e.preventDefault()}
          className="DropdownMenuContent"
          align="end"
        >
          <Link to={'/account/profile'}>
            <DropdownMenu.Item className="DropdownMenuItem">
              Edit profile
            </DropdownMenu.Item>
          </Link>

          <Alert id={id} />
        </DropdownMenu.Content>
      </DropdownMenu.Portal>
    </DropdownMenu.Root>
  );
}

import * as RadioGroup from '@radix-ui/react-radio-group';
import { Blockchain } from './CreateCollection';

interface Coin {
  selectedCoin: Blockchain | null;
  setCoin: React.Dispatch<React.SetStateAction<Blockchain | null>>;
}

export default function RadioButtons({ selectedCoin, setCoin }: Coin) {
  let id: number;

  const setValue = (name: string) => {
    switch (name) {
      case 'Solana':
        id = 1;
        break;

      case 'Bitcoin':
        id = 2;
        break;

      case 'Dogecoin':
        id = 3;
        break;

      case 'Ethereum':
        id = 4;
        break;

      case 'Ethereum Classic':
        id = 5;
        break;
    }
    setCoin({ name, id });
  };

  return (
    <div className="flex-1">
      <RadioGroup.Root
        className="RadioGroupRoot"
        defaultValue={selectedCoin?.name}
        aria-label="View density"
        onValueChange={setValue}
      >
        <div className="flex items-center">
          <RadioGroup.Item className="RadioGroupItem" value="Solana" id="b1">
            <RadioGroup.Indicator className="RadioGroupIndicator" />
          </RadioGroup.Item>
          <label className="Label" htmlFor="b1">
            Solana
          </label>
        </div>
        <div className="flex items-center">
          <RadioGroup.Item className="RadioGroupItem" value="Bitcoin" id="b2">
            <RadioGroup.Indicator className="RadioGroupIndicator" />
          </RadioGroup.Item>
          <label className="Label" htmlFor="b2">
            Bitcoin
          </label>
        </div>
        <div className="flex items-center">
          <RadioGroup.Item className="RadioGroupItem" value="Dogecoin" id="b3">
            <RadioGroup.Indicator className="RadioGroupIndicator" />
          </RadioGroup.Item>
          <label className="Label" htmlFor="b3">
            Dogecoin
          </label>
        </div>
        <div className="flex items-center">
          <RadioGroup.Item className="RadioGroupItem" value="Ethereum" id="b4">
            <RadioGroup.Indicator className="RadioGroupIndicator" />
          </RadioGroup.Item>
          <label className="Label" htmlFor="b4">
            Ethereum
          </label>
        </div>
        <div className="flex items-center">
          <RadioGroup.Item
            className="RadioGroupItem"
            value="Ethereum Classic"
            id="b5"
          >
            <RadioGroup.Indicator className="RadioGroupIndicator" />
          </RadioGroup.Item>
          <label className="Label" htmlFor="b5">
            Ethereum Classic
          </label>
        </div>
      </RadioGroup.Root>
    </div>
  );
}

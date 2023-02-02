/* eslint-disable */
import { SetStateAction, useState } from 'react';
import styles from './Top.module.css';
import Trending from '../Trending/Trending';
import Coin from '../Trending/Coin';
import DayDropDown from './DayDropDown';
import CoinFilter from './CoinFilter';





const Home = () => {
  const [option, setOption] = useState('DAY');
  const [coinId, setCoin] = useState(1);
  const [activeTab, setActiveTab] = useState('trending');
    const onTabChange = (tab: SetStateAction<string>) => {
      setActiveTab(tab);
    };
  return (
    <main className={styles.gridContainer}>
      <section
        className={`${styles.gridItem} ${styles.section} ${styles.statsSection}`}
      >
        <header className={`${styles.statsHeader}`}>
          <div className={styles.tabContainer}>
            <button
              className={`${styles.tabBtn} ${
                activeTab === 'trending' && styles.tabBtnActive
              }`}
              onClick={() => onTabChange('trending')}
            >
              TOP
            </button>
            <button
              className={`${styles.tabBtn} ${
                activeTab === 'top' && styles.tabBtnActive
              }`}
              onClick={() => onTabChange('top')}
            >
              COIN
            </button>
          </div>
          <div>
            <CoinFilter
              setCoin={setCoin}
              onCoinClick={() => {
                console.log(coinId);
              }}
            />
          </div>
          <div>
            <DayDropDown
              setOption={setOption}
              onOptionClick={() => {
                console.log(option);
              }}
            />
          </div>
        </header>

        {activeTab === 'trending' && (
          <>
            <div>
              <Trending option={option} />
            </div>
          </>
        )}

        {activeTab === 'top' && (
          <>
            <div>
              <Coin coinId={coinId} />
            </div>
          </>
        )}
      </section>
    </main>
  );
};

export default Home;

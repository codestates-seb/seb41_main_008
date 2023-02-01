/* eslint-disable */
import { useState } from 'react';
import styles from './Top.module.css';
import Trending from '../Trending/Trending';
import DayDropDown from './DayDropDown';
import CoinFilter from './CoinFilter';




const Home = () => {
  const [option, setOption] = useState('DAY');
  return (
    <main className={styles.gridContainer}>
      <section
        className={`${styles.gridItem} ${styles.section} ${styles.statsSection}`}
      >
        <header className={`${styles.statsHeader}`}>
          <div className={styles.tabContainer}>
            <button
              className={`${styles.tabBtn} ${
                 styles.tabBtnActive
              }`}
            >
              TOP
            </button>
          </div>
          <div>
            {/* <CoinFilter  /> */}
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
        <Trending option={option}/>
      </section>
    </main>
  );
};

export default Home;

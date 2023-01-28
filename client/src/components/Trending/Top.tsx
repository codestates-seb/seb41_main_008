/* eslint-disable */
import { useState } from 'react';
import styles from './Top.module.css';
import Trending from '../Trending/Trending';
import { IoIosArrowDown } from 'react-icons/io';
import DayDropdown from './DayDropDown';
const Home = () => {
  const [activeTab] = useState('trending');
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
            >
              TOP
            </button>
          </div>
          <div> 
            <DayDropdown
              onOptionClick={(option) => {
              console.log(option)
            }}
             options={['DAY', 'WEEK', 'MONTH']} />
          </div>
        </header>
        <Trending />
      </section>
    </main>
  );
};

export default Home;

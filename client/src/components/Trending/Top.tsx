/* eslint-disable */
import { useState } from 'react';
import styles from './Top.module.css';
import Trending from '../Trending/Trending';
import { IoIosArrowDown } from 'react-icons/io';

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
              Trending
            </button>
          </div>
          <div className={styles.filterBtnsContainer}>
            <button className={styles.filterBtn}>
              <span> 24h </span>
              <IoIosArrowDown />
              <span
                className={`material-symbols-outlined ${styles.btnExpandIcon}`}
              ></span>
            </button>
          </div>
        </header>
        {activeTab === 'trending' && <Trending />}
      </section>
    </main>
  );
};

export default Home;

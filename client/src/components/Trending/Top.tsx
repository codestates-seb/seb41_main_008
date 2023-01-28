/* eslint-disable */
import { useState } from 'react';
import styles from './Top.module.css';
import Trending from '../Trending/Trending';
import { IoIosArrowDown } from 'react-icons/io';

const Home = () => {
  const [activeTab] = useState('trending');
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
                activeTab === 'trending' && styles.tabBtnActive
              }`}
            >
              TOP
            </button>
          </div>
          <div></div>
        </header>
        <Trending option={option} />
      </section>
    </main>
  );
};

export default Home;

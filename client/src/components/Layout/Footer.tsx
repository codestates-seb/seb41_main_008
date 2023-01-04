import React from 'react'; // eslint-disable-line no-unused-vars
import { BsTwitter, BsYoutube } from 'react-icons/bs';
import { AiOutlineInstagram } from 'react-icons/ai';
import { FaDiscord, FaRedditAlien, FaTiktok } from 'react-icons/fa';

const styles = {
  box: `cursor-pointer w-[45px] h-[45px] sm:w-[60px] sm:h-[60px] rounded-lg hover:bg-[#3f86ff] dark:hover:bg-[#2b5cae] dark:bg-[#3674de] bg-[#1867b7] flex justify-center items-center`,
  icon: `text-white cursor-pointer dark:hover:text-white`,
};

function Footer() {
  return (
    <>
      <div className="mt-auto flex justify-center items-center text-2xl sm:text-3xl bg-[#2b5cae] gap-[.75em] h-[200px]">
        <a href="https://twitter.com/opensea?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor">
          <div className={styles.box}>
            <BsTwitter className={styles.icon} />
          </div>
        </a>
        <a href="https://www.instagram.com/opensea/">
          <div className={styles.box}>
            <AiOutlineInstagram className={styles.icon} />
          </div>
        </a>
        <a href="https://discord.com/invite/opensea">
          <div className={styles.box}>
            <FaDiscord className={styles.icon} />
          </div>
        </a>
        <a href="https://www.reddit.com/r/opensea/">
          <div className={styles.box}>
            <FaRedditAlien className={styles.icon} />
          </div>
        </a>
        <a href="https://www.youtube.com/c/OpenSeaTV">
          <div className={styles.box}>
            <BsYoutube className={styles.icon} />
          </div>
        </a>
        <a href="https://www.tiktok.com/@opensea">
          <div className={styles.box}>
            <FaTiktok className={styles.icon} />
          </div>
        </a>
      </div>
    </>
  );
}

export default Footer;

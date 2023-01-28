/* eslint-disable */
import { ReactComponent as LogoWhite } from '../../assets/icons/logo-white.svg';
import { ReactComponent as TwitterIcon } from '../../assets/icons/twitter.svg';
import { ReactComponent as InstagramIcon } from '../../assets/icons/instagram.svg';
import { ReactComponent as DiscordIcon } from '../../assets/icons/discord.svg';
import { ReactComponent as RedditIcon } from '../../assets/icons/reddit.svg';
import { ReactComponent as YoutubeIcon } from '../../assets/icons/youtube.svg';
import { ReactComponent as TiktokIcon } from '../../assets/icons/tiktok.svg';
import styles from './Footer.module.css';

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div className={`${styles.flexContainer} ${styles.row}`}>
        <div className={styles.newsletter}>
          <h2>Introducing Page</h2>
          <p>
            Our product ideas have been primarily built on financial instruments
            that build NFTs.With multiple Financial Instruments as the lever and
            NFT trading as the fulcrum, stimulate, maintain and accelerate the
            growth of NFT liquidity in a phased
          </p>
          <form className={styles.form}>
          </form>
        </div>
        <div className={styles.socials}>
          <h2>Join the community</h2>
          <div>
            <a href="/" className={styles.socialLink}>
              <button className={styles.socialBtn}>
                <TwitterIcon />
              </button>
            </a>
            <button className={styles.socialBtn}>
              <InstagramIcon />
            </button>
            <button className={styles.socialBtn}>
              <DiscordIcon />
            </button>
            <button className={styles.socialBtn}>
              <RedditIcon />
            </button>
            <button className={styles.socialBtn}>
              <YoutubeIcon />
            </button>
            <button className={styles.socialBtn}>
              <TiktokIcon />
            </button>
          </div>
        </div>
      </div>
      <div className={styles.copyrightContainer}>
        <p className={styles.copyright}>© 2018 - 2023 Ozone Networks, Inc</p>
        <div>
          <a href="/" className={styles.policy}>
            Privacy Policy
          </a>
          <a href="/" className={styles.policy}>
            Terms of Service
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

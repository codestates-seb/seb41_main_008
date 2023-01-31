/* eslint-disable */
import { ReactComponent as LogoWhite } from '../../assets/icons/logo-white.svg';
import { ReactComponent as TwitterIcon } from '../../assets/icons/twitter.svg';
import { ReactComponent as InstagramIcon } from '../../assets/icons/instagram.svg';
import { ReactComponent as DiscordIcon } from '../../assets/icons/discord.svg';
import { ReactComponent as RedditIcon } from '../../assets/icons/reddit.svg';
import { ReactComponent as YoutubeIcon } from '../../assets/icons/youtube.svg';
import { ReactComponent as TiktokIcon } from '../../assets/icons/tiktok.svg';
import profile1 from '../../assets/profile/65396939.png';
import profile2 from '../../assets/profile/2.png';
import profile3 from '../../assets/profile/3.png';
import profile4 from '../../assets/profile/4.png';
import profile5 from '../../assets/profile/5.png';
import profile6 from '../../assets/profile/6.png';

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
          <form className={styles.form}></form>
        </div>
        <div className={styles.socials}>
          <h2>Contributor's github</h2>
          <div>
            <a href="https://github.com/jaehak24" className={styles.socialLink}>
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile1} alt="profile1" />
              </button>
            </a>
            <button className={styles.socialBtn}>
              <img className="rounded-full " src={profile2} alt="profile2" />
              <p></p>
            </button>
            <button className={styles.socialBtn}>
              <img className="rounded-full " src={profile3} alt="profile3" />
            </button>
            <button className={styles.socialBtn}>
              <img className="rounded-full " src={profile4} alt="profile4" />
            </button>
            <button className={styles.socialBtn}>
              <img className="rounded-full " src={profile5} alt="profile5" />
            </button>
            <button className={styles.socialBtn}>
              <img className="rounded-full " src={profile6} alt="profile6" />
            </button>
          </div>
          <div></div>
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

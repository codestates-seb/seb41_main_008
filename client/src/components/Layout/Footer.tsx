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
    <footer className={`${styles.footer} ${'dark:bg-[#04121d]'}`}>
      <div className={`${styles.flexContainer} ${styles.row}`}>
        <div className={styles.newsletter}>
          <h2>About Project.</h2>
          <p>
            Hello. We are NFTEAM. We wanted to make one-stop NFT Trade SHOP. You
            can do all From coin purchases To NFT transactions right here. Make
            your own NFT And Own your own NFT.
          </p>
          <form className={styles.form}></form>
        </div>
        <div className={styles.socials}>
          <h2>Contributor's github</h2>
          <div>
            <a
              href="https://github.com/jaehak24"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile1} alt="profile1" />
              </button>
            </a>

            <a
              href="https://github.com/sojeongLee0125"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile2} alt="profile2" />
              </button>
            </a>

            <a
              href="https://github.com/ShinHB417"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile3} alt="profile3" />
              </button>
            </a>

            <a
              href="https://github.com/taejinii"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile4} alt="profile4" />
              </button>
            </a>

            <a
              href="https://github.com/kongalabear"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile5} alt="profile5" />
              </button>
            </a>

            <a
              href="https://github.com/Valentin1495"
              target="_blank"
              className={styles.socialLink}
            >
              <button className={styles.socialBtn}>
                <img className="rounded-full " src={profile6} alt="profile6" />
              </button>
            </a>
          </div>
          <div></div>
        </div>
      </div>
      <div className={styles.copyrightContainer}>
        <p className={styles.copyright}>© 2022 - 2023 NFTeam, Inc</p>
        <div>
          <a
            href="https://github.com/codestates-seb/seb41_main_008"
            target="_blank"
            className={styles.policy}
          >
            Guide
          </a>
          <a
            href="https://www.notion.so/codestates/41-TEAM-008-NFTEAM-b5519f19a8c1470cbb5ca29167f5a02a"
            target="_blank"
            className={styles.policy}
          >
            Github
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

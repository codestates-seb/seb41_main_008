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
          <h2>Stay in the loop</h2>
          <p>
            Join our mailing list to stay in the loop with our newest feature
            releases, NFT drops, and tips and tricks for navigating OpenSea.
          </p>
          <form className={styles.form}>
            <input
              type="email"
              className={styles.signupInput}
              placeholder="Your email address"
            />
            <button type="submit" className={styles.signupButton}>
              Sign up
            </button>
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
            <a href="/" className={styles.socialLink}></a>
          </div>
        </div>
      </div>
      <div className={`${styles.flexContainer} ${styles.row}`}>
        <div className={styles.companyInfo}>
          <div>
            <LogoWhite className={styles.logo} />
          </div>
          <h2 className={styles.name}>OpenSea</h2>
          <p className={styles.description}>
            The world’s first and largest digital marketplace for crypto
            collectibles and non-fungible tokens (NFTs). Buy, sell, and discover
            exclusive digital items.
          </p>
        </div>
        <div className={styles.navigation}>
          <div>
            <h3>MarketPlace</h3>
            <ul>
              <li>
                <a href="/">All NFTs</a>
              </li>
              <li>
                <a href="/">Salana NFTs</a>
              </li>
              <li>
                <a href="/">Art</a>
              </li>
              <li>
                <a href="/">Collectibles</a>
              </li>
              <li>
                <a href="/">Domain Names</a>
              </li>
              <li>
                <a href="/">Music</a>
              </li>
              <li>
                <a href="/">Photography</a>
              </li>
              <li>
                <a href="/">Sports</a>
              </li>
              <li>
                <a href="/">Trading Cards</a>
              </li>
              <li>
                <a href="/">Utility</a>
              </li>
              <li>
                <a href="/">Virtual Worlds</a>
              </li>
            </ul>
          </div>
          <div className={styles.flexCol}>
            <div>
              <h3>My Account</h3>
              <ul>
                <li>
                  <a href="/">Profile</a>
                </li>
                <li>
                  <a href="/">Favorites</a>
                </li>
                <li>
                  <a href="/">WatchList</a>
                </li>
                <li>
                  <a href="/">My Collections</a>
                </li>
                <li>
                  <a href="/">Settings</a>
                </li>
              </ul>
            </div>
            <div>
              <h3>Stats</h3>
              <ul>
                <li>
                  <a href="/">Rankings</a>
                </li>
                <li>
                  <a href="/">Activity</a>
                </li>
              </ul>
            </div>
          </div>
          <div>
            <h3>Resources</h3>
            <ul>
              <li>
                <a href="/">Learn</a>
              </li>
              <li>
                <a href="/">Help Center</a>
              </li>
              <li>
                <a href="/">Platform Status</a>
              </li>
              <li>
                <a href="/">Partners</a>
              </li>
              <li>
                <a href="/">Taxes</a>
              </li>
              <li>
                <a href="/">Blog</a>
              </li>
              <li>
                <a href="/">Docs</a>
              </li>
              <li>
                <a href="/">Newsletter</a>
              </li>
            </ul>
          </div>
          <div>
            <h3>Company</h3>
            <ul>
              <li>
                <a href="/">About</a>
              </li>
              <li>
                <a href="/">Careers</a>
              </li>
              <li>
                <a href="/">Ventures</a>
              </li>
              <li>
                <a href="/">Grants</a>
              </li>
            </ul>
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

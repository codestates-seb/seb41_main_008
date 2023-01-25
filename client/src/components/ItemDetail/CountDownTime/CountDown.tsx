/* eslint-disable */
import { useEffect, useState } from 'react';
import styles from '../CountDownTime/CountDown.module.css';
// import { clearInterval } from 'timers';

const CountdownTimer = () => {
    const [days, setDays] = useState(0)
    const [hours, setHours] = useState(0);
    const [minutes, setMinutes] = useState(0);
    const [seconds, setSeconds] = useState(0);

    useEffect(() => {
      const target = new Date('01/31/2023 23:59:59');

      const interval = setInterval(() => {
        const now = new Date();
        const difference = target.getTime() - now.getTime();

        const d = Math.floor(difference / (1000 * 60 * 60 * 24));
        setDays(d);

        const h = Math.floor(
          (difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        );
        setHours(h);

        const m = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
        setMinutes(m);

        const s = Math.floor((difference % (1000 * 60)) / 1000);
        setSeconds(s);
      }, 1000);

      return () => clearInterval(interval);
    }, [])
      return (
        <div className={styles.timerwrapper}>
          <div className={styles.timerinner}>
            <div className={styles.timer}>
              <span className={styles.time}>{days}</span>
              <span className={styles.label }>Days</span>
            </div>
            <span className={styles.divider}>:</span>
            <div className={styles.timer}>
              <span className={styles.time}>{hours}</span>
              <span className={styles.label}>Hours</span>
            </div>
            <span className={styles.divider}>:</span>
            <div className={styles.timer}>
              <span className={styles.time}>{minutes}</span>
              <span className={styles.label}>Minutes</span>
            </div>
            <span className={styles.divider}>:</span>
            <div className={styles.timer}>
              <span className={styles.time}>{seconds}</span>
              <span className={styles.label}>Seconds</span>
            </div>
          </div>
        </div>
      );






}
export default CountdownTimer;
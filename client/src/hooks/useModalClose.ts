import { useRef, useEffect } from 'react';
import { useAppDispatch } from './hooks';

const useModalClose = (isOpen: boolean, callback: any) => {
  const ref = useRef<HTMLDivElement>(null);
  const dispatch = useAppDispatch();
  useEffect(() => {
    const modalClose = (e: MouseEvent) => {
      if (isOpen && ref.current?.contains(e.target as Node)) {
        dispatch(callback);
      }
    };
    document.addEventListener('click', modalClose);
    return () => {
      document.removeEventListener('click', modalClose);
    };
  }, [isOpen, callback, dispatch]);
  return ref;
};
export default useModalClose;

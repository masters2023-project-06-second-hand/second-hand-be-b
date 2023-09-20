import { atom } from 'jotai';

type ToastState = {
  message: string;
  visible: boolean;
  type: 'success' | 'error' | 'noti';
};

export const toastAtom = atom<ToastState>({
  message: '',
  visible: false,
  type: 'noti',
});

export const animationAtom = atom<'slideUp' | 'slideDown' | null>(null);

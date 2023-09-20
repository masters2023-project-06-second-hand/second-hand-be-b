import { useSetAtom } from 'jotai';
import { toastAtom, animationAtom } from '@atoms/toastAtom';

type ToastOptions = {
  text: string;
  type: 'success' | 'error' | 'noti';
};

export function useToast() {
  const setToastState = useSetAtom(toastAtom);
  const setAnimation = useSetAtom(animationAtom);

  const show = (options: ToastOptions) => {
    const { text, type = 'noti' } = options;

    setToastState({
      message: text,
      visible: true,
      type,
    });
    setAnimation('slideUp');

    setTimeout(() => {
      setAnimation('slideDown');
    }, 2700);

    setTimeout(() => {
      setToastState((prev) => ({ ...prev, visible: false }));
    }, 3000);
  };

  const success = (message: string) => {
    show({ text: message, type: 'success' });
  };

  const error = (message: string) => {
    show({ text: message, type: 'error' });
  };

  const noti = (message: string) => {
    show({ text: message, type: 'noti' });
  };

  return { success, error, noti };
}

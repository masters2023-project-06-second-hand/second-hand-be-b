import ReactDOM from 'react-dom';
import styled, { keyframes } from 'styled-components';
import { useAtom } from 'jotai';
import { toastAtom, animationAtom } from '@atoms/toastAtom';
import { Icon } from '@components/Icon/Icon';

export const Toast: React.FC = () => {
  const [toastState, setToastState] = useAtom(toastAtom);
  const [animation, setAnimation] = useAtom(animationAtom);

  const handleAnimationEnd = () => {
    if (animation === 'slideDown') {
      setToastState((prev) => ({ ...prev, visible: false }));
      setAnimation(null);
    }
  };

  if (animation === null) return null;

  return ReactDOM.createPortal(
    <ToastWrapper>
      <Text
        $animation={animation || 'slideDown'}
        $type={toastState.type}
        onAnimationEnd={handleAnimationEnd}
      >
        {toastState.type === 'error' && (
          <Icon fill="#ff6243" name="circleXFilled" stroke="none" />
        )}
        <p>{toastState.message}</p>
      </Text>
    </ToastWrapper>,
    document.body
  );
};

const slideUp = keyframes`
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
`;

const slideDown = keyframes`
  from {
    transform: translateY(0);
    opacity: 1;
  }
  to {
    transform: translateY(100%);
    opacity: 0;
  }
`;

const ToastWrapper = styled.div`
  width: 377px;
  margin: 0 auto;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

/* 디자인이 따로 정해져있는게 없어서 그냥 막 작성합니다 ^^... */
const Text = styled.div<{
  $animation: 'slideUp' | 'slideDown';
  $type: 'success' | 'error' | 'noti';
}>`
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0;
  position: absolute;
  bottom: 70px;
  padding: 8px 16px;
  width: ${({ $type }) => ($type === 'success' ? 'max-content' : '100%')};
  background-color: ${({ $type }) => ($type === 'error' ? '#fff' : '#212123')};
  border: 1px solid;
  border-radius: ${({ $type, theme: { radius } }) =>
    $type === 'success' ? radius.large : radius.small};
  font: ${({ $type, theme: { font } }) =>
    $type === 'error' ? font.availableStrong12 : font.displayDefault12};
  color: ${({ $type }) => ($type === 'error' ? '#ff6243' : '#e2e2e2')};
  z-index: 1000;
  transform: translateX(-50%) translateY(0);
  animation:
    ${slideUp} 0.3s,
    ${slideDown} 0.3s 2.7s;
  animation-fill-mode: forwards;
  animation: ${({ $animation }) =>
      $animation === 'slideUp' ? slideUp : slideDown}
    0.3s;
  animation-fill-mode: forwards;
`;

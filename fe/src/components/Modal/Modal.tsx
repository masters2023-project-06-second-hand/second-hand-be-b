import { styled } from 'styled-components';
import { useModal } from './useModal';
import { AlertModalProps } from './types';

/* Todo. 모달들 파일 분리하기 */

export const AlertModal: React.FC<AlertModalProps> = ({
  style,
  onDelete,
  message,
  leftButtonText,
  rightButtonText,
}) => {
  const { closeModal } = useModal();

  return (
    <AlertWrapper style={style}>
      <Message>{message}</Message>
      <Action>
        <button onClick={closeModal}>{leftButtonText}</button>
        <button onClick={onDelete}>{rightButtonText}</button>
      </Action>
    </AlertWrapper>
  );
};

const AlertWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 336px;
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  border-radius: ${({ theme: { radius } }) => radius.large};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
`;
const Message = styled.div`
  padding: 24px;
  font: ${({ theme: { font } }) => font.displayStrong16};
`;
const Action = styled.div`
  padding: 24px;
  display: flex;
  gap: 32px;
  justify-content: flex-end;

  button:first-child {
    color: ${({ theme: { color } }) => color.neutralTextStrong};
    font: ${({ theme: { font } }) => font.displayDefault16};
  }
  button:last-child {
    color: ${({ theme: { color } }) => color.systemWarning};
    font: ${({ theme: { font } }) => font.displayStrong16};
  }
`;

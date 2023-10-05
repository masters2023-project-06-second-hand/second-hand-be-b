import { ReactNode } from 'react';
import { styled } from 'styled-components';
import { useDropdown } from './useDropdown';

type DropdownProps = {
  trigger: ReactNode;
  children: ReactNode;
  position?: 'top-left' | 'top-right' | 'bottom-left' | 'bottom-right';
  size?: 'L' | 'S';
};

export const Dropdown: React.FC<DropdownProps> = ({
  trigger,
  children,
  position = 'bottom-left',
  size = 'L',
}) => {
  const { isOpen, toggleDropdown, dropdownRef } = useDropdown();

  return (
    <Wrapper ref={dropdownRef}>
      <Trigger onClick={toggleDropdown}>{trigger}</Trigger>
      {isOpen && (
        <>
          <Overlay onClick={toggleDropdown} />
          <List $position={position} $size={size} onClick={toggleDropdown}>
            {children}
          </List>
        </>
      )}
    </Wrapper>
  );
};

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: rgba(0, 0, 0, 0.2);
`;

const Wrapper = styled.div`
  position: relative;
`;

const Trigger = styled.div``;

const POSITION_STYLES = {
  'top-left': `
    bottom: 100%;
    left: 0;
  `,
  'top-right': `
    bottom: 100%;
    right: 0;
  `,
  'bottom-left': `
    top: 100%;
    left: 0;
  `,
  'bottom-right': `
    top: 100%;
    right: 0;
  `,
};

const List = styled.ul<{
  $position: 'top-left' | 'top-right' | 'bottom-left' | 'bottom-right';
  $size: 'L' | 'S';
}>`
  position: absolute;
  ${({ $position }) => POSITION_STYLES[$position]}

  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  font: ${({ $size, theme: { font } }) =>
    $size === 'L' ? font.availableDefault16 : font.displayDefault12};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  width: ${({ $size }) => ($size === 'L' ? '240px' : '140px')};
  align-items: flex-start;

  li {
    width: 100%;
    text-align: left;
    padding: ${({ $size }) => ($size === 'L' ? '16px' : '14px')};
    border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};

    &:last-child {
      border-bottom: none;
    }
  }
`;

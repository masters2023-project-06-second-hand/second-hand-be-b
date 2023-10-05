import { Icon } from '@components/Icon/Icon';
import { CategoryModalProps } from './types';
import { useModal } from './useModal';
import styled from 'styled-components';

export const CategoryModal: React.FC<CategoryModalProps> = ({
  style,
  selectedCategoryId,
  categoryData,
  onClick,
}) => {
  const { closeModal } = useModal();

  return (
    <ModalBase style={style}>
      <Header>
        <h3>카테고리</h3>
        <ButtonBase onClick={closeModal}>
          <Icon name="x" />
        </ButtonBase>
      </Header>
      <List>
        {categoryData.map((category) => (
          <Option
            key={category.id}
            $isSelected={selectedCategoryId === category.id}
            onClick={() => {
              closeModal();
              onClick(category.id);
            }}
          >
            {category.name}
          </Option>
        ))}
      </List>
    </ModalBase>
  );
};

const ModalBase = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 320px;
  height: 700px;
  border: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  border-radius: ${({ theme: { radius } }) => radius.large};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  overflow: hidden;
`;

const ButtonBase = styled.button`
  width: 48px;
  height: 48px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Header = styled.div`
  padding: 8px 8px 8px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const List = styled.ul`
  padding: 0 24px;
  overflow-y: scroll;
  height: 100%;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const Option = styled.li<{ $isSelected: boolean }>`
  font: ${({ $isSelected, theme: { font } }) =>
    $isSelected ? font.displayStrong16 : font.availableDefault16};
  padding: 16px 0;
  border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  &:last-child {
    border-bottom: none;
  }
  cursor: pointer;
`;

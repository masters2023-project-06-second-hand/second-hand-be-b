import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import { styled, keyframes } from 'styled-components';
import {
  useChangeProductStatusMutation,
  useDeleteProductMutation,
} from '@api/product/product';
import { QUERY_KEYS } from '@api/queryKey';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import { selectedCategoryIdAtom } from '@atoms/categoryAtom';
import { useModal } from '@components/Modal/useModal';

type Status = '판매중' | '판매완료' | '예약중';

type Menu = {
  id: string;
  name: string;
};

type BottomMenuProps = {
  productId: number;
  menuList: Menu[];
  onClose: () => void;
};

export const BottomMenu: React.FC<BottomMenuProps> = ({
  menuList,
  onClose,
  productId,
}) => {
  const [userRegions] = useAtom(userRegionsAtom);
  const [selectedCategoryId] = useAtom(selectedCategoryIdAtom);
  const productsQueryKey = QUERY_KEYS.PRODUCTS(
    userRegions.selectedRegion.id,
    selectedCategoryId
  );
  const changeProductStatus = useChangeProductStatusMutation(productsQueryKey);
  const deleteProduct = useDeleteProductMutation(productsQueryKey);
  const { openModal, closeModal } = useModal();
  const [isLeaving, setIsLeaving] = useState(false);

  const handleOnClose = () => {
    setIsLeaving(true);
    setTimeout(onClose, 300);
  };

  const handleStatusChange = (status: Status) => {
    changeProductStatus.mutate({
      productId,
      status,
    });
  };

  const handleEdit = () => {
    // 리태 수정 클릭 시 로직 추가 부탁드려요
  };

  const handleDelete = () => {
    openModal('alert', {
      message: `선택한 상품을 삭제하시겠습니까?`,
      leftButtonText: '취소',
      rightButtonText: '삭제',
      onDelete: () => {
        deleteProduct.mutate(productId);
        closeModal();
      },
    });
  };

  const menuActions: Record<string, () => void> = {
    판매중: () => handleStatusChange('판매중'),
    판매완료: () => handleStatusChange('판매완료'),
    예약중: () => handleStatusChange('예약중'),
    삭제: handleDelete,
    수정: handleEdit,
  };

  const handleMenuClick = (menuId: keyof typeof menuActions) => {
    const action = menuActions[menuId];
    if (action) action();
    handleOnClose();
  };

  const modalRoot = document.getElementById('modal-root');

  return modalRoot
    ? ReactDOM.createPortal(
        <>
          <Overlay onClick={handleOnClose} />
          <Wrapper isLeaving={isLeaving}>
            <List>
              {menuList.map((menu) => (
                <Option key={menu.id} onClick={() => handleMenuClick(menu.id)}>
                  {menu.name}
                </Option>
              ))}
            </List>
          </Wrapper>
        </>,
        modalRoot
      )
    : '오류가 발생해 메뉴가 뜨지 않습니다.';
};

const slideDown = keyframes`
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(100%);
  }
`;

const slideUp = keyframes`
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
`;

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 393px;
  left: 50%;
  transform: translateX(-50%);
  height: 100vh;
  z-index: 1;
  background-color: rgba(0, 0, 0, 0.2);
`;

const Wrapper = styled.div<{ isLeaving: boolean }>`
  animation: ${(props) => (props.isLeaving ? slideDown : slideUp)} 0.3s;
  position: fixed;
  bottom: 0;
  width: 100%;
  display: flex;
  overflow: hidden;
  justify-content: center;
  z-index: 2;
`;

const List = styled.ul`
  width: 393px;
  margin: auto;
  border-radius: 16px 16px 0 0;
  background-color: ${({ theme: { color } }) => color.neutralBackground};
`;

const Option = styled.li`
  text-align: center;
  line-height: 56px;
  height: 56px;
  border-bottom: 0.8px solid ${({ theme: { color } }) => color.neutralBorder};

  &:last-child {
    border-bottom: none;
    color: ${({ theme: { color } }) => color.systemWarning};
  }
  &:first-child {
    border-radius: 16px 16px 0 0;
  }
`;

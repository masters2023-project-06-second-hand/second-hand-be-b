import { styled } from 'styled-components';
import { Button } from '@components/Button/Button';
import { RegionSettingModalProps } from './types';
import { useModal } from './useModal';
import { Icon } from '@components/Icon/Icon';
import {
  useAddUserRegionMutation,
  useDeleteUserRegionMutation,
  useSetUserRegionMutation,
} from '@api/region/region';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import extractRegionName from '@utils/extractRegionName';
import { useToast } from '@components/Toast/useToast';

type Region = {
  name: string;
  id: number;
};

export const RegionSettingModal: React.FC<RegionSettingModalProps> = ({
  style,
}) => {
  const { openModal, closeModal } = useModal();
  const setUserRegion = useSetUserRegionMutation();
  const deleteUserRegion = useDeleteUserRegionMutation();
  const [userRegions] = useAtom(userRegionsAtom);
  const toast = useToast();
  const myRegionCount = userRegions.regions.length;
  const addUserRegion = useAddUserRegionMutation();

  const openConfirmAlert = (region: Region) => {
    openModal('alert', {
      message: `${region.name}을(를) 삭제하시겠습니까?`,
      leftButtonText: '취소',
      rightButtonText: '삭제',
      onDelete: () => {
        deleteUserRegion(region.id);
        closeModal();
      },
    });
  };

  const handleSelectedRegion = (id: number) => {
    setUserRegion(id);
  };

  const handleDeleteClick = (
    e: React.MouseEvent<HTMLButtonElement>,
    region: Region
  ) => {
    e.stopPropagation();

    if (myRegionCount <= 1) {
      toast.noti('지역은 최소 1개 이상 설정해야 합니다.');
      return;
    }

    openConfirmAlert(region);
  };

  const handleAddButtonClick = () => {
    if (myRegionCount >= 2) {
      toast.noti('지역은 최대 2개까지만 설정 가능합니다.');
      return;
    }

    openModal('searchRegion', {
      onSelectRegion: (regionId) => {
        const isRegionAlreadyAdded = userRegions.regions.some(
          (region) => region.id === regionId
        );

        if (isRegionAlreadyAdded) {
          toast.noti('이미 추가된 지역입니다.');
          return;
        }

        addUserRegion(regionId);
        closeModal();
      },
    });
  };

  return (
    <ModalBase style={style}>
      <Header>
        <h3>동네설정</h3>
        <ButtonBase onClick={closeModal}>
          <Icon name="x" />
        </ButtonBase>
      </Header>
      <Content>
        <NoticeText>
          지역은 최소 1개,
          <br /> 최대 2개까지 설정 가능해요.
        </NoticeText>
        <RegionList>
          {userRegions.regions.map((region) => (
            <Item
              key={region.id}
              $isSelected={userRegions.selectedRegion.id === region.id}
              onClick={() => handleSelectedRegion(region.id)}
            >
              <p>{extractRegionName(region.name)}</p>
              <DeleteButton onClick={(e) => handleDeleteClick(e, region)}>
                <Icon fill="accentText" name="circleXFilled" stroke="none" />
              </DeleteButton>
            </Item>
          ))}
        </RegionList>
        <Button
          backgroundColor="accentText"
          icon="plus"
          onClick={handleAddButtonClick}
          size="M"
          text="추가"
        />
      </Content>
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

const Header = styled.div`
  padding: 8px 8px 8px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const ButtonBase = styled.button`
  width: 48px;
  height: 48px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const NoticeText = styled.p`
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralText};
  text-align: center;
  margin-bottom: 32px;
`;

const Content = styled.div`
  padding: 40px 16px;
`;

const RegionList = styled.ul`
  font: ${({ theme: { font } }) => font.availableStrong16};
  color: ${({ theme: { color } }) => color.accentText};
`;

const Item = styled.li<{ $isSelected: boolean }>`
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${({ $isSelected, theme: { color } }) =>
    $isSelected ? color.accentPrimary : color.neutralTextWeak};
  border-radius: ${({ theme: { radius } }) => radius.small};
  margin-bottom: 8px;
`;
const DeleteButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
`;

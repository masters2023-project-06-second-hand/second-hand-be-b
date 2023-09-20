import { styled } from 'styled-components';
import { Button } from '@components/Button/Button';
import { RegionSettingModalProps } from './types';
import { useModal } from './useModal';
import { Icon } from '@components/Icon/Icon';
import { useSetUserRegionMutation } from '@api/region/region';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import extractRegionName from '@utils/extractRegionName';

export const RegionSettingModal: React.FC<RegionSettingModalProps> = ({
  style,
}) => {
  const { openModal, closeModal } = useModal();
  const setUserRegion = useSetUserRegionMutation();
  const [userRegions] = useAtom(userRegionsAtom);

  const openConfirmAlert = (regionName: string) => {
    openModal('alert', {
      message: `${regionName}을(를) 삭제하시겠습니까?`,
      leftButtonText: '취소',
      rightButtonText: '삭제',
      onDelete: () => {
        console.log(`${regionName} deleted!`);
      },
    });
  };

  const handleSelectedRegion = (id: number) => {
    setUserRegion(id);
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
              <DeleteButton
                onClick={(e) => {
                  e.stopPropagation();
                  openConfirmAlert(region.name);
                }}
              >
                <Icon fill="accentText" name="circleXFilled" stroke="none" />
              </DeleteButton>
            </Item>
          ))}
        </RegionList>
        <Button
          backgroundColor="accentText"
          icon="plus"
          onClick={() => openModal('searchRegion', {})}
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

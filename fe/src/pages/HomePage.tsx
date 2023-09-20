import { styled } from 'styled-components';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import { Header } from '@components/Header/Header';
import { TextButton } from '@components/Button/TextButton';
import { Icon } from '@components/Icon/Icon';
import { RegionDropdown } from '@components/Dropdown/ReigonDropdown';
import { ProductItem } from '@components/Item/ProductItem';
import { FabButton } from '@components/Button/FabButton';
import { usePageNavigator } from '@hooks/usePageNavigator';
import {
  useSetUserRegionMutation,
  useGetUserReigions,
} from '@api/region/region';
import extractRegionName from '@utils/extractRegionName';

const items = [
  {
    id: 1,
    writerId: 1,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 콜라',
    region: '개포1동',
    createdAt: '2023-09-01 14:22',
    status: '예약중',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 2,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 3,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 4,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 5,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 6,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 7,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
  {
    id: 8,
    writerId: 2,
    thumbnailUrl: 'https://avatars.githubusercontent.com/u/142768426?s=48&v=4',
    name: '코카 제로 콜라',
    region: '개포2동',
    createdAt: '2023-09-01 17:22',
    status: '판매완료',
    price: 23000,
    likeCount: 2,
    chattingCount: 1,
  },
];

export const HomePage = () => {
  const { navigateToCategory } = usePageNavigator();
  useGetUserReigions();

  const setUserRegion = useSetUserRegionMutation();
  const [userRegions] = useAtom(userRegionsAtom);

  const handleSelectRegion = (id: number) => {
    setUserRegion(id);
  };

  return (
    <>
      <Header>
        <Header.Left>
          <RegionDropdown
            trigger={
              <TextButton size="M" textColor="neutralTextStrong">
                {extractRegionName(userRegions.selectedRegion.name)}
                <Icon name="chevronDown" size="M" stroke="neutralTextStrong" />
              </TextButton>
            }
            myRegions={userRegions.regions}
            selectedRegionId={userRegions.selectedRegion.id}
            onSelectRegion={handleSelectRegion}
          />
        </Header.Left>
        <Header.Right>
          <button
            style={{
              padding: '8px',
            }}
            onClick={navigateToCategory}
          >
            <Icon name="layoutGrid" size="M" stroke="neutralTextStrong" />
          </button>
        </Header.Right>
      </Header>

      <Content>
        {items.map((item) => (
          <ProductItem item={item} key={item.id} />
        ))}
      </Content>

      <AddPageButtonWrapper>
        <FabButton />
      </AddPageButtonWrapper>
    </>
  );
};

const Content = styled.div`
  padding: 56px 16px 56px 16px;
  overflow-y: scroll;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const AddPageButtonWrapper = styled.div`
  position: absolute;
  bottom: 88px;
  width: 100px;
  right: -20px;
`;

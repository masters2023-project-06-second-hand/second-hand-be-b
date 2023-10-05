import { useMemo } from 'react';
import { styled } from 'styled-components';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import { selectedCategoryIdAtom } from '@atoms/categoryAtom';
import { Header } from '@components/Header/Header';
import { TextButton } from '@components/Button/TextButton';
import { Icon } from '@components/Icon/Icon';
import { RegionDropdown } from '@components/Dropdown/ReigonDropdown';
import { ProductItem } from '@components/Item/ProductItem';
import { FabButton } from '@components/Button/FabButton';
import { usePageNavigator } from '@hooks/usePageNavigator';
import { useInfiniteScroll } from '@hooks/useInfiniteScroll';
import {
  useSetUserRegionMutation,
  useGetUserReigions,
} from '@api/region/region';
import { useProducts } from '@api/product/productList';
import extractRegionName from '@utils/extractRegionName';

export const HomePage = () => {
  const { navigateToCategory } = usePageNavigator();
  useGetUserReigions();

  const setUserRegion = useSetUserRegionMutation();
  const [userRegions] = useAtom(userRegionsAtom);
  const [selectedCategoryId, setSeletedCategoryId] = useAtom(
    selectedCategoryIdAtom
  );

  const handleSelectRegion = (id: number) => {
    setUserRegion(id);
  };

  const handleResetCategory = () => {
    setSeletedCategoryId(null);
  };

  const { data, hasNextPage, fetchNextPage, isLoading, isFetching, error } =
    useProducts(userRegions.selectedRegion.id, selectedCategoryId);

  const flattenedData = useMemo(() => {
    if (data) {
      const flattened = data.pages.flatMap((item) => item.products);
      return flattened;
    }
    return [];
  }, [data]);

  const lastElementRef = useInfiniteScroll({
    isLoading,
    hasNextPage,
    fetchNextPage,
    isFetching,
  });

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
          <CategoryButton onClick={navigateToCategory}>
            <Icon name="layoutGrid" size="M" stroke="neutralTextStrong" />
          </CategoryButton>
        </Header.Right>
      </Header>

      <Content>
        {selectedCategoryId !== null && (
          <ResetCategoryButton onClick={handleResetCategory}>
            전체 카테고리 목록으로 돌아가기
          </ResetCategoryButton>
        )}

        {isLoading ? (
          <LoadingMessage>
            <p>상품 리스트를 가져오는 중입니다...</p>
          </LoadingMessage>
        ) : error ? (
          <ErrorMessage>
            <p>상품 리스트를 가져오는 데 실패했습니다.</p>
          </ErrorMessage>
        ) : (
          <>
            {flattenedData &&
              flattenedData.map((item, index) => (
                <ProductItem
                  item={item}
                  key={item.id}
                  ref={
                    index === flattenedData.length - 1 ? lastElementRef : null
                  }
                />
              ))}
            {!hasNextPage && (
              <EndMessage>더 이상의 리스트가 존재하지 않습니다.</EndMessage>
            )}
          </>
        )}
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

const CategoryButton = styled.button`
  padding: 8px;
`;

const ResetCategoryButton = styled.button`
  padding: 8px 16px;
  margin-top: 16px;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.neutralText};
  border-radius: ${({ theme: { radius } }) => radius.medium};
`;

const EndMessage = styled.p`
  margin: 0 auto;
  font: ${({ theme: { font } }) => font.displayDefault12};
  padding: 56px 16px 56px 16px;
`;

const CenteredMessage = styled.div`
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayDefault12};
`;

const LoadingMessage = styled(CenteredMessage)``;
const ErrorMessage = styled(CenteredMessage)``;

const AddPageButtonWrapper = styled.div`
  position: absolute;
  bottom: 88px;
  width: 100px;
  right: -20px;
`;

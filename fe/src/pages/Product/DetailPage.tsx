import { styled } from 'styled-components';
import { ActionBar } from '@components/ActionBar/ActionBar';
import { EditBar } from '@components/ActionBar/EditBar';
import { PostBar } from '@components/ActionBar/PostBar';
import { TextButton } from '@components/Button/TextButton';
import { Header } from '@components/Header/Header';
import { Icon } from '@components/Icon/Icon';
import { SellerInfo } from '@components/SellerInfo/SellerInfo';
import { States } from '@components/States/States';
import { usePageNavigator } from '@hooks/usePageNavigator';
import displayTimeAgo from '@utils/displayTimeAgo';
import { useAtom } from 'jotai';
import { userInfoAtom } from '@atoms/userAtom';
import { MenuDropdown } from '@components/Dropdown/MenuDropdown';
import { STATES_OF_PRODUCT } from '@constants/constants';
import { useModal } from '@components/Modal/useModal';
import {
  useChangeProductStatusMutation,
  useDeleteProductMutation,
} from '@api/product/product';
import { Product, ProductStatus } from '@api/product/types';
import extractRegionName from '@utils/extractRegionName';
import { QUERY_KEYS } from '@api/queryKey';

export const DetailPage = ({
  productData,
  goEditPage,
}: {
  productData: Product;
  goEditPage(): void;
}) => {
  const [userInfo] = useAtom(userInfoAtom);
  const { navigateToGoBack } = usePageNavigator();
  const { openModal } = useModal();
  const changeProductStatus = useChangeProductStatusMutation(
    QUERY_KEYS.PRODUCT_DETAIL(productData.id)
  );
  const deleteProduct = useDeleteProductMutation(
    QUERY_KEYS.PRODUCT_DETAIL(productData.id)
  );

  const openConfirmAlert = (productName: string) => {
    openModal('alert', {
      message: `${productName}을(를) 삭제하시겠습니까?`,
      leftButtonText: '취소',
      rightButtonText: '삭제',
      onDelete: () => {
        deleteProduct.mutate(productData.id);
      },
    });
  };

  const isWriter = userInfo?.id !== productData.writer.id;

  const stat = {
    chattingCount: 2,
    likeCount: 2,
    viewCount: 2,
    isLiked: true,
  };

  return (
    <>
      <Header backgroundColor="none">
        <Header.Left>
          <TextButton
            size="M"
            textColor="accentText"
            onClick={navigateToGoBack}
          >
            <Icon name="chevronLeft" size="M" stroke="accentText" />
            뒤로
          </TextButton>
        </Header.Left>
        {!isWriter && (
          <MenuDropdown
            trigger={
              <Header.Right>
                <MenuButton>
                  <Icon name="dots" stroke="accentText" />
                </MenuButton>
              </Header.Right>
            }
            position="bottom-right"
          >
            <li onClick={goEditPage}>게시물 수정</li>
            <li onClick={() => openConfirmAlert(productData.productName)}>
              삭제
            </li>
          </MenuDropdown>
        )}
      </Header>
      <Content>
        <ImgContent>
          <ProductImg src={productData.images[0].imgUrl} />
        </ImgContent>
        <InfoContent>
          <SellerInfo nickname={productData.writer.nickname} />
          <MenuDropdown
            trigger={<States name={productData.status} />}
            position="bottom-left"
          >
            {STATES_OF_PRODUCT.filter(
              (status) => status !== productData.status
            ).map((statusList: ProductStatus) => (
              <li
                key={statusList}
                onClick={() => {
                  changeProductStatus.mutate({
                    productId: productData.id,
                    status: statusList,
                  });
                }}
              >
                {statusList}
              </li>
            ))}
          </MenuDropdown>
          <Title>
            <ProductName>{productData.productName}</ProductName>
            <ProductInfo>
              {productData.categoryName} ・{' '}
              {displayTimeAgo(productData.createdAt)}
            </ProductInfo>
          </Title>
          <ProductContent>{productData.content}</ProductContent>
          <ProductStats>
            채팅 {stat.chattingCount} 관심 {stat.likeCount} 조회{' '}
            {stat.viewCount}
          </ProductStats>
        </InfoContent>
      </Content>
      <ActionBar>
        {isWriter ? (
          <EditBar regionName={extractRegionName(productData.regionName)} />
        ) : (
          <PostBar
            id={productData.id}
            isLiked={stat.isLiked}
            price={productData.price}
          />
        )}
      </ActionBar>
    </>
  );
};

const MenuButton = styled.button`
  padding: 8px;
`;

const Content = styled.div`
  margin-top: -56px;
  overflow-y: auto;
  width: 100%;
  height: 100%;
  display: flex;
  gap: 16px;
  flex-direction: column;
`;

const ImgContent = styled.div`
  width: 100%;
  height: 400px;
`;

const ProductImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const InfoContent = styled.div`
  padding: 0px 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const Title = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const ProductName = styled.span`
  width: 100%;
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const ProductInfo = styled.p`
  width: 100%;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralTextWeak};
`;

const ProductContent = styled.p`
  padding: 16px 0px;
  width: 100%;
  font: ${({ theme: { font } }) => font.displayDefault16};
  color: ${({ theme: { color } }) => color.neutralText};
`;

const ProductStats = styled.p`
  width: 100%;
  font: ${({ theme: { font } }) => font.displayDefault12};
  color: ${({ theme: { color } }) => color.neutralTextWeak};
`;

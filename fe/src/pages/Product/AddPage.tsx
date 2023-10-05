import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { ActionBar } from '@components/ActionBar/ActionBar';
import { EditBar } from '@components/ActionBar/EditBar';
import { AddImgInput } from '@components/AddImgInput/AddImgInput';
import { TextButton } from '@components/Button/TextButton';
import { Header } from '@components/Header/Header';
import { Input } from '@components/Input/Input';
import { PriceInput } from '@components/Input/PriceInput';

import { Tag } from '@components/Tag/Tag';
import { Textarea } from '@components/Textarea/Textarea';
import { useInput } from '@hooks/useInput';
import { usePrice } from '@hooks/usePrice';
import { Categories } from '@api/category/types';
import { useCategoriesWithoutImages } from '@api/category/useCategories';
import generateRecommendCategory from '@utils/generateRecommendCategory';
import { Product } from '@api/product/types';
import { Icon } from '@components/Icon/Icon';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import { usePageNavigator } from '@hooks/usePageNavigator';
import extractRegionName from '@utils/extractRegionName';
import { useModal } from '@components/Modal/useModal';
import { ImgProps } from '@api/images/type';
import { usePostProductMutation } from '@api/product/product';
import { useDeleteImgMutation, usePostImgMutation } from '@api/images/images';
import { PictureItem } from '@components/PictureItem/PictureItem';

export const AddPage = ({
  productData,
  goDetailPage,
}: {
  productData?: Product;
  goDetailPage?(): void;
}) => {
  const category = useCategoriesWithoutImages();
  const { openModal } = useModal();
  const { navigateToGoBack } = usePageNavigator();
  const [userRegions] = useAtom(userRegionsAtom);
  const { value: name, onChange: onChangeName } = useInput(
    productData?.productName
  );
  const { value: price, onChange: onChangePrice } = usePrice(
    productData?.price.toString()
  );
  const { value: content, onChange: onChangeContent } = useInput(
    productData?.content
  );
  const [selectedRegion, setSelectedRegion] = useState<number>(
    userRegions.selectedRegion.id
  );
  const [selectedCategory, setSelectedCategory] = useState<number | null>(
    productData ? productData.category.id : null
  );
  const [imgData, setImgData] = useState<ImgProps[]>(
    productData ? productData.images : []
  );
  const [recommendCategories, setRecommendCategories] =
    useState<Categories[]>();
  const hasName = name;

  const postData = {
    name: name,
    categoryId: selectedCategory!,
    price: price.replace(/,/g, ''),
    content: content,
    regionId: selectedRegion,
    imagesId: imgData.map((item) => item.id),
  };

  const isValid = !!imgData && !!name && !!selectedCategory && !!content;

  const uploadProductImg = (newImgData: ImgProps) => {
    setImgData([...imgData, newImgData]);
  };

  const deleteProductImg = (imgId: number) => {
    const updatedImgData = imgData.filter((img) => img.id !== imgId);
    setImgData(updatedImgData);
  };

  const selectRegion = (regionId: number) => {
    setSelectedRegion(regionId);
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (files) {
      postImg.mutate(files);
    }
  };

  const postNewProduct = usePostProductMutation();
  const handleDeleteImg = useDeleteImgMutation(deleteProductImg);
  const postImg = usePostImgMutation(uploadProductImg);

  const openCategoryModal = () => {
    openModal('category', {
      selectedCategoryId: selectedCategory!,
      categoryData: category,
      onClick: (categoryId: number) => {
        setSelectedCategory(categoryId);
      },
    });
  };

  // To do: 카테로그 추천 로직 변경
  useEffect(() => {
    async function fetchRecommendCategories() {
      const categories = await category;
      setRecommendCategories(generateRecommendCategory(categories));
    }
    fetchRecommendCategories();
  }, []);

  useEffect(() => {
    if (hasName) return;
    setRecommendCategories(generateRecommendCategory(category));
    setSelectedCategory(null);
  }, [hasName]);

  return (
    <>
      <Header backgroundColor="neutralBackgroundWeak">
        <Header.Left>
          <TextButton
            size="M"
            textColor="neutralTextStrong"
            onClick={productData ? goDetailPage : navigateToGoBack}
          >
            닫기
          </TextButton>
        </Header.Left>
        <Header.Center>내 물건 팔기</Header.Center>
        <Header.Right>
          <TextButton
            disabled={!isValid}
            textColor="accentPrimary"
            onClick={
              // To Do: ProductDetail API가 수정되면 수정 로직 추가
              productData ? () => {} : () => postNewProduct.mutate(postData)
            }
          >
            완료
          </TextButton>
        </Header.Right>
      </Header>
      <Contents>
        <ImgSection>
          <ScrollWrapper>
            <ImgScroll>
              <AddImgInput
                numberOfImg={imgData.length}
                onChange={handleFileChange}
              />
              {imgData.map((productImg, index) => (
                <PictureItem
                  key={productImg.id}
                  id={productImg.id}
                  isThumbnail={index === 0}
                  imgUrl={productImg.imgUrl}
                  deleteImg={() => handleDeleteImg.mutate(productImg.id)}
                />
              ))}
            </ImgScroll>
          </ScrollWrapper>
        </ImgSection>
        <NameSection>
          <div>
            <Input value={name} onChange={onChangeName} />
          </div>
          {hasName && (
            <RecommendCategoryWrapper>
              <RecommendCategories>
                {recommendCategories?.map((tag) => (
                  <Tag
                    key={tag.id}
                    title={tag.name}
                    isSelected={selectedCategory === tag.id}
                    onClick={() => setSelectedCategory(tag.id)}
                  />
                ))}
              </RecommendCategories>
              <MoreCategoryButton onClick={openCategoryModal}>
                <Icon name="chevronRight" stroke="neutralTextStrong" />
              </MoreCategoryButton>
            </RecommendCategoryWrapper>
          )}
        </NameSection>
        <PriceSection>
          <PriceInput
            currencyUnit="won"
            value={price}
            onChange={onChangePrice}
          />
        </PriceSection>
        <ContentSection>
          <Textarea
            placeholder={`${extractRegionName(
              userRegions.selectedRegion.name
            )}에 올릴 게시물 내용을 작성해주세요.(판매금지 물품은 게시가 제한될 수 있어요.)`}
            value={content}
            onChange={onChangeContent}
          />
        </ContentSection>
      </Contents>
      <ActionBar>
        <EditBar userRegions={userRegions} selectRegion={selectRegion} />
      </ActionBar>
    </>
  );
};

const Contents = styled.div`
  padding: 72px 16px 16px 16px;
  overflow-y: auto;
  width: 100%;
  height: 100%;
  display: flex;
  gap: 16px;
  flex-direction: column;
  justify-content: start;
  section:not(:last-child)::after {
    position: absolute;
    left: 0px;
    bottom: 0px;
    content: '';
    width: inherit;
    height: 1px;
    background-color: ${({ theme }) => theme.color.neutralBorder};
  }
`;

const Section = styled.section`
  position: relative;
  width: 100%;
  padding-bottom: 16px;
`;

const ImgSection = styled(Section)`
  min-height: 104px;
`;

const NameSection = styled(Section)``;

const PriceSection = styled(Section)``;

const ContentSection = styled(Section)``;

const RecommendCategories = styled.div`
  display: flex;
  gap: 4px;
`;

const ScrollWrapper = styled.div`
  width: 100%;
  height: 100%;
  overflow-x: auto;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const ImgScroll = styled.div`
  display: flex;
  align-items: end;
  width: max-content;
  height: 100%;
  gap: 16px;
`;

const RecommendCategoryWrapper = styled.div`
  padding-top: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const MoreCategoryButton = styled.button`
  padding: 0px;
  width: 24px;
  height: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

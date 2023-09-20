import { ChangeEvent, useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { ActionBar } from '@components/ActionBar/ActionBar';
import { EditBar } from '@components/ActionBar/EditBar';
import { AddImgInput } from '@components/AddImgInput/AddImgInput';
import { TextButton } from '@components/Button/TextButton';
import { Header } from '@components/Header/Header';
import { Input } from '@components/Input/Input';
import { PriceInput } from '@components/Input/PriceInput';
import { PictureItem } from '@components/PictureItem/PictureItem';
import { Tag } from '@components/Tag/Tag';
import { Textarea } from '@components/Textarea/Textarea';
import { useInput } from '@hooks/useInput';
import { usePrice } from '@hooks/usePrice';
import { Categories } from '@api/category/types';
import { useCategoriesWithoutImages } from '@api/category/useCategories';
import { MAX_IMAGE_SIZE } from '@constants/constants';
import generateRecommendCategory from '@utils/generateRecommendCategory';
import { Product } from '@api/product/types';
import { Icon } from '@components/Icon/Icon';
import { useAtom } from 'jotai';
import { userRegionsAtom } from '@atoms/userAtom';
import { usePageNavigator } from '@hooks/usePageNavigator';
import extractRegionName from '@utils/extractRegionName';

export const AddPage = ({
  productData,
  goDetailPage,
}: {
  productData?: Product;
  goDetailPage?(): void;
}) => {
  const category = useCategoriesWithoutImages();
  const { navigateToGoBack } = usePageNavigator();
  const [userRegions] = useAtom(userRegionsAtom);
  const [imgFiles, setImgFiles] = useState<File[]>([]);
  const [productImgs, setProductImgs] = useState<string[]>([]);
  const { value: name, onChange: onChangeName } = useInput(
    productData?.productName
  );
  const { value: content, onChange: onChangeContent } = useInput(
    productData?.content
  );
  const { value: price, onChange: onChangePrice } = usePrice(
    productData?.price.toString()
  );
  const [selectedCategory, setSelectedCategory] = useState<number>();
  const [recommendCategories, setRecommendCategories] =
    useState<Categories[]>();
  const isAllRequired = imgFiles.length === 0 || name === '' || content === '';
  const hasName = name;

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
    setSelectedCategory(undefined);
  }, [hasName]);

  const onUploadImg = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (file && file.size <= MAX_IMAGE_SIZE) {
      setImgFiles([...imgFiles, file]);
      setProductImgs([...productImgs, URL.createObjectURL(file)]);
    }
  };

  const deleteImg = (e: React.MouseEvent<HTMLButtonElement>) => {
    const imgId = e.currentTarget.id;
    setImgFiles(imgFiles.filter((_, idx) => idx.toString() !== imgId));
    setProductImgs(productImgs.filter((_, idx) => idx.toString() !== imgId));
  };

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
          <TextButton disabled={isAllRequired} textColor="accentPrimary">
            완료
          </TextButton>
        </Header.Right>
      </Header>
      <Contents>
        <ImgSection>
          <ScrollWrapper>
            <ImgScroll>
              <AddImgInput
                numberOfImg={productImgs.length}
                onChange={onUploadImg}
              />
              {productImgs.map((productImg, index) => (
                <PictureItem
                  key={index}
                  id={index}
                  isThumbnail={index === 0}
                  imgUrl={productImg}
                  deleteImg={deleteImg}
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
              <MoreCategoryButton>
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
        <EditBar
          regionName={extractRegionName(userRegions.selectedRegion.name)}
        />
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

import { useMemo } from 'react';
import { styled } from 'styled-components';
import { Input } from '@components/Input/Input';
import { SearchRegionModalProps } from './types';
import { Icon } from '@components/Icon/Icon';

import { useInput } from '@hooks/useInput';
import { useDebounce } from '@hooks/useDebounce';
import { useModal } from './useModal';
import { useRegions } from '@api/region/regionList';
import { useInfiniteScroll } from '@hooks/useInfiniteScroll';

export const SearchRegionModal: React.FC<SearchRegionModalProps> = ({
  style,
  onSelectRegion,
}) => {
  const { closeModal, closeAllModals } = useModal();
  const { value: region, onChange: onChangeRegion } = useInput();
  const debouncedRegion = useDebounce(region, 500);

  const { data, isLoading, isError, isFetching, fetchNextPage, hasNextPage } =
    useRegions(debouncedRegion);

  const flattenedData = useMemo(() => {
    if (data) {
      const flattened = data.pages.flatMap((item) => item.regions);
      console.log(flattened);

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
    <ModalBase style={style}>
      <SearchHeader>
        <ButtonBase onClick={closeModal}>
          <Icon name="chevronLeft" />
        </ButtonBase>
        <ButtonBase onClick={closeAllModals}>
          <Icon name="x" />
        </ButtonBase>
      </SearchHeader>

      <InputWrapper>
        <Input
          placeholder="동명(읍, 면)으로 검색(ex. 서초동)"
          value={region}
          onChange={onChangeRegion}
        />
      </InputWrapper>

      {isLoading ? (
        <LoadingMessage>지역 리스트를 불러오는 중...</LoadingMessage>
      ) : isError ? (
        <ErrorMessage>불러오는 중에 오류가 발생했습니다.</ErrorMessage>
      ) : flattenedData.length === 0 ? (
        <NoResultsMessage>결과가 없습니다</NoResultsMessage>
      ) : (
        <List>
          {flattenedData.map((item, index) => (
            <Option
              key={item.id}
              ref={index === flattenedData.length - 1 ? lastElementRef : null}
              onClick={() => onSelectRegion(item.id)}
            >
              {item.name}
            </Option>
          ))}
          {!hasNextPage && (
            <EndOfResultsMessage>
              더 이상의 결과가 없습니다.
            </EndOfResultsMessage>
          )}
        </List>
      )}
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

const SearchHeader = styled.div`
  padding: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font: ${({ theme: { font } }) => font.displayStrong20};
  color: ${({ theme: { color } }) => color.neutralTextStrong};
`;

const InputWrapper = styled.div`
  padding: 0 16px;
  background-color: #eee;
  width: 288px;
  height: 40px;
  line-height: 40px;
  margin: 0 auto;
  border-radius: ${({ theme: { radius } }) => radius.small};
`;

const ButtonBase = styled.button`
  width: 48px;
  height: 48px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const List = styled.ul`
  font: ${({ theme: { font } }) => font.availableDefault16};
  padding: 0 24px;
  overflow-y: scroll;
  height: 576px;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;
const Option = styled.li`
  padding: 16px 0;
  border-bottom: 1px solid ${({ theme: { color } }) => color.neutralBorder};
  &:last-child {
    border-bottom: none;
  }
`;

const LoadingMessage = styled.div`
  text-align: center;
  padding-top: 40px;
`;

const ErrorMessage = styled.div`
  text-align: center;
  padding-top: 40px;
`;

const NoResultsMessage = styled.div`
  text-align: center;
  padding-top: 40px;
`;

const EndOfResultsMessage = styled.div`
  text-align: center;
  padding-top: 16px;
  color: ${({ theme: { color } }) => color.neutralTextWeak};
`;

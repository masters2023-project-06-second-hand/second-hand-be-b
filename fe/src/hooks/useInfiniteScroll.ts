import { useRef, useCallback } from 'react';

type InfiniteScrollProps = {
  isLoading?: boolean;
  hasNextPage?: boolean;
  fetchNextPage: () => void;
  isFetching?: boolean;
};

/* li 태그에 사용할 수 있는 훅 */
export const useInfiniteScroll = ({
  isLoading = false,
  hasNextPage = false,
  fetchNextPage,
  isFetching = false,
}: InfiniteScrollProps) => {
  const observer = useRef<IntersectionObserver>();

  const lastElementRef = useCallback(
    (node: HTMLLIElement) => {
      if (isLoading || isFetching) return;

      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });

      if (node) observer.current.observe(node);
    },
    [isLoading, hasNextPage, fetchNextPage, isFetching]
  );

  return lastElementRef;
};

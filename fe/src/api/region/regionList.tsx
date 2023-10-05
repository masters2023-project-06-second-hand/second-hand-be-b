import { API_ENDPOINTS } from '@constants/endpoints';
import { privateApi } from '../index';
import { useInfiniteQuery, QueryFunctionContext } from '@tanstack/react-query';
import { QUERY_KEYS } from '@api/queryKey';

interface QueryParams {
  word: string;
}

/* TODO. B조 api 수정 되면 publicApi로 수정하기 */
const getRegionsList = async ({
  pageParam = 0,
  queryKey,
}: QueryFunctionContext<[string, QueryParams]>) => {
  const [, { word }] = queryKey;

  const { data } = await privateApi.get(API_ENDPOINTS.REGIONS, {
    params: {
      page: pageParam,
      size: 10,
      word,
    },
  });

  return data;
};

export const useRegions = (word: string) => {
  return useInfiniteQuery(QUERY_KEYS.REGIONS(word), getRegionsList, {
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext ? lastPage.page + 1 : null;
    },
  });
};

import { API_ENDPOINTS } from '@constants/endpoints';
import { publicApi } from '../index';
import { useInfiniteQuery, QueryFunctionContext } from '@tanstack/react-query';
import { QUERY_KEYS } from '@api/queryKey';

interface QueryParams {
  regionId: number;
  categoryId: number | null;
}

const getProductList = async ({
  pageParam = 0,
  queryKey,
}: QueryFunctionContext<[string, QueryParams]>) => {
  const [, { regionId, categoryId }] = queryKey;

  const { data } = await publicApi.get(API_ENDPOINTS.PRODUCTS, {
    params: {
      regionId,
      categoryId,
      page: pageParam,
    },
  });
  return data;
};

export const useProducts = (regionId: number, categoryId: number | null) => {
  return useInfiniteQuery(
    QUERY_KEYS.PRODUCTS(regionId, categoryId),
    getProductList,
    {
      getNextPageParam: (lastPage) => {
        return lastPage.hasNext ? lastPage.page + 1 : null;
      },
    }
  );
};

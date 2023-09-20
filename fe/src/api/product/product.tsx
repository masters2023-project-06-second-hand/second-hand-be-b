import { API_ENDPOINTS } from '@constants/endpoints';
import { privateApi, publicApi } from '..';
import {
  QueryKey,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { Product, ProductStatus } from './types';
import { QUERY_KEYS } from '@api/queryKey';

const getProductDetail = async (productId: number) => {
  const { data } = await publicApi.get(API_ENDPOINTS.PRODUCT_DETAIL(productId));
  return data;
};

export const useProductDetail = (productId: number) => {
  return useQuery<Product>(QUERY_KEYS.PRODUCT_DETAIL(productId), () =>
    getProductDetail(productId)
  );
};

type StatusMutation = {
  productId: number;
  status: ProductStatus;
};

const updateProductStatus = async (
  productId: number,
  status: ProductStatus
) => {
  const requestData = {
    status: status,
  };

  const response = await privateApi.put(
    API_ENDPOINTS.PRODUCT_STATUS(productId),
    requestData
  );
  return response.data;
};

export const useChangeProductStatusMutation = (queryKey: QueryKey) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    (data: StatusMutation) => updateProductStatus(data.productId, data.status),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(queryKey);
      },
    }
  );
  return { mutate };
};

const deleteProduct = async (productId: number) => {
  const response = await privateApi.delete(
    API_ENDPOINTS.DELETE_PRODUCT(productId)
  );
  return response.data;
};

export const useDeleteProductMutation = (queryKey: QueryKey) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    (productId: number) => deleteProduct(productId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(queryKey);
      },
    }
  );
  return { mutate };
};

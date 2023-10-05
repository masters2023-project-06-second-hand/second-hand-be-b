import { API_ENDPOINTS } from '@constants/endpoints';
import { privateApi, publicApi } from '..';
import {
  QueryKey,
  useMutation,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import {
  NewProductProps,
  Product,
  ProductStatProps,
  ProductStatus,
  StatusMutation,
} from './types';
import { usePageNavigator } from '@hooks/usePageNavigator';
import { useToast } from '@components/Toast/useToast';
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

const getProductStat = async (productId: number) => {
  const { data } = await publicApi.get(API_ENDPOINTS.PRODUCT_STAT(productId));

  return data;
};

export const useProductStat = (productId: number) => {
  return useQuery<ProductStatProps>(QUERY_KEYS.PRODUCT_STAT(productId), () =>
    getProductStat(productId)
  );
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

const postNewProduct = async (productData: NewProductProps) => {
  const response = await privateApi.post(
    API_ENDPOINTS.POST_NEW_PRODUCT(),
    productData
  );

  return response.data;
};

export const usePostProductMutation = () => {
  const { navigateToHome } = usePageNavigator();
  const toast = useToast();
  const { mutate } = useMutation(postNewProduct, {
    onSuccess: () => {
      navigateToHome();
      toast.noti('상품이 등록되었습니다.');
    },
    onError: () => {
      toast.error('에러가 발생했습니다. 다시 시도해주세요.');
    },
  });
  return { mutate };
};

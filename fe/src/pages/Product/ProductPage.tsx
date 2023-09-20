import { AddPage } from '@pages/Product/AddPage';
import { DetailPage } from './DetailPage';
import { useParams } from 'react-router-dom';
import { useProductDetail } from '@api/product/product';
import { Loading } from '@pages/Loading';
import { ErrorPage } from '@pages/ErrorPage';
import { useState } from 'react';

export const ProductPage = () => {
  const { id } = useParams();
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const { data, isLoading, isError } = useProductDetail(Number(id));
  if (isLoading) return <Loading />;
  if (isError) return <ErrorPage />;

  const goEditProductPage = () => {
    setIsEdit(true);
  };

  const goDetailProductPage = () => {
    setIsEdit(false);
  };

  return (
    <>
      {isEdit ? (
        <AddPage productData={data} goDetailPage={goDetailProductPage} />
      ) : (
        <DetailPage productData={data} goEditPage={goEditProductPage} />
      )}
    </>
  );
};

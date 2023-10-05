import { AddPage } from '@pages/Product/AddPage';
import { DetailPage } from './DetailPage';
import { useParams } from 'react-router-dom';
import { useProductDetail, useProductStat } from '@api/product/product';
import { Loading } from '@pages/Loading';
import { ErrorPage } from '@pages/ErrorPage';
import { useState } from 'react';

export const ProductPage = () => {
  const { id } = useParams();
  const [isEdit, setIsEdit] = useState<boolean>(false);
  const {
    data: productData,
    isLoading: dataLoading,
    isError: dataError,
  } = useProductDetail(Number(id));
  const {
    data: productStat,
    isLoading: statLoading,
    isError: statError,
  } = useProductStat(Number(id));
  if (dataLoading || statLoading) return <Loading />;
  if (dataError || statError) return <ErrorPage />;

  const goEditProductPage = () => {
    setIsEdit(true);
  };

  const goDetailProductPage = () => {
    setIsEdit(false);
  };

  return (
    <>
      {isEdit ? (
        <AddPage productData={productData} goDetailPage={goDetailProductPage} />
      ) : (
        <DetailPage
          productData={productData}
          productStat={productStat}
          goEditPage={goEditProductPage}
        />
      )}
    </>
  );
};

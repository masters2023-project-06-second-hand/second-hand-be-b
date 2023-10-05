import { API_ENDPOINTS } from '@constants/endpoints';
import { multiFormApi, privateApi } from '..';
import { MAX_IMAGE_SIZE } from '@constants/constants';
import { useMutation } from '@tanstack/react-query';
import { ImgProps } from './type';
import { useToast } from '@components/Toast/useToast';

const uploadImage = async (file: FileList) => {
  if (file[0].size > MAX_IMAGE_SIZE) {
    return;
  }

  const formData = new FormData();
  formData.append('file', file[0]);

  const response = await multiFormApi.post(
    API_ENDPOINTS.POST_PRODUCT_IMG(),
    formData
  );

  return response.data;
};

export const usePostImgMutation = (
  uploadImg: (newImgData: ImgProps) => void
) => {
  const { mutate } = useMutation(uploadImage, {
    onSuccess: (data) => {
      uploadImg(data);
    },
  });

  return { mutate };
};

const deleteProductImg = async (imgId: number) => {
  const response = await privateApi.delete(
    API_ENDPOINTS.DELETE_PRODUCT_IMG(imgId)
  );
  return response;
};

export const useDeleteImgMutation = (deleteImg: (imgId: number) => void) => {
  const toast = useToast();
  const { mutate } = useMutation((imgId: number) => deleteProductImg(imgId), {
    onSuccess: (data, imgId) => {
      console.log(data); /* 지울 콘솔입니다 */
      deleteImg(imgId);
      toast.noti('이미지가 삭제되었습니다');
    },
  });
  return { mutate };
};

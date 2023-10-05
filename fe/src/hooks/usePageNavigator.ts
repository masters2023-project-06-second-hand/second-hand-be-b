import { useNavigate } from 'react-router-dom';
import { PATH } from '@constants/path';

export const usePageNavigator = () => {
  const navigate = useNavigate();

  return {
    navigateToHome: () => navigate(PATH.HOME),
    navigateToCategory: () => navigate(PATH.CATEGORY),
    navigateToHistory: () => navigate(PATH.HISTORY),
    navigateToLiked: () => navigate(PATH.LIKED),
    navigateToChat: () => navigate(PATH.CHAT),
    navigateToAccount: () => navigate(PATH.ACCOUNT),
    navigateToDetail: (productId: number) => navigate(PATH.DETAIL(productId)),
    navigateToAdd: () => navigate(PATH.ADD),
    navigateToJoin: () => navigate(PATH.JOIN),
    navigateToCallback: () => navigate(PATH.CALLBACK),
    navigateToError: () => navigate(PATH.FALLBACK),
    navigateToGoBack: () => navigate(PATH.GOBACK),
  };
};

import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useSetAtom } from 'jotai';
import { usePageNavigator } from '@hooks/usePageNavigator';
import { signupTokenAtom } from '@atoms/loginAtom';
import { fetchLogin, useHandleLogin } from '@api/auth/login';
import { extractQueryString } from '@utils/index';

export const CallbackPage = () => {
  const { provider } = useParams();

  const { navigateToJoin, navigateToHome } = usePageNavigator();

  const handleLogin = useHandleLogin();
  const setSignupToken = useSetAtom(signupTokenAtom);

  useEffect(() => {
    const URL = window.location.href;
    const queryString = extractQueryString(URL);

    async function login() {
      try {
        if (provider) {
          const data = await fetchLogin(provider, queryString);

          if (data.accessToken && data.refreshToken) {
            await handleLogin(data);
            navigateToHome();
          }
        }
      } catch (error) {
        /* status 422일 경우 처음 가입 회원, 나머지는 에러 예외처리하기 */
        if (axios.isAxiosError(error) && error.response) {
          const signupToken = error.response.data.message.signupToken;
          setSignupToken(signupToken);
          navigateToJoin();
        } else {
          // Handle other types of errors or re-throw
          console.error(error);
        }
      }
    }

    login();
  }, []);

  return <></>;
};

export const PATH = {
  HOME: '/',
  CATEGORY: '/category',
  HISTORY: '/history',
  LIKED: '/liked',
  CHAT: '/chat',
  ACCOUNT: '/account',
  DETAIL: (productId: number) => `/product/${productId}`,
  ADD: '/add',
  JOIN: '/join',
  CALLBACK: '/callback/:provider',
  FALLBACK: '*',
  GOBACK: -1,
};

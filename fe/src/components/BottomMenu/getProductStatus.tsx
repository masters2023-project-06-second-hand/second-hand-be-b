export const getDropdownItems = (status: string) => {
  switch (status) {
    case '판매중':
      return [
        { id: '수정', name: '게시글 수정' },
        { id: '판매완료', name: '판매 완료 상태로 전환' },
        { id: '예약중', name: '예약 중 상태로 전환' },
        { id: '삭제', name: '삭제' },
      ];
    case '예약중':
      return [
        { id: '수정', name: '게시글 수정' },
        { id: '판매완료', name: '판매 완료 상태로 전환' },
        { id: '판매중', name: '판매 중 상태로 전환' },
        { id: '삭제', name: '삭제' },
      ];
    case '판매완료':
      return [
        { id: 'edit', name: '게시글 수정' },
        { id: '판매중', name: '판매 중 상태로 전환' },
        { id: '예약중', name: '예약 중 상태로 전환' },
        { id: '삭제', name: '삭제' },
      ];
    default:
      return [];
  }
};

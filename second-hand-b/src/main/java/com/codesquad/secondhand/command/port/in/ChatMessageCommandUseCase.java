package com.codesquad.secondhand.command.port.in;

public interface ChatMessageCommandUseCase {

    /**
     * 채팅방에서 보낸 메시지를 저장한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param message    메시지
     * @param senderId   보낸 사용자 ID
     */
    void saveChatMessage(long chatRoomId, String message, long senderId);

    /**
     * 채팅방 ID에 존재하는 메시지 중 상대방이 보낸 메시지를 모두 읽음 처리한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   로그인 된 사용자 ID
     */
    void markMessagesAsRead(long chatRoomId, long memberId);
}

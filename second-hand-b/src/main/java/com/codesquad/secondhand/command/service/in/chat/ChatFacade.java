package com.codesquad.secondhand.command.service.in.chat;

import static com.codesquad.secondhand.command.service.in.chat.ChatMapper.toChatRoomDetail;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomId;
import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.port.in.ChatUseCase;
import com.codesquad.secondhand.command.service.in.MemberCommandService;
import com.codesquad.secondhand.command.service.in.ProductCommandService;
import com.codesquad.secondhand.common.exception.ChatRoomNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatFacade implements ChatUseCase {

    public static final int CHAT_ROOM_MEMBER_MAX_SIZE = 2;

    private final ChatRoomService chatRoomService;
    private final ChatRoomMemberService chatRoomMemberService;
    private final ChatMessageService chatMessageService;
    private final ProductCommandService productService;
    private final MemberCommandService memberService;

    @Transactional
    @Override
    public ChatRoomId getChatRoomId(long productId, long sellerId, long memberId) {
        try {
            ChatRoom chatRoom = chatRoomService.getChatRoomId(productId, sellerId, memberId);
            return new ChatRoomId(chatRoom.getId());
        } catch (ChatRoomNotFoundException e) {
            ChatRoom chatRoom = toChatRoom(productId, memberId);
            ChatRoom savedChatRoom = chatRoomService.save(chatRoom);
            return new ChatRoomId(savedChatRoom.getId());
        }
    }

    @Override
    public ChatRoomDetail getChatRoomDetail(long chatRoomId, long memberId) {
        ChatRoom chatRoom = chatRoomService.getById(chatRoomId);
        List<ChatMessage> chatMessages = chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
        return toChatRoomDetail(chatRoom, chatMessages, memberId);
    }

    @Override
    public void markMessagesAsRead(long chatRoomId, long memberId) {
        chatMessageService.markMessagesAsRead(chatRoomId, memberId);
    }

    @Override
    public void addChatRoomMember(long chatRoomId, long memberId) {
        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoomId, memberId);
        chatRoomMemberService.save(chatRoomMember);
    }

    @Override
    public void deleteChatRoomMember(long chatRoomId, long memberId) {
        chatRoomMemberService.deleteByChatRoomIdAndMemberId(chatRoomId, memberId);
    }

    @Override
    public void saveChatMessage(long chatRoomId, String message, long senderId) {
        ChatMessage chatMessage = toChatMessage(chatRoomId, message, senderId);
        chatMessageService.save(chatMessage);
    }

    private ChatRoom toChatRoom(long productId, long memberId) {
        Product product = productService.getById(productId);
        Member seller = memberService.getById(product.getWriterId());
        Member buyer = memberService.getById(memberId);
        return new ChatRoom(
                product,
                seller,
                buyer,
                LocalDateTime.now());
    }

    private ChatMessage toChatMessage(long chatRoomId, String message, long senderId) {
        ChatRoom chatRoom = chatRoomService.getById(chatRoomId);
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberService.findAllByChatRoomId(chatRoomId);
        boolean readOrNot = isChatRoomFull(chatRoomMembers);
        return new ChatMessage(
                chatRoom,
                senderId,
                message,
                readOrNot,
                LocalDateTime.now());
    }

    private boolean isChatRoomFull(List<ChatRoomMember> chatRoomMembers) {
        return chatRoomMembers.size() == CHAT_ROOM_MEMBER_MAX_SIZE;
    }
}

package com.codesquad.secondhand.application.service.in.chat;

import static com.codesquad.secondhand.application.service.in.chat.ChatMapper.toChatRoomDetail;

import com.codesquad.secondhand.adapter.in.web.response.ChatRoomDetail;
import com.codesquad.secondhand.adapter.in.web.response.ChatRoomId;
import com.codesquad.secondhand.application.port.in.ChatUseCase;
import com.codesquad.secondhand.application.service.in.exception.ChatRoomNotFoundException;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.domain.chat.ChatMessage;
import com.codesquad.secondhand.domain.chat.ChatRoom;
import com.codesquad.secondhand.domain.chat.ChatRoomMember;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatFacade implements ChatUseCase {

    private final ChatRoomService chatRoomService;
    private final ChatRoomMemberService chatRoomMemberService;
    private final ChatMessageService chatMessageService;
    private final ProductService productService;

    @Transactional
    @Override
    public ChatRoomId getChatRoomId(long productId, long sellerId, Member member) {
        try {
            ChatRoom chatRoom = chatRoomService.getChatRoomId(productId, sellerId, member.getId());
            return new ChatRoomId(chatRoom.getId());
        } catch (ChatRoomNotFoundException e) {
            ChatRoom chatRoom = toChatRoom(productId, member);
            ChatRoom savedChatRoom = chatRoomService.save(chatRoom);
            return new ChatRoomId(savedChatRoom.getId());
        }
    }

    @Override
    public ChatRoomDetail getChatRoomDetail(long chatRoomId, Member member) {
        ChatRoom chatRoom = chatRoomService.getById(chatRoomId);
        List<ChatMessage> chatMessages = chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
        return toChatRoomDetail(chatRoom, chatMessages, member);
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

    private ChatRoom toChatRoom(long productId, Member member) {
        Product product = productService.getById(productId);
        Member seller = product.getWriter();
        return new ChatRoom(
                product,
                seller,
                member,
                LocalDateTime.now());
    }
}

package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomId;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.port.in.ChatRoomCommandUseCase;
import com.codesquad.secondhand.command.service.in.MemberCommandService;
import com.codesquad.secondhand.command.service.in.ProductCommandService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomCommandFacade implements ChatRoomCommandUseCase {

    private final ChatRoomCommandService chatRoomCommandService;
    private final ChatRoomMemberCommandService chatRoomMemberCommandService;
    private final ProductCommandService productService;
    private final MemberCommandService memberService;

    @Override
    public ChatRoomId getChatRoomId(long productId, long sellerId, long memberId) {
        ChatRoom chatRoom = chatRoomCommandService.getChatRoomId(productId, sellerId, memberId);
        return new ChatRoomId(chatRoom.getId());
    }

    @Transactional
    @Override
    public ChatRoomId createChatRoomId(long productId, long memberId) {
        ChatRoom chatRoom = toChatRoom(productId, memberId);
        ChatRoom savedChatRoom = chatRoomCommandService.save(chatRoom);
        return new ChatRoomId(savedChatRoom.getId());
    }

    @Override
    public void addChatRoomMember(long chatRoomId, long memberId) {
        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoomId, memberId);
        chatRoomMemberCommandService.save(chatRoomMember);
    }

    @Override
    public void deleteChatRoomMember(long chatRoomId, long memberId) {
        chatRoomMemberCommandService.deleteByChatRoomIdAndMemberId(chatRoomId, memberId);
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
}

package com.codesquad.secondhand.application.service.in.chat;

import com.codesquad.secondhand.adapter.in.web.response.ChatRoomId;
import com.codesquad.secondhand.application.port.in.ChatUseCase;
import com.codesquad.secondhand.application.service.in.exception.ChatRoomNotFoundException;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.domain.chat.ChatRoom;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatFacade implements ChatUseCase {

    private final ChatRoomService chatRoomService;
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

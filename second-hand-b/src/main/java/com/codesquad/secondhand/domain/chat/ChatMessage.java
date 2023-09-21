package com.codesquad.secondhand.domain.chat;

import com.codesquad.secondhand.domain.member.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean readOrNot;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public String getMessage() {
        return message;
    }

    public Long getSenderId() {
        return sender.getId();
    }

    public boolean isSender(long memberId) {
        return sender.isSameId(memberId);
    }

    public void readMessage() {
        readOrNot = true;
    }
}

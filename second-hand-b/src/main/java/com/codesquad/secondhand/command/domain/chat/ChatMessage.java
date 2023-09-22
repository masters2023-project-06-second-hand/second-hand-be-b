package com.codesquad.secondhand.command.domain.chat;

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
    @Column(nullable = false)
    private Long senderId;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private boolean readOrNot;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ChatMessage(ChatRoom chatRoom, Long senderId, String message, boolean readOrNot, LocalDateTime createdAt) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.message = message;
        this.readOrNot = readOrNot;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public Long getSenderId() {
        return senderId;
    }

    public boolean isSender(long memberId) {
        return senderId.equals(memberId);
    }

    public void readMessage() {
        readOrNot = true;
    }
}

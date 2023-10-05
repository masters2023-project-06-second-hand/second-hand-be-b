package com.codesquad.secondhand.command.domain.chat;

import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.product.Product;
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
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ChatRoom(Product product, Member seller, Member buyer, LocalDateTime createdAt) {
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getOpponentName(Long memberId) {
        if (seller.isSameId(memberId)) {
            return buyer.getNickname();
        }
        return seller.getNickname();
    }

    public Long getOpponentId(Long memberId) {
        if (seller.isSameId(memberId)) {
            return buyer.getId();
        }
        return seller.getId();
    }
}

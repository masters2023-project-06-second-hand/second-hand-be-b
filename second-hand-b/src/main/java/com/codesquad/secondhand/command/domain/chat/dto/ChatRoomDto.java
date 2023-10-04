package com.codesquad.secondhand.command.domain.chat.dto;

import java.time.LocalDateTime;

public interface ChatRoomDto {

    Long getId();

    Long getProductId();

    String getProductThumbnailUrl();

    String getOpponentName();

    String getOpponentThumbnailUrl();

    String getMessage();

    LocalDateTime getSendAt();

    Integer getUnreadMessageCount();
}

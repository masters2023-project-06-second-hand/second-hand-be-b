package com.codesquad.secondhand.common.messaging.port;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationUseCase {

    SseEmitter subscribe(Long memberId);

    void notify(Long memberId, Object data);
}

package com.codesquad.secondhand.command.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationUseCase {

    SseEmitter subscribe(Long memberId);
}

package com.codesquad.secondhand.command.adapter.in.web.chat;

import com.codesquad.secondhand.command.port.in.NotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
public class SseNotificationController {

    private final NotificationUseCase notificationUseCase;

    @GetMapping(value = "/api/subscribe/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long memberId) {
        return notificationUseCase.subscribe(memberId);
    }
}

package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.port.in.NotificationUseCase;
import com.codesquad.secondhand.command.repository.SseEmitterRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class NotificationService implements NotificationUseCase {

    private final SseEmitterRepository sseEmitterRepository;

    private static final Long SSE_TIMEOUT = 60 * 60 * 1000L;
    private static final String SUBSCRIBE_EVENT_NAME = "subscribe";
    private static final String NOTIFY_EVENT_NAME = "notify";

    @Override
    public SseEmitter subscribe(Long memberId) {
        SseEmitter sseEmitter = createSseEmitter(memberId);
        sendToClient(memberId, SUBSCRIBE_EVENT_NAME, true);
        return sseEmitter;
    }

    public void notify(Long memberId, Object data) {
        sendToClient(memberId, NOTIFY_EVENT_NAME, data);
    }

    private SseEmitter createSseEmitter(Long memberId) {
        SseEmitter sseEmitter = new SseEmitter(SSE_TIMEOUT);
        sseEmitterRepository.save(memberId, sseEmitter);

        // 요청이 완료되거나 타임아웃 발생 시 SseEmitter 객체를 다시 생성하기 때문에 기존 SseEmitter를 삭제해준다.
        sseEmitter.onCompletion(() -> sseEmitterRepository.delete(memberId));
        sseEmitter.onTimeout(() -> sseEmitterRepository.delete(memberId));
        return sseEmitter;
    }

    private void sendToClient(Long memberId, String eventName, Object data) {
        SseEmitter sseEmitter = sseEmitterRepository.get(memberId);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException e) {
                sseEmitterRepository.delete(memberId);
                sseEmitter.completeWithError(e);
            }
        }
    }
}

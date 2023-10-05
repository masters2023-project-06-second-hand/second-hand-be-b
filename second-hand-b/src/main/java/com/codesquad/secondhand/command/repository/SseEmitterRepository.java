package com.codesquad.secondhand.command.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long id, SseEmitter sseEmitter) {
        sseEmitters.put(id, sseEmitter);
        return sseEmitter;
    }

    public void delete(Long id) {
        sseEmitters.remove(id);
    }

    public SseEmitter get(Long id) {
        return sseEmitters.get(id);
    }
}

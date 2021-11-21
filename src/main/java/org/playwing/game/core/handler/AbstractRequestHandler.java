package org.playwing.game.core.handler;

import org.playwing.game.core.handler.converter.BaseRequestConverter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractRequestHandler<RequestType> implements BaseRequestHandler {

    private final Lock lock = new ReentrantLock(true);

    @Override
    public void handleRequest(String input) {
        lock.lock();

        try {
            BaseRequestConverter<RequestType> requestConverter = getRequestConverter();

            RequestType request = requestConverter.convertToDto(input);

            handleRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public abstract void handleRequest(RequestType request);

    public abstract BaseRequestConverter<RequestType> getRequestConverter();
}

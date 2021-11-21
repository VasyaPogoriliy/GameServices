package org.playwing.game.core.commons.handlers;

import org.playwing.game.core.commons.exception.RequestHandlerNotFoundException;
import org.playwing.game.core.handler.BaseRequestHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestHandlers {

    private final Map<String, BaseRequestHandler> requestHandlersMap = new ConcurrentHashMap<>();

    public void addRequestHandler(String handlerName, BaseRequestHandler requestHandler) {
        requestHandlersMap.computeIfAbsent(handlerName, key -> requestHandler);
    }

    public void handleRequest(String handlerName, String request) {
        requestHandlersMap.compute(handlerName, (key, handler) -> {
            if (handler != null) {
                handler.handleRequest(request);
                return handler;
            }
            throw new RequestHandlerNotFoundException(String.format("handler - %s, not found!", handlerName));
        });
    }
}

package org.playwing.game.core.handler.converter;

public interface BaseRequestConverter<T> {

    T convertToDto(String request);
}

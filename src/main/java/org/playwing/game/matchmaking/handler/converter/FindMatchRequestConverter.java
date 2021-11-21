package org.playwing.game.matchmaking.handler.converter;

import com.google.gson.Gson;
import org.playwing.game.core.handler.converter.BaseRequestConverter;
import org.playwing.game.matchmaking.dto.request.FindMatchRequest;

public class FindMatchRequestConverter implements BaseRequestConverter<FindMatchRequest> {

    @Override
    public FindMatchRequest convertToDto(String request) {
        Gson gson = new Gson();
        return gson.fromJson(request, FindMatchRequest.class);
    }
}

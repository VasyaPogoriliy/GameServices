package org.playwing.game.matchmaking.dto.response;

import com.google.gson.Gson;
import lombok.Builder;
import org.playwing.game.core.commons.response.Response;

@Builder
public class FindMatchResponse implements Response {

    private final String team;
    private final String serverAddress;

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

package org.playwing.game.matchmaking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playwing.game.core.commons.response.Response;

@Getter
@AllArgsConstructor
public class BasePlayer {

    private final String id;

    public void sendResponse(Response response) {
        System.out.printf("Player with id - %s, got response - %s\n\n", id, response.toJson());
    }
}

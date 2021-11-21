package org.playwing.game.matchmaking.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMatchRequest {

    private String id;
    private Integer skill;
    private Integer squadId;
}

package org.playwing.game.matchmaking.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Player extends BasePlayer {

    private final Integer skill;
    private final Integer squadId;

    public Player(String id, Integer skill, Integer squadId) {
        super(id);
        this.skill = skill;
        this.squadId = squadId;
    }
}

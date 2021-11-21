package org.playwing.game.matchmaking.domain;

import lombok.Getter;
import lombok.Setter;
import org.playwing.game.matchmaking.dto.constants.TeamType;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class Team {

    private TeamType teamType;
    private List<Player> players;
    private Integer averageTeamSkill;

    public void setNewPlayersAndCalculateAverageTeamSkill(List<Player> teamPlayers) {
        if (players == null) {
            players = new LinkedList<>();
        }

        AtomicReference<Integer> newAverageValue = new AtomicReference<>(0);
        players.forEach(player -> newAverageValue.updateAndGet(v -> v + player.getSkill()));
        teamPlayers.forEach(player -> newAverageValue.updateAndGet(v -> v + player.getSkill()));
        players.addAll(teamPlayers);
        averageTeamSkill = newAverageValue.get() / players.size();
    }
}

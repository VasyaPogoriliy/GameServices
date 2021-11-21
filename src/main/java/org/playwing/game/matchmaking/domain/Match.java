package org.playwing.game.matchmaking.domain;

import lombok.Getter;
import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.matchmaking.dto.constants.TeamType;

import java.util.List;

@Getter
public class Match {

    private final String serverAddress;

    private Team teamA;
    private Team teamB;

    private boolean isEnded;

    public Match(Integer matchNumber) {
        serverAddress = String.format("some address for team - №%d", matchNumber);
    }

    public boolean tryToSetTeam(List<Player> teamPlayers, Integer averageTeamSkill) {
        if (teamA == null && teamB == null) {
            this.teamA = new Team();
            teamA.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            teamA.setTeamType(TeamType.A);
            return true;
        }
        if (teamA != null &&
                (teamA.getPlayers().size() + teamPlayers.size() <= DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) &&
                (teamA.getAverageTeamSkill().compareTo(averageTeamSkill) <= DefaultConfig.DEFAULT_DELTA_SKILL_BETWEEN_PLAYERS)) {
            this.teamA = new Team();
            teamA.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            return true;
        }
        if (teamB != null &&
                (teamB.getPlayers().size() + teamPlayers.size() <= DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) &&
                (teamB.getAverageTeamSkill().compareTo(averageTeamSkill) <= DefaultConfig.DEFAULT_DELTA_SKILL_BETWEEN_PLAYERS)) {
            teamB.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            return true;
        }
        if (teamB == null) {
            this.teamB = new Team();
            teamB.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            teamB.setTeamType(TeamType.B);
            return true;
        }
        if (teamA == null) {
            this.teamA = new Team();
            teamA.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            teamA.setTeamType(TeamType.A);
            return true;
        }
        return false;
    }

    public boolean tryToSetTeamPerfectly(List<Player> teamPlayers, Integer averageTeamSkill) {
        if (teamA == null && teamPlayers.size() == DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) {
            this.teamA = new Team();
            teamA.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            teamA.setTeamType(TeamType.A);
            return true;
        }
        if (teamB == null && teamPlayers.size() == DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) {
            this.teamB = new Team();
            teamB.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            teamB.setTeamType(TeamType.B);
            return true;
        }
        if (teamA != null &&
                (teamA.getPlayers().size() + teamPlayers.size() == DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) &&
                (teamA.getAverageTeamSkill().compareTo(averageTeamSkill) <= DefaultConfig.DEFAULT_DELTA_SKILL_BETWEEN_PLAYERS)) {
            teamA.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            return true;
        }
        if (teamB != null &&
                (teamB.getPlayers().size() + teamPlayers.size() == DefaultConfig.DEFAULT_TEAM_PLAYERS_COUNT) &&
                (teamB.getAverageTeamSkill().compareTo(averageTeamSkill) <= DefaultConfig.DEFAULT_DELTA_SKILL_BETWEEN_PLAYERS)) {
            teamB.setNewPlayersAndCalculateAverageTeamSkill(teamPlayers);
            return true;
        }
        return false;
    }

    public void endMatch() {
        isEnded = true;
    }
}

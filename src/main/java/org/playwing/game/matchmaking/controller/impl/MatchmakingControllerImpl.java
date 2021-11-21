package org.playwing.game.matchmaking.controller.impl;

import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.matchmaking.controller.MatchmakingController;
import org.playwing.game.matchmaking.domain.Match;
import org.playwing.game.matchmaking.domain.Player;
import org.playwing.game.matchmaking.domain.Team;
import org.playwing.game.matchmaking.dto.constants.TeamType;
import org.playwing.game.matchmaking.dto.request.FindMatchRequest;
import org.playwing.game.matchmaking.dto.response.FindMatchResponse;

import java.util.List;

public class MatchmakingControllerImpl implements MatchmakingController {

    @Override
    public void addPlayerToSearchMatch(FindMatchRequest findMatchRequest) {
        Player player = new Player(
                findMatchRequest.getId(),
                findMatchRequest.getSkill(),
                findMatchRequest.getSquadId());

        DependencyManager.getInstance()
                .getHoldersProvider()
                .getSearchingPlayersHolder()
                .addPlayerToSearchingMatch(player);
    }

    @Override
    public void sendResponseToAllPlayersInMatches(List<Match> matches) {
        matches.forEach(this::sendResponseToAllPlayersInMatch);
    }

    @Override
    public void sendResponseToAllPlayersInMatch(Match match) {
        String serverAddress = match.getServerAddress();

        sendResponseToTeamMembers(match.getTeamA(), serverAddress);
        sendResponseToTeamMembers(match.getTeamB(), serverAddress);
    }

    private void sendResponseToTeamMembers(Team team, String serverAddress) {
        TeamType teamType = team.getTeamType();

        team.getPlayers().forEach(player -> {
            FindMatchResponse findMatchResponse = FindMatchResponse.builder()
                    .serverAddress(serverAddress)
                    .team(teamType.name())
                    .build();

            player.sendResponse(findMatchResponse);
        });
    }
}

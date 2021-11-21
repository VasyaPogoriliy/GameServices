package org.playwing.game.matchmaking.controller;

import org.playwing.game.matchmaking.domain.Match;
import org.playwing.game.matchmaking.dto.request.FindMatchRequest;

import java.util.List;

public interface MatchmakingController {

    void addPlayerToSearchMatch(FindMatchRequest findMatchRequest);

    void sendResponseToAllPlayersInMatches(List<Match> matches);

    void sendResponseToAllPlayersInMatch(Match match);
}

package org.playwing.game.matchmaking.service;

import org.playwing.game.matchmaking.domain.Match;
import org.playwing.game.matchmaking.domain.Player;

import java.util.List;

public interface MatchmakingService {

    List<Match> findMatchesForPlayers(List<Player> players);
}

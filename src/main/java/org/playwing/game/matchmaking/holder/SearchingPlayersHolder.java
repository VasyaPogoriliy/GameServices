package org.playwing.game.matchmaking.holder;

import org.playwing.game.matchmaking.domain.Player;

import java.util.List;

public interface SearchingPlayersHolder {

    void addPlayerToSearchingMatch(Player player);

    void addPlayersToSearchingMatch(List<Player> players);

    List<Player> getFixedNumberOfPlayersAndRemoveThemFromQueue(int playersCount);

    boolean isThereArePlayersWhoSearchMatch();

    boolean isPlayersWhoSearchMatchEnoughToStartTaskExecution();
}

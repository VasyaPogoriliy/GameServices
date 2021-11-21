package org.playwing.game.matchmaking.holder.impl;

import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.matchmaking.domain.Player;
import org.playwing.game.matchmaking.holder.SearchingPlayersHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchingPlayersHolderImpl implements SearchingPlayersHolder {

    private final Queue<Player> playersInSearchQueue;

    public SearchingPlayersHolderImpl() {
        playersInSearchQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void addPlayerToSearchingMatch(Player player) {
        playersInSearchQueue.add(player);
    }

    @Override
    public void addPlayersToSearchingMatch(List<Player> players) {
        playersInSearchQueue.addAll(players);
    }

    @Override
    public List<Player> getFixedNumberOfPlayersAndRemoveThemFromQueue(int playersCount) {
        synchronized (playersInSearchQueue) {
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < playersCount; i++) {
                if (!playersInSearchQueue.isEmpty()) {
                    players.add(playersInSearchQueue.poll());
                }
            }

            return players;
        }
    }

    @Override
    public boolean isThereArePlayersWhoSearchMatch() {
        return !playersInSearchQueue.isEmpty();
    }

    @Override
    public boolean isPlayersWhoSearchMatchEnoughToStartTaskExecution() {
        Queue<Player> playersQueue = new ConcurrentLinkedQueue<>(playersInSearchQueue);

        for (int i = 0; i < DefaultConfig.DEFAULT_MINIMAL_PLAYERS_COUNT; i++) {
            if (playersQueue.poll() == null) {
                return false;
            }
        }
        return true;
    }
}

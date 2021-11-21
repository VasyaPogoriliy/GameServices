package org.playwing.game.matchmaking.tasks;

import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.matchmaking.domain.Match;
import org.playwing.game.matchmaking.domain.Player;
import org.playwing.game.matchmaking.holder.SearchingPlayersHolder;

import java.util.List;

public class ProcessingPlayersTask implements Runnable {

    @Override
    public void run() {
        SearchingPlayersHolder searchingPlayersHolder = DependencyManager.getInstance()
                .getHoldersProvider()
                .getSearchingPlayersHolder();

        boolean isThereArePlayersWhoSearchMatch = searchingPlayersHolder.isThereArePlayersWhoSearchMatch();

        if (!isThereArePlayersWhoSearchMatch) {
            return;
        }

        boolean isPlayersWhoSearchMatchEnoughToStartTaskExecution = searchingPlayersHolder
                .isPlayersWhoSearchMatchEnoughToStartTaskExecution();

        if (!isPlayersWhoSearchMatchEnoughToStartTaskExecution) {
            return;
        }

        startProcessingPlayers();
    }

    private void startProcessingPlayers() {
        DependencyManager dependencyManager = DependencyManager.getInstance();

        List<Player> players = dependencyManager.getHoldersProvider()
                .getSearchingPlayersHolder()
                .getFixedNumberOfPlayersAndRemoveThemFromQueue(
                        DefaultConfig.DEFAULT_PLAYERS_COUNT_TO_PROCESS_BY_ONE_THREAD);

        List<Match> matches = dependencyManager.getMatchmakingService()
                .findMatchesForPlayers(players);

        if (!matches.isEmpty()) {
            dependencyManager.getMatchmakingController()
                    .sendResponseToAllPlayersInMatches(matches);
        }
    }
}

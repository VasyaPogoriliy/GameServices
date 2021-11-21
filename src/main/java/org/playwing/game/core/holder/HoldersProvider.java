package org.playwing.game.core.holder;

import lombok.Getter;
import org.playwing.game.matchmaking.holder.SearchingPlayersHolder;
import org.playwing.game.matchmaking.holder.PlayersWhoPlayMatchHolder;
import org.playwing.game.matchmaking.holder.impl.SearchingPlayersHolderImpl;
import org.playwing.game.matchmaking.holder.impl.PlayersWhoPlayMatchHolderImpl;

@Getter
public class HoldersProvider {

    private final SearchingPlayersHolder searchingPlayersHolder;
    private final PlayersWhoPlayMatchHolder playersWhoPlayMatchHolder;

    public HoldersProvider() {
        searchingPlayersHolder = new SearchingPlayersHolderImpl();
        playersWhoPlayMatchHolder = new PlayersWhoPlayMatchHolderImpl();
    }
}

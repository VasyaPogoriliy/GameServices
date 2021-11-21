package org.playwing.game.matchmaking.handler;

import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.core.handler.AbstractRequestHandler;
import org.playwing.game.core.handler.converter.BaseRequestConverter;
import org.playwing.game.matchmaking.dto.request.FindMatchRequest;
import org.playwing.game.matchmaking.handler.converter.FindMatchRequestConverter;

public class FindMatchHandler extends AbstractRequestHandler<FindMatchRequest> {

    @Override
    public void handleRequest(FindMatchRequest request) {
        DependencyManager.getInstance()
                .getMatchmakingController()
                .addPlayerToSearchMatch(request);
    }

    @Override
    public BaseRequestConverter<FindMatchRequest> getRequestConverter() {
        return new FindMatchRequestConverter();
    }
}

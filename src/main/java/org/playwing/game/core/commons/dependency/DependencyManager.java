package org.playwing.game.core.commons.dependency;

import lombok.Getter;
import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.core.commons.handlers.RequestHandlers;
import org.playwing.game.core.holder.HoldersProvider;
import org.playwing.game.core.service.TaskStarterService;
import org.playwing.game.core.service.impl.TaskStarterServiceImpl;
import org.playwing.game.matchmaking.controller.MatchmakingController;
import org.playwing.game.matchmaking.controller.impl.MatchmakingControllerImpl;
import org.playwing.game.matchmaking.service.MatchmakingService;
import org.playwing.game.matchmaking.service.MatchmakingTaskService;
import org.playwing.game.matchmaking.service.impl.MatchmakingServiceImpl;
import org.playwing.game.matchmaking.service.impl.MatchmakingTaskServiceImpl;

@Getter
public class DependencyManager {

    private static DependencyManager instance;

    private RequestHandlers requestHandlers;
    private HoldersProvider holdersProvider;
    private MatchmakingController matchmakingController;
    private MatchmakingService matchmakingService;
    private TaskStarterService taskStarterService;
    private MatchmakingTaskService matchmakingTaskService;

    public void init() {
        requestHandlers = new RequestHandlers();
        holdersProvider = new HoldersProvider();
        matchmakingController = new MatchmakingControllerImpl();
        matchmakingService = new MatchmakingServiceImpl();
        taskStarterService = new TaskStarterServiceImpl(DefaultConfig.DEFAULT_THREAD_POOL_SIZE);
        matchmakingTaskService = new MatchmakingTaskServiceImpl(taskStarterService);
    }

    public static DependencyManager getInstance() {
        if (instance == null) {
            instance = new DependencyManager();
        }

        return instance;
    }
}

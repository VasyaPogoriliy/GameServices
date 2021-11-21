package org.playwing.game.matchmaking.service.impl;

import lombok.AllArgsConstructor;
import org.playwing.game.core.commons.config.DefaultConfig;
import org.playwing.game.core.service.TaskStarterService;
import org.playwing.game.matchmaking.service.MatchmakingTaskService;
import org.playwing.game.matchmaking.tasks.ProcessingPlayersTask;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class MatchmakingTaskServiceImpl implements MatchmakingTaskService {

    TaskStarterService taskStarterService;

    @Override
    public void startProcessingPlayersTask() {
        taskStarterService.startRepeatableTask(new ProcessingPlayersTask(),
                DefaultConfig.DEFAULT_INITIAL_TASK_DELAY_IN_MILLIS,
                DefaultConfig.DEFAULT_GLOBAL_TASK_PERIOD_IN_MILLIS,
                TimeUnit.MILLISECONDS);
    }
}

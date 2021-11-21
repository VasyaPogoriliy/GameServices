package org.playwing.game.core.service.impl;

import org.playwing.game.core.commons.dependency.DependencyManager;
import org.playwing.game.core.service.TaskStarterService;

import java.util.concurrent.*;

public class TaskStarterServiceImpl implements TaskStarterService {

    private final ScheduledThreadPoolExecutor taskScheduler;

    public TaskStarterServiceImpl(int threadPoolSize) {
        this.taskScheduler = new ScheduledThreadPoolExecutor(threadPoolSize);
    }

    @Override
    public void startGlobalTasks() {
        DependencyManager dependencyManager = DependencyManager.getInstance();

        dependencyManager.getMatchmakingTaskService()
                .startProcessingPlayersTask();
    }

    @Override
    public <T> Future<T> startCallableTask(Callable<T> task) {
        return taskScheduler.submit(task);
    }

    @Override
    public void shutdown() {
        taskScheduler.shutdown();
    }

    @Override
    public void startRepeatableTask(Runnable task, int initialDelay, int period, TimeUnit unit) {
        scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    private void scheduleAtFixedRate(Runnable task, int initialDelay, int period, TimeUnit unit) {
        taskScheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }
}

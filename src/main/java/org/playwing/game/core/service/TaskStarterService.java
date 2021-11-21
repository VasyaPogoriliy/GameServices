package org.playwing.game.core.service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface TaskStarterService {

    void startGlobalTasks();

    <T> Future<T> startCallableTask(Callable<T> task);

    void shutdown();

    void startRepeatableTask(Runnable task, int initialDelay, int period, TimeUnit unit);
}

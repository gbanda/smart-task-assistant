package hms.codefest.elves.service.impl;

import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.domain.Task;
import hms.codefest.elves.service.TaskProcessingScheduler;
import hms.codefest.elves.service.TasksProcessingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by gayan on 2/23/18.
 */
public class DefaultTaskProcessingScheduler implements TaskProcessingScheduler {
    private static final Logger logger = LogManager.getLogger(DefaultTaskProcessingScheduler.class);
    private TasksProcessingService taskService;
    private ScheduledExecutorService executor;
    private int interval;
    private int threadPoolSize;

    private void init() {
        if (executor == null) {
            synchronized (this) {
                if (executor == null) {
                    executor = Executors.newScheduledThreadPool(threadPoolSize);
                }
            }
        }
    }

    @Override
    public void schedule() {
        init();
        executor.scheduleAtFixedRate(() -> {
            logger.info("Executing the task processing schedule.");
            List<BasicTask> availableTaskList = null;
            try {
                availableTaskList = taskService.getAvailableTaskList();
                System.out.println(availableTaskList);
            } catch (ExecutionException | InterruptedException e) {
                logger.error("Error occurred while attempting to retrieve the available task list from the projects server.", e);
                return;
            }
            logger.info("Retrieved [{}] tasks, submitting to process flow.", availableTaskList.size());
            if (logger.isDebugEnabled()) {
                logger.debug("Task list : [{}]", availableTaskList);
            }
            List<BasicTask> candidates = taskService.extractUpdateEligibleTasks(availableTaskList);
            System.out.println(candidates);
            logger.info("Found [{}] candidate tasks for viber message sending, submitting to connector.", availableTaskList.size());
            if (logger.isDebugEnabled()) {
                logger.debug("Task list : [{}]", availableTaskList);
            }
            if (candidates.size() > 3) {
                taskService.submitTaskToLineMessage(candidates.get(3));
            }
            logger.info("Execution of the task processing schedule completed successfully.");
        }, 10, 600, TimeUnit.SECONDS);
    }

    public void setTaskService(TasksProcessingService taskService) {
        if (taskService == null) {
            throw new IllegalArgumentException("Task service cannot be null.-");
        }
        this.taskService = taskService;
    }

    public void setInterval(int interval) {
        this.interval = Math.max(interval, 1);
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = Math.max(threadPoolSize, 1);
    }
}

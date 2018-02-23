package hms.codefest.elves.service.impl;

import hms.codefest.elves.domain.Task;
import hms.codefest.elves.service.TaskUpdateEligibilityPredicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * Created by gayan on 2/23/18.
 */
public class TaskReachedDeadlinePredicate implements TaskUpdateEligibilityPredicate{
    private static final Logger logger = LogManager.getLogger(TaskReachedDeadlinePredicate.class);

    @Override
    public boolean test(Task task) {
        LocalDateTime now = LocalDateTime.now();
        logger.info("Applying predicate at [{}] on task [{}]", now, task);
        return now.isAfter(task.startTime()) && now.isAfter(task.deadline());
    }
}

package hms.codefest.elves.service;

import hms.codefest.elves.connector.UpdateTaskResponse;
import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.domain.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by gayan on 2/22/18.
 */
public interface TasksProcessingService {
    public List<BasicTask> extractUpdateEligibleTasks(List<BasicTask> tasks);

    public List<BasicTask> getAvailableTaskList() throws ExecutionException, InterruptedException;

    public boolean submitTaskToLineMessage(BasicTask task);

    public UpdateTaskResponse submitTaskForUpdate(Task task);
}

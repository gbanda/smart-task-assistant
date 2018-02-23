package hms.codefest.elves.service;

import hms.codefest.elves.connector.UpdateTaskResponse;
import hms.codefest.elves.connector.ViberMessageResponse;
import hms.codefest.elves.domain.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by gayan on 2/22/18.
 */
public interface TasksProcessingService {
    public List<Task> extractUpdateEligibleTasks(List<Task> tasks);

    public List<Task> getAvailableTaskList() throws ExecutionException, InterruptedException;

    public ViberMessageResponse submitTaskToViberMessage(Task task);

    public UpdateTaskResponse submitTaskForUpdate(Task task);
}

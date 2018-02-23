package hms.codefest.elves.connector;

import java.util.concurrent.ExecutionException;

/**
 * Created by gayan on 2/22/18.
 */
public interface ProjectsServerConnector {
    public TasksResponse getTasks(TasksRequest request) throws ExecutionException, InterruptedException;

    public UpdateTaskResponse updateTask(UpdateTaskRequest request);
}

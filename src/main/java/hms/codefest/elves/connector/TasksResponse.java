package hms.codefest.elves.connector;

import hms.codefest.elves.domain.Task;

import java.util.List;

/**
 * Created by gayan on 2/22/18.
 */
public interface TasksResponse {
    enum Status {
        SUCCESS, REQUEST_ERROR, SERVER_ERROR, CONNECTION_FAILED, CONNECTION_TIMED_OUT
    }

    public List<Task> getTasks();
}

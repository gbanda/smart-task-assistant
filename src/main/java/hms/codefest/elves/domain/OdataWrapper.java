package hms.codefest.elves.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import hms.codefest.elves.connector.TasksContainerResponse;

/**
 * <p></p>
 *
 * @author prabath.
 */

@JsonIgnoreProperties
public class OdataWrapper {

    @JsonProperty("d")
    private TasksContainerResponse tasksContainerResponse;

    public TasksContainerResponse getTasksContainerResponse() {
        return tasksContainerResponse;
    }

    public void setTasksContainerResponse(TasksContainerResponse tasksContainerResponse) {
        this.tasksContainerResponse = tasksContainerResponse;
    }
}

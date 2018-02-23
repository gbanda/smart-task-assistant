package hms.codefest.elves.connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.domain.Task;

import java.util.List;

@JsonRootName(value = "d")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TasksContainerResponse implements TasksResponse {

    @JsonProperty("results")
    List<BasicTask> basicTasks;

    @Override
    public List<Task> getTasks() {
        return null;
    }
}

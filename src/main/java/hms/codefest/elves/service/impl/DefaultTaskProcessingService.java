package hms.codefest.elves.service.impl;

import hms.codefest.elves.connector.*;
import hms.codefest.elves.domain.Task;
import hms.codefest.elves.service.TaskUpdateEligibilityPredicate;
import hms.codefest.elves.service.TasksProcessingService;import jdk.nashorn.internal.objects.NativeUint8Array;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by gayan on 2/22/18.
 */
public class DefaultTaskProcessingService implements TasksProcessingService{
    private TaskUpdateEligibilityPredicate predicate;
    private ProjectsServerConnector projectsServerConnector;
    private ViberMessageConnector viberConnector;

    @Override
    public List<Task> extractUpdateEligibleTasks(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }
        return tasks.stream().filter(t -> predicate.test(t)).collect(Collectors.toList());
    }

    @Override
    public List<Task> getAvailableTaskList() throws ExecutionException, InterruptedException {
        TasksResponse tasks = projectsServerConnector.getTasks(null);//todo properly initialize the get tasks request
        return tasks.getTasks();
    }

    @Override
    public ViberMessageResponse submitTaskToViberMessage(Task task) {
        return viberConnector.sendViberMessage(null);//todo properly initialize viber message request
    }

    @Override
    public UpdateTaskResponse submitTaskForUpdate(Task task) {
        return projectsServerConnector.updateTask(null);//todo initialize the request properly
    }

    public void setPredicate(TaskUpdateEligibilityPredicate predicate) {
        this.predicate = predicate;
    }

    public void setProjectsServerConnector(ProjectsServerConnector projectsServerConnector) {
        this.projectsServerConnector = projectsServerConnector;
    }

    public void setViberConnector(ViberMessageConnector viberConnector) {
        this.viberConnector = viberConnector;
    }
}

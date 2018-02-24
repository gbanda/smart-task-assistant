package hms.codefest.elves.service.impl;

import hms.codefest.elves.connector.*;
import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.domain.Task;
import hms.codefest.elves.service.TaskUpdateEligibilityPredicate;
import hms.codefest.elves.service.TasksProcessingService;
import hms.codefest.elves.service.messaging.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by gayan on 2/22/18.
 */
public class DefaultTaskProcessingService implements TasksProcessingService {
    private TaskUpdateEligibilityPredicate predicate;
    private ProjectsServerConnector projectsServerConnector;
    @Autowired
    private MessageSender lineMessageSender;
    private Map<String, BasicTask> tasksSubmittedForUserResponse = new HashMap<>();

    @Override
    public List<BasicTask> extractUpdateEligibleTasks(List<BasicTask> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }
        return tasks.stream().filter(t -> predicate.test(t)).collect(Collectors.toList());
    }

    @Override
    public List<BasicTask> getAvailableTaskList() throws ExecutionException, InterruptedException {
        DefaultTasksRequest.Builder builder = new DefaultTasksRequest.Builder();
        DefaultTasksRequest request = builder.adminPassword("R@njanaH745").adminUsername("ranjana")
                .projectId("17a0176a-c917-e811-85df-3464a9bf110d")
                .userName("aroshar").userPassword("A#rosha@456").build();
        TasksResponse tasks = projectsServerConnector.getTasks(request);
        TasksContainerResponse containerResponse = (TasksContainerResponse) tasks;
        return containerResponse.getBasicTasks();
    }

    @Override
    public boolean submitTaskToLineMessage(BasicTask task) {
        if (tasksSubmittedForUserResponse.get("aroshar") != null) {
            return false;
        }
        String message = "The task assigned to you on project 'Smart Task Manager' with title 'Implement project manager notifications " +
                "on assignee updates' to commenced on " + task.getStartDate().toString() + "is pending status update. Reply with the current " +
                "completed percentage or any message you would like to save as the reply?";
        tasksSubmittedForUserResponse.put("aroshar", task);
        return lineMessageSender.pushMessage(message, "aroshar");
    }

    public void clearSubmittedTask(String user) {
        tasksSubmittedForUserResponse.remove(user);
    }

    @Override
    public UpdateTaskResponse submitTaskForUpdate(Task task) {
        return null;
    }

    public UpdateTaskResponse submitTaskForUpdate(BasicTask task, int percentage) {
        BasicUpdateTaskRequest updateTaskRequest = new BasicUpdateTaskRequest();
        updateTaskRequest.setAdminPassword("R@njanaH745");
        updateTaskRequest.setAdminUsername("ranjana");
        updateTaskRequest.setAssignmentUrl(task.getMetadata().getUri());
        updateTaskRequest.setProjectId("17a0176a-c917-e811-85df-3464a9bf110d");
        updateTaskRequest.setUpdatedPercentage(percentage);
        updateTaskRequest.setUserName("aroshar");
        updateTaskRequest.setUserPassword("A#rosha@456");
        return projectsServerConnector.updateTask(updateTaskRequest);
    }

    public BasicTask findSubmittedTaskByUser(String userName) {
        return tasksSubmittedForUserResponse.getOrDefault(userName, new BasicTask());
    }

    public void setPredicate(TaskUpdateEligibilityPredicate predicate) {
        this.predicate = predicate;
    }

    public void setProjectsServerConnector(ProjectsServerConnector projectsServerConnector) {
        this.projectsServerConnector = projectsServerConnector;
    }

    public void setLineMessageSender(MessageSender lineMessageSender) {
        this.lineMessageSender = lineMessageSender;
    }
}

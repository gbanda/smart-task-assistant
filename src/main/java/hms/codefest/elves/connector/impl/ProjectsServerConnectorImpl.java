package hms.codefest.elves.connector.impl;

import hms.codefest.elves.connector.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * Created by gayan on 2/22/18.
 */
public class ProjectsServerConnectorImpl implements ProjectsServerConnector{

    private Client jaxRsClient;
    private WebTarget projectsServerWebTarget;
    private String targetUrl;

    public void init() {
        if (targetUrl == null || targetUrl.isEmpty()) {
            throw new IllegalArgumentException("The target URL for the projects web server has not been specified");
        }
        jaxRsClient = ClientBuilder.newClient();
        projectsServerWebTarget = jaxRsClient.target(targetUrl);
    }

    public TasksResponse getTasks(TasksRequest request) throws ExecutionException, InterruptedException {
        if (request.getRequestPath() == null || request.getRequestPath().isEmpty()) {
            return null;
        }
        WebTarget getTasksWebPath = projectsServerWebTarget.path(request.getRequestPath());
        Invocation.Builder invocationBuilder = getTasksWebPath.request(MediaType.APPLICATION_XML);
        TasksResponse response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_XML), TasksResponse.class);
        //todo validate the received response and set the response status
        return response;
    }

    public UpdateTaskResponse updateTask(UpdateTaskRequest request) {
        if (request.getRequestPath() == null || request.getRequestPath().isEmpty()) {
            return null;
        }
        WebTarget updateTaskWebPath = projectsServerWebTarget.path(request.getRequestPath());
        Invocation.Builder invocationBuilder = updateTaskWebPath.request(MediaType.APPLICATION_XML);
        Invocation invocation = invocationBuilder.build("patch", Entity.entity(request, MediaType.APPLICATION_XML));
        UpdateTaskResponse response = invocation.invoke(UpdateTaskResponse.class);
        //todo validate the received response and set the response status
        return response;
    }

    public void destroy() {
        jaxRsClient.close();
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}

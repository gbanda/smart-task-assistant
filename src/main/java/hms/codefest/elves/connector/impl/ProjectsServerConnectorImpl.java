package hms.codefest.elves.connector.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import hms.codefest.elves.connector.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

/**
 * Created by gayan on 2/22/18.
 */
public class ProjectsServerConnectorImpl implements ProjectsServerConnector {

    private Client jaxRsClient;

    private WebTarget projectsServerWebTarget;

    private String targetUrl;

    public void init() {
        ClientConfig clientConfig = new ClientConfig();

        if (targetUrl == null || targetUrl.isEmpty()) {
            throw new IllegalArgumentException("The target URL for the projects web server has not been specified");
        }
        jaxRsClient = ClientBuilder.newClient();
        projectsServerWebTarget = jaxRsClient.target(targetUrl);
    }

    public String executeRestGet(String user, String pass) {
        Client client = ClientBuilder.newClient(prepareClientConfig(user, pass));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('17a0176a-c917-e811-85df-3464a9bf110d')");
        return target.request().accept(MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                .header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
    }

    private ClientConfig prepareClientConfig(String user, String pass) {
        ClientConfig clientConfig = new ClientConfig();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new NTCredentials(user, pass, null, null));

        clientConfig.property(ApacheClientProperties.CREDENTIALS_PROVIDER, credentialsProvider);
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        return clientConfig;
    }

    public TasksResponse getTasks(TasksRequest request) throws ExecutionException, InterruptedException {
        if (request.getRequestPath() == null || request.getRequestPath().isEmpty()) {
            return null;
        }
        DefaultTasksRequest defaultTasksRequest = (DefaultTasksRequest) request;
        Client client = ClientBuilder
                .newClient(prepareClientConfig(defaultTasksRequest.getUserName(), defaultTasksRequest.getUserPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + defaultTasksRequest.getProjectId() +
                        "')/Assignments$select=ActualCostWorkPerformed,Start,PercentWorkComplete,results/__metadata/uri");
        String responseJsonStr = target.request().accept(MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                .header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        return createTaskResponse(responseJsonStr);
    }

    private TasksContainerResponse createTaskResponse(String jsonResponse) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        ObjectReader reader = objectMapper.reader(TasksContainerResponse.class);
        try {
            return reader.readValue(jsonResponse.getBytes());
        } catch (Exception e) {
            return null;
        }
    }

    public UpdateTaskResponse updateTask(UpdateTaskRequest request) {
        if (request.getRequestPath() == null || request.getRequestPath().isEmpty()) {
            return null;
        }
        BasicUpdateTaskRequest updateTaskRequest = (BasicUpdateTaskRequest) request;
        checkOut(updateTaskRequest);
        updateTask(updateTaskRequest);
        publishTask(updateTaskRequest);
        return new BasicUpdateTaskResponse(true);
    }

    private boolean publishTask(BasicUpdateTaskRequest updateTaskRequest) {

        String digest = getDigest(updateTaskRequest);
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + updateTaskRequest.getProjectId() + "')/Draft/Publish(true)");
        String checkoutResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose").post(Entity.json(null), String.class);
        return true;
    }

    private boolean updateTask(BasicUpdateTaskRequest updateTaskRequest) {
        String digest = getDigest(updateTaskRequest);
        String updatedUrl = updateTaskRequest.getAssignmentUrl().replaceFirst("Assignment", "Draft/Assignment");
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        int updatedPercentage = updateTaskRequest.getUpdatedPercentage();
        String updateRequest = "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updatedPercentage + "}";
        String checkoutResponse =
                client.target(updatedUrl).request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                        .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                        .method("patch", Entity.json(updateRequest), String.class);
        return true;
    }

    private boolean checkOut(BasicUpdateTaskRequest updateTaskRequest) {
        String digest = getDigest(updateTaskRequest);
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + updateTaskRequest.getProjectId() + "')");
        String checkoutResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                .header("Content-Type", MediaType.APPLICATION_JSON_TYPE).post(Entity.json(null), String.class);
        return true;
    }

    private String getDigest(BasicUpdateTaskRequest updateTaskRequest) {
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("contextinfo");
        DigestResponse digestResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                .post(Entity.json(null), DigestResponse.class);

        return digestResponse.getDigest();

    }

    public void destroy() {
        jaxRsClient.close();
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}

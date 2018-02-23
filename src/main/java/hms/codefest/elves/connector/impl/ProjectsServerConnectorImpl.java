package hms.codefest.elves.connector.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import hms.codefest.elves.connector.*;
import hms.codefest.elves.domain.OdataWrapper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
        DefaultTasksRequest defaultTasksRequest = (DefaultTasksRequest) request;
        Client client = ClientBuilder
                .newClient(prepareClientConfig(defaultTasksRequest.getUserName(), defaultTasksRequest.getUserPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + defaultTasksRequest.getProjectId() +
                        "')/Assignments");
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
        BasicUpdateTaskRequest updateTaskRequest = (BasicUpdateTaskRequest) request;
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        String digest = getDigest(updateTaskRequest);
        digest = checkOut(updateTaskRequest, digest, client);
        updateTask(updateTaskRequest, digest, client);
        publishTask(updateTaskRequest, digest, client);
        return new BasicUpdateTaskResponse(true);
    }

    private boolean publishTask(BasicUpdateTaskRequest updateTaskRequest, String digest, Client client) {

        //        String digest = getDigest(updateTaskRequest);
/*
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
*/
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + updateTaskRequest.getProjectId() + "')/Draft/Publish(true)");
        try {
            String checkoutResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                    .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose").header("Content-Length", 0)
                    .post(Entity.json(null), String.class);
        } catch (ForbiddenException e) {
            digest = (String) e.getResponse().getHeaders().get("X-RequestDigest").get(0);
            target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                    .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose").header("Content-Length", 0)
                    .post(Entity.json(null), String.class);
        }
        return true;
    }

/*
    private boolean updateTask(BasicUpdateTaskRequest updateTaskRequest, String digest, Client client) {
        //        String digest = getDigest(updateTaskRequest);
        String updatedUrl = updateTaskRequest.getAssignmentUrl().replaceFirst("Assignment", "Draft/Assignment");
        int updatedPercentage = updateTaskRequest.getUpdatedPercentage();
        //        String updateRequest = "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updatedPercentage + "}";
        String updateRequest = "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updatedPercentage + "}";
        String checkoutResponse =
                client.target(updatedUrl).request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                        .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                        .method("PATCH", Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE), String.class);
        return true;
    }
*/

    private boolean updateTask(BasicUpdateTaskRequest updateTaskRequest, String digest, Client client) {
        //        String digest = getDigest(updateTaskRequest);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new NTCredentials(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword(), null, null));
        String updatedUrl = updateTaskRequest.getAssignmentUrl().replaceFirst("Assignment", "Draft/Assignment");
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpPatch request = new HttpPatch(updatedUrl);
        request.setHeader("X-RequestDigest", digest);
/*
        String updateRequest =
                "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updateTaskRequest.getUpdatedPercentage() + "}";
*/
        String updateRequest =
                "{\"__metadata\":{\"type\":\"PS.DraftAssignment\"}, \"PercentWorkComplete\": " + updateTaskRequest.getUpdatedPercentage() + "}";
        request.setHeader("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose");
        request.setEntity(new StringEntity(updateRequest, ContentType.APPLICATION_JSON));
        try {
            httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }


       /* int updatedPercentage = updateTaskRequest.getUpdatedPercentage();
        //        String updateRequest = "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updatedPercentage + "}";
        String updateRequest = "{__metadata:{type:PS.DraftAssignment}, PercentWorkComplete: " + updatedPercentage + "}";
        String checkoutResponse =
                client.target(updatedUrl).request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                        .header("Content-Type", MediaType.APPLICATION_JSON_TYPE + ";odata=verbose")
                        .method("PATCH", Entity.entity("{}", MediaType.APPLICATION_JSON_TYPE), String.class);*/
        return true;
    }

    private String checkOut(BasicUpdateTaskRequest updateTaskRequest, String digest, Client client) {
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + updateTaskRequest.getProjectId() + "')/checkOut()");
        //        String digest = getDigest(updateTaskRequest);
        String checkoutResponse;
        try {
            checkoutResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE)
                    .header("Content-Length", 0).header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(null), String.class);
        } catch (ForbiddenException e) {
            digest = (String) e.getResponse().getHeaders().get("X-RequestDigest").get(0);
            checkIn(digest, updateTaskRequest, client);
            checkoutResponse = target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                    .header("Content-Length", 0).header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.json(null), String.class);
        }
        return digest;
    }

    private void checkIn(String digest, BasicUpdateTaskRequest updateTaskRequest, Client client) {
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("ProjectServer/Projects('" + updateTaskRequest.getProjectId() + "')/Draft/checkin()");
        target.request().accept(MediaType.APPLICATION_JSON_TYPE).header("X-RequestDigest", digest)
                .header("Content-Length", 0).header("Content-Type", MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(null), String.class);
    }

    private String getDigest(BasicUpdateTaskRequest updateTaskRequest) {
        Client client = ClientBuilder
                .newClient(prepareClientConfig(updateTaskRequest.getAdminUsername(), updateTaskRequest.getAdminPassword()));
        WebTarget target = client
                .target("http://projectserver/PWA/_api")
                .path("contextinfo");
        String response =
                target.request().accept(MediaType.APPLICATION_JSON_TYPE + ";odata=verbose").header("Content-Length", 0)
                        .post(Entity.json(null), String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        ObjectReader reader = objectMapper.reader(OdataWrapper.class);
        try {
            DigestResponse digestResponse = ((OdataWrapper) reader.readValue(response.getBytes())).getDigestResponse();
            if (digestResponse != null) {
                return digestResponse.getDigest();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void destroy() {
        jaxRsClient.close();
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}

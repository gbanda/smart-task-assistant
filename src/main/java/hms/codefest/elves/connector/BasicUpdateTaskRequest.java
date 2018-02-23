package hms.codefest.elves.connector;

/**
 * <p></p>
 *
 * @author prabath.
 */
public class BasicUpdateTaskRequest implements UpdateTaskRequest {

    private String projectId;

    private String adminPassword;

    private String adminUsername;

    private String userName;

    private String userPassword;

    private String assignmentUrl;

    private int updatedPercentage;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAssignmentUrl() {
        return assignmentUrl;
    }

    public void setAssignmentUrl(String assignmentUrl) {
        this.assignmentUrl = assignmentUrl;
    }

    public int getUpdatedPercentage() {
        return updatedPercentage;
    }

    public void setUpdatedPercentage(int updatedPercentage) {
        this.updatedPercentage = updatedPercentage;
    }

    @Override
    public String getRequestPath() {
        return null;
    }
}

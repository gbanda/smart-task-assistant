package hms.codefest.elves.connector;

/**
 * <p></p>
 *
 * @author prabath.
 */
public class DefaultTasksRequest implements TasksRequest {

    private String projectId;

    private String adminPassword;

    private String adminUsername;

    private String userName;

    private String userPassword;

    private DefaultTasksRequest(Builder builder) {
        this.projectId = builder.projectId;
        this.adminPassword = builder.adminPassword;
        this.adminUsername = builder.adminUsername;
        this.userName = builder.userName;
        this.userPassword = builder.userPassword;
    }

    public static Builder newDefaultTasksRequest() {
        return new Builder();
    }

    @Override
    public String getRequestPath() {
        return null;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getUserName() {
        return userName;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultTasksRequest{");
        sb.append("projectId='").append(projectId).append('\'');
        sb.append(", adminPassword='").append(adminPassword).append('\'');
        sb.append(", adminUsername='").append(adminUsername).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static final class Builder {

        private String projectId;

        private String adminPassword;

        private String adminUsername;

        private String userName;

        private String userPassword;

        public Builder() {
        }

        public DefaultTasksRequest build() {
            return new DefaultTasksRequest(this);
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder adminPassword(String adminPassword) {
            this.adminPassword = adminPassword;
            return this;
        }

        public Builder adminUsername(String adminUsername) {
            this.adminUsername = adminUsername;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }
    }
}

package hms.codefest.elves.connector;

/**
 * <p></p>
 *
 * @author prabath.
 */
public class BasicUpdateTaskRequest implements UpdateTaskRequest {

    private String assignmentUrl;

    private int updatedPercentage;

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

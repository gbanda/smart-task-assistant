package hms.codefest.elves.connector;

/**
 * <p></p>
 *
 * @author prabath.
 */
public class BasicUpdateTaskResponse implements UpdateTaskResponse {

    private boolean isSuccess;

    public BasicUpdateTaskResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}

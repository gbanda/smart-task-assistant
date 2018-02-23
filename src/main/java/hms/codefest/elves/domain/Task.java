package hms.codefest.elves.domain;

import java.time.LocalDateTime;

/**
 * Created by gayan on 2/22/18.
 */
public interface Task {
    public LocalDateTime startTime();

    public LocalDateTime deadline();

    public String toString();
}

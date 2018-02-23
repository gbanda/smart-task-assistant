package hms.codefest.elves.domain;

import java.time.LocalDateTime;

/**
 * Created by gayan on 2/22/18.
 */
public interface Task {
    LocalDateTime startTime();

    LocalDateTime deadline();

    String toString();
}

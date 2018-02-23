package hms.codefest.elves.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p></p>
 *
 * @author prabath.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicTask implements Task {

    @JsonProperty("__metadata")
    private Metadata metadata;

    @JsonProperty("ActualCostWorkPerformed")
    private float actualCostWorkPerformed;

    @JsonProperty("Start")
    private Date startDate;

    @JsonProperty("PercentWorkComplete")
    private int percentWorkComplete;

    public float getActualCostWorkPerformed() {
        return actualCostWorkPerformed;
    }

    public void setActualCostWorkPerformed(float actualCostWorkPerformed) {
        this.actualCostWorkPerformed = actualCostWorkPerformed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getPercentWorkComplete() {
        return percentWorkComplete;
    }

    public void setPercentWorkComplete(int percentWorkComplete) {
        this.percentWorkComplete = percentWorkComplete;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public LocalDateTime startTime() {
        return null;
    }

    @Override
    public LocalDateTime deadline() {
        return null;
    }
}

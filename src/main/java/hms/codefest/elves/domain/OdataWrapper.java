package hms.codefest.elves.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import hms.codefest.elves.connector.DigestResponse;

/**
 * <p></p>
 *
 * @author prabath.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("d")
public class OdataWrapper {

    @JsonProperty("GetContextWebInformation")
    private DigestResponse digestResponse;

    public DigestResponse getDigestResponse() {
        return digestResponse;
    }

    public void setDigestResponse(DigestResponse digestResponse) {
        this.digestResponse = digestResponse;
    }
}

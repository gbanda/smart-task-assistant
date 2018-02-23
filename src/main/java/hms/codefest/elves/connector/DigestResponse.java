package hms.codefest.elves.connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p></p>
 *
 * @author prabath.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DigestResponse {

    @JsonProperty("FormDigestValue")
    private String digest;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}

package hms.codefest.elves.connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * <p></p>
 *
 * @author prabath.
 */
@JsonRootName(value = "d")
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

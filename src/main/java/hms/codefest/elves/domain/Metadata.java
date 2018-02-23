package hms.codefest.elves.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <p></p>
 *
 * @author prabath.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

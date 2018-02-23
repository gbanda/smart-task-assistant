package hms.codefest.elves.connector;

/**
 * Created by gayan on 2/23/18.
 */
public interface ViberMessageConnector {
    public ViberMessageResponse sendViberMessage(ViberMessage message);
}

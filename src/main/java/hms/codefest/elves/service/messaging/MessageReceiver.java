package hms.codefest.elves.service.messaging;

public interface MessageReceiver {

    boolean onMessageReceive(String msg, String userName);

}

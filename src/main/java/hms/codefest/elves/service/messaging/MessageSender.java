package hms.codefest.elves.service.messaging;

public interface MessageSender {

    boolean pushMessage(String msg, String userName);

}

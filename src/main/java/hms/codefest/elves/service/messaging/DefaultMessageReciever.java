package hms.codefest.elves.service.messaging;

import hms.codefest.elves.domain.BasicTask;
import hms.codefest.elves.service.impl.DefaultTaskProcessingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gayan on 2/23/18.
 */
public class DefaultMessageReciever implements MessageReceiver{
    @Autowired
    private TextMessageSender replySender;
    @Autowired
    private DefaultTaskProcessingService taskProcessingService;

    private Map<String, String> userToUpdatePerc = new HashMap<>();

    @Override
    public boolean onMessageReceive(String msg, String userName) {
        userName = userName == null ? "aroshar" : userName;
        if (msg == null || msg.isEmpty()) {
            replySender.pushMessage("Sorry, your reply was empty. PLease try again.", userName);

        }else  if (msg.matches("^(100|[0-9]{2})$")) {
            replySender.pushMessage("Thank you for the update, would you like to save the change and publish the task?", userName);
            userToUpdatePerc.put(userName, msg);
        } else if (msg.toLowerCase().equals("yes")) {
            replySender.pushMessage("Thank you. We will notify the project manager regarding your update. Have a nice day!", userName);
            BasicTask submittedTaskByUser = taskProcessingService.findSubmittedTaskByUser(userName);
            taskProcessingService.submitTaskForUpdate(submittedTaskByUser, Integer.parseInt(userToUpdatePerc.getOrDefault(userName, "12")));
            taskProcessingService.clearSubmittedTask(userName);
        } else {
            replySender.pushMessage("Thank you for the response, reply with 'notify' if you would like to get project managers attention " +
                    "on this. If not we will save your message in our action history, have a nice day.", userName);
        }
        return true;
    }
}
;
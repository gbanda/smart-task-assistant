package hms.codefest.elves.service.messaging;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import hms.codefest.elves.data.User;
import hms.codefest.elves.data.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextMessageSender implements MessageSender {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean pushMessage(String msg, String userName) {
        User user = userRepo.findUser(userName);
        PushMessage pushMessage = new PushMessage(user.getUserId(), new TextMessage(msg));
        lineMessagingClient.pushMessage(pushMessage);
        return true;
    }
}

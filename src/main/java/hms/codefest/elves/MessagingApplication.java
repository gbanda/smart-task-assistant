package hms.codefest.elves;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import hms.codefest.elves.data.UserRepo;
import hms.codefest.elves.service.messaging.MessageReceiver;
import hms.codefest.elves.service.messaging.TextMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@LineMessageHandler
@ImportResource("classpath:spring-context.xml")
public class MessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private TextMessageSender textMessageSender;

    @Autowired
    private MessageReceiver messageReceiver;

    @Autowired
    private UserRepo userRepo;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
//        messageReceiver.onMessageReceive(event.getMessage().getText(),
//                userRepo.findUserById(event.getSource().getUserId()).getUserName());
        textMessageSender.pushMessage("Hi all", "aroshar");
    }
}

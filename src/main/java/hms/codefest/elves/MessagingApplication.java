package hms.codefest.elves;

/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
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

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);
        System.out.println("userID" + event.getSource().getUserId());
        //U5c2208351ef1a0aeea2be23596710c95
        //U5c2208351ef1a0aeea2be23596710c95
        pushTextMessage();
        return new TextMessage(event.getMessage().getText());
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    public void pushTextMessage() {
        PushMessage pushMessage = new PushMessage("U5c2208351ef1a0aeea2be23596710c95", new TextMessage("Hi Good morning"));
        lineMessagingClient.pushMessage(pushMessage);
    }

    public void replyText(MessageEvent<TextMessageContent> messageEvent, String message) {
        try {
            BotApiResponse botApiResponse = lineMessagingClient.replyMessage(new ReplyMessage(messageEvent.getReplyToken(), new TextMessage(message))).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleReply(MessageEvent<TextMessageContent> messageEvent) {

        boolean updated = false;
        if(messageEvent.getMessage().toString().startsWith("register ")) {

            replyText(messageEvent, "Hello" + messageEvent.getSource().getSenderId());
            //pushMessage(messageEvent, new TextMessage("Hello! " + messageEvent.getSource().getSenderId()));
        }
    }
}

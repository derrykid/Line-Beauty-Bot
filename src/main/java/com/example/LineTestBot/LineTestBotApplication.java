package com.example.LineTestBot;

import com.example.LineTestBot.Message.TestOut;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
@AllArgsConstructor
@Slf4j
public class LineTestBotApplication {

    static String derryID = "U3f3dc951e3cfb83333415a2df55f0fe1";
    static String anotherID = "Uc9e70cd5e5c151598d1c8fb58c7dbc3d";

    public static void main(String[] args) {
        SpringApplication.run(LineTestBotApplication.class, args);
    }


    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) {
        log(event);

        if (event.getMessage().getText().equalsIgnoreCase("push")) {
            pushMessage(event);
            return null;
        }

        return new TextMessage("Default");
    }

    public static void pushMessage(MessageEvent<TextMessageContent> event) {
        final LineMessagingClient client = LineMessagingClient
                .builder("N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=")
                .build();

        String userName = getUserName(event);

        final TextMessage textMessage = new TextMessage("This is your display name: " + userName);
        final PushMessage pushMessage = new PushMessage(event.getSource().getUserId(), textMessage);

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

//		final LineMessagingClient clientWithBear = LineMessagingClient
//				.builder("Bearer N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=")
//						.build();
//
//		final PushMessage push = new PushMessage(userID, new TextMessage("I need bearer QQ!"));
//		final BotApiResponse botApiResponse1;
//		try {
//			botApiResponse1 = client.pushMessage(push).get();
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//			return;
//		}

    }

    private static String getUserName(MessageEvent<TextMessageContent> event) {
        final LineMessagingClient client = LineMessagingClient
                .builder("N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=")
                .build();

        final UserProfileResponse userProfileResponse;
        try {
            userProfileResponse = client.getProfile(event.getSource().getUserId()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "";
        }

        return "DisplayName method:" + userProfileResponse.getDisplayName() + "\n" ;
    }


    public static void log(MessageEvent<TextMessageContent> event) {
        log.info("Event: " + event);
        System.out.println("Event Log: " + event);
    }

}

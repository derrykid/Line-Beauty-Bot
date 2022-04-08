package com.example.LineTestBot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
@AllArgsConstructor
@Slf4j
public class LineTestBotApplication {

    static String derryID = "U3f3dc951e3cfb83333415a2df55f0fe1";
    static String anotherID = "Uc9e70cd5e5c151598d1c8fb58c7dbc3d";
    static String test_token = "N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=";
    static String groupID;

    public static void main(String[] args) {
        SpringApplication.run(LineTestBotApplication.class, args);
    }


    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) throws URISyntaxException, IOException, InterruptedException {
        log.info("event: " + event);
        this.groupID = event.getSource().getSenderId();

        pushMsg pushMsg = new pushMsg();
        pushMsg.run();
        pushMsg.wait(3000L);
        pushMsg.run();

        return new TextMessage("Alive");

    }



    public static void log(MessageEvent<TextMessageContent> event) {
        log.info("Event: " + event);
        System.out.println("Event Log: " + event);
    }

}

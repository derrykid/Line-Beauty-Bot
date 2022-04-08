package com.example.LineTestBot;

import com.example.LineTestBot.Message.TestOut;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) throws URISyntaxException, IOException, InterruptedException {


        return new TextMessage("hello");
    }



    public static void log(MessageEvent<TextMessageContent> event) {
        log.info("Event: " + event);
        System.out.println("Event Log: " + event);
    }

}

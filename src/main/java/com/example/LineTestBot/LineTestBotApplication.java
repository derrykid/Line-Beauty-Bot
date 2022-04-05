package com.example.LineTestBot;

import com.example.LineTestBot.Message.GameSet;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@LineMessageHandler
@AllArgsConstructor
@Slf4j
public class LineTestBotApplication {


	public static void main(String[] args) {
		SpringApplication.run(LineTestBotApplication.class, args);
	}


	@EventMapping
	public Message handleTextMessage(MessageEvent<TextMessageContent> event){
		log(event);

		String command = event.getMessage().getText();

		if (command.equalsIgnoreCase("group")) {
			return new TextMessage("It's group ID " + event.getSource().getSenderId());
		} else if (command.equalsIgnoreCase("user")) {
			return new TextMessage("It's user ID " + event.getSource().getUserId());
		} else if (command.equalsIgnoreCase("source")){
			return new TextMessage("It's get source: " +event.getSource().toString());
		} else {
			return new TextMessage("It's default");
		}


	}

	public static void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

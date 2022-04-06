package com.example.LineTestBot;

import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@LineMessageHandler
@AllArgsConstructor
@Slf4j
public class LineTestBotApplication {


	public static void main(String[] args) {
		SpringApplication.run(LineTestBotApplication.class, args);
	}


	@EventMapping
	public PushMessage handleTextMessage(MessageEvent<TextMessageContent> event){
		log(event);

		String userID = "Uc9e70cd5e5c151598d1c8fb58c7dbc3d" ;
		PushMessage pushMessage = new PushMessage(userID, new TextMessage("hallo"));

		return pushMessage;


	}

	public static void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

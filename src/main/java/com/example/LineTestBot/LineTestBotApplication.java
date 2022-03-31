package com.example.LineTestBot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@LineMessageHandler
@Slf4j
public class LineTestBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LineTestBotApplication.class, args);
	}

	@EventMapping
	public Message handleTextMessage(MessageEvent<TextMessageContent> event){

		return new TextMessage("Hello");
	}

	@EventMapping
	public void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

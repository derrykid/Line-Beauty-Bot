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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
@LineMessageHandler
@AllArgsConstructor
@Slf4j
public class LineTestBotApplication {

	@Value("${line.bot.channel-token}")
	private String channel_token;

	public static void main(String[] args) {
		SpringApplication.run(LineTestBotApplication.class, args);
	}



	@EventMapping
	public void handleTextMessage(MessageEvent<TextMessageContent> event){
		log(event);

		final LineMessagingClient lineMessagingClient = LineMessagingClient.builder(channel_token).build();

		lineMessagingClient.pushMessage(new PushMessage(event.getSource().getUserId(), new TextMessage("Hello, some update")));

	}

	public static void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

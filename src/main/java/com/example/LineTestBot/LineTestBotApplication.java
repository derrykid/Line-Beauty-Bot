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

	private String channel_token;

	public static void main(String[] args) {
		SpringApplication.run(LineTestBotApplication.class, args);
	}

	@PostConstruct
	void init(){
		this.channel_token = "N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=";
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

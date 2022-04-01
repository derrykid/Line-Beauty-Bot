package com.example.LineTestBot;

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

		String replyToken = event.getReplyToken();

		// Textmessage echo bot with extra emoji
		TextMessage.Emoji emoji = TextMessage.Emoji.builder().productId("5ac21c4e031a6752fb806d5b").emojiId("009").build();
		List<TextMessage.Emoji> emojiList = new ArrayList<>();
		emojiList.add(emoji);
		TextMessage msg = TextMessage.builder().text(event.getMessage().getText()).emojis(emojiList).build();


		return msg;

	}

	public static void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

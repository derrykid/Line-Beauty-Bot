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

		// Textmessage echo bot with extra emoji
		TextMessage.Emoji emoji = TextMessage.Emoji.builder().productId("5ac21a18040ab15980c9b43e").emojiId("070").build();
		List<TextMessage.Emoji> emojiList = new ArrayList<>();
		emojiList.add(emoji);

		StringBuilder replyMessage = new StringBuilder("$ ");
		replyMessage.append(event.getMessage().getText());

		TextMessage msg = TextMessage.builder().text(replyMessage.toString()).quickReply(null).sender(null).emojis(emojiList).build();

		double userId = Double.parseDouble(event.getSource().getUserId());
		GameSet game = new GameSet(userId, "Poker");

		if(event.getMessage().getText().equals("/getgame")){
			return new TextMessage(game.toString());
		}

		return msg;


	}

	public static void log(MessageEvent<TextMessageContent> event){
		log.info("Event: " + event);
		System.out.println("Event Log: " + event);
	}

}

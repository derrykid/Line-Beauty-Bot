package com.example.LineTestBot.Message;

import com.example.LineTestBot.LineTestBotApplication;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

public class TestOut {
    public static void push(MessageEvent<TextMessageContent> event) {
        String userID = event.getSource().getUserId();
        LineTestBotApplication.pushMessage(userID);
    }
}

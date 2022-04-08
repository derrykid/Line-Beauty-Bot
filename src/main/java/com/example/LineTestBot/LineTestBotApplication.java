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

    public static void main(String[] args) {
        SpringApplication.run(LineTestBotApplication.class, args);
    }


    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event)  throws URISyntaxException, IOException, InterruptedException{
        log.info("event: " + event);

        String targetURL =  "https://api.pokerapi.dev/v1/winner/texas_holdem?cc=AC,KD,QH,JS,7C&pc[]=10S,8C&pc[]=3S,2C";
        try {
            URL url = new URL( targetURL );
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 設定請求引數（過期時間，輸入、輸出流、訪問方式），以流的形式進行連線 */
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            int code = connection.getResponseCode();
            String msg = "";
            if (code == 200) { // 正常響應
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = null;

                while ((line = reader.readLine()) != null) { // 迴圈從流中讀取
                    msg += line + "\n";
                }
                reader.close(); // 關閉流
            }
            connection.disconnect();
            return new TextMessage("hahaha");
        } catch (IOException e) {
            e.printStackTrace();
            return new TextMessage("This is hereee");
        }
    }


    public static void log(MessageEvent<TextMessageContent> event) {
        log.info("Event: " + event);
        System.out.println("Event Log: " + event);
    }

}

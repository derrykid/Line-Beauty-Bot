package com.example.LineTestBot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class pushMsg implements Runnable {

    static String test_token = "N6UpY0AcuaoeOd4g3YYL3DNqXF8tzIGcaXZ4oAWF8Wa+S4tIwhbufl15UCkS+am82kxgM8rBnRyXwgwYhIY1hmu+kh8NCckUZNRImthycZFA7dv5Oljwns8e117Bon2rOfM+uyfe84vSjk+Y7tkBigdB04t89/1O/w1cDnyilFU=";


    @Override
    public void run() {
        String groupID = LineTestBotApplication.groupID;
        String targetURL = "https://api.pokerapi.dev/v1/winner/texas_holdem?cc=AC,KD,QH,JS,7C&pc[]=10S,8C&pc[]=3S,2C";
        try {
            URL url = new URL(targetURL);
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
            push(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private static void push(String msg){
        // push stuff
        String groupID = LineTestBotApplication.groupID;
        String token = test_token;

        LineMessagingClient client = LineMessagingClient.builder(token).build();


        final TextMessage textMessage = new TextMessage("Push ya");
        final PushMessage pushMessage = new PushMessage(groupID, textMessage);

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Push message error occurs");
            return;
        }

    }
}

// one another way: save it to map and retreive later
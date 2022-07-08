package club.derry;

import club.derry.basic.PingTask;
import club.derry.ptt.PttTask;
import club.derry.routine.Routine;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@LineMessageHandler
@Slf4j
public class Application {

    private static String configPath = "src/main/resources/application.yml";

    private static Map<String, Boolean> registerMap = new HashMap<>();

    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        String groupId = event.getSource().getSenderId();
        String text =event.getMessage().getText();

        if (!isRegister(groupId) && isSubscribe(text)) {
            Routine.registerRoutine(new PttTask(groupId, configPath),
                    24, TimeUnit.HOURS);
        }

        if (isRegister(groupId) && isUnsubscribe(text)) {
            Routine.onShutDown();
        }

        return null;
    }

    private boolean isUnsubscribe(String text) {
        String cmd =  text.split(" ")[0].substring(1).toLowerCase();
        return cmd.equalsIgnoreCase("unsubscribe");
    }

    private boolean isSubscribe(String text) {
        String cmd =  text.split(" ")[0].substring(1).toLowerCase();
        return cmd.equalsIgnoreCase("subscribe");
    }

    /**
     * Check if the chat room has already registered the routine
     * @param groupId
     * @return boolean
     */
    private static boolean isRegister(String groupId) {
        return registerMap.containsKey(groupId);
    }
}

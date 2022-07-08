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

        if (!isRegister(groupId)) {
            Routine.registerRoutine(new PttTask(groupId, configPath),
                    24, TimeUnit.HOURS);
        }
        return null;
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

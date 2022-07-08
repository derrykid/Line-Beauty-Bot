package club.derry;

import club.derry.basic.PingTask;
import club.derry.ptt.PttTask;
import club.derry.routine.Routine;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@LineMessageHandler
@Slf4j
public class Application {

    private static String configPath = "src/main/resources/application.yml";

    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        String isRequired = event.getMessage().getText();

        if (isRequired.toLowerCase().contains("/get")) {
            String groupId = event.getSource().getSenderId();
            Routine.registerRoutine(groupId, new PttTask(groupId, configPath),
                    24, TimeUnit.HOURS);
        }

        if (isRequired.toLowerCase().contains("/ping")) {
            String groupId = event.getSource().getSenderId();
            Routine.registerRoutine(groupId, new PingTask(groupId, configPath),
                    10, TimeUnit.SECONDS);
        }

        return null;
    }
}

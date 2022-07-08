package club.derry;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

@LineMessageHandler
@Slf4j
public class Application {

    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        URI uri = null;
        try {
            uri = new URI("https://i.imgur.com/sHQiOsk.jpg");
        } catch (URISyntaxException e) {
            log.error("Error occur when forming uri, {}", e);
        }

        return new ImageMessage(uri, uri);
    }
}

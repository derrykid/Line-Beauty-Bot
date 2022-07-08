package club.derry;

import club.derry.LineServerInteractor.LineServerInteractor;
import club.derry.config.LineConfig;
import club.derry.ptt.PttBeauty;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@LineMessageHandler
@Slf4j
public class Application {

    private static String configPath = "src/main/resources/application.yml";


    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        String isRequired = event.getMessage().getText();
        if (isRequired.equalsIgnoreCase("/get")) {
            PttBeauty ptt = new PttBeauty();

            List<URI> uris = ptt.getPictures().stream().map(each -> {
                try {
                    return new URI(each);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            String groupId = event.getSource().getSenderId();
            LineServerInteractor.sendPictures(uris, configPath, groupId);
        }

        return null;
    }
}

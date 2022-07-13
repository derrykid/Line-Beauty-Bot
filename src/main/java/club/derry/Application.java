/* (C)2022 */
package club.derry;

import club.derry.config.Config;
import club.derry.service.ptt.PttBeautyService;
import club.derry.subscription.Subscription;
import club.derry.userinfo.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Accept the user event, check for subscription
 */
@LineMessageHandler
@Slf4j
@RestController
public class Application {

    private static final Config config = Config.load();

    @EventMapping
    public Message handle(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        String groupId = event.getSource().getSenderId();
        String text = event.getMessage().getText();

        if (text.toLowerCase().contains("/subscribe")) {
            boolean isSuccess =
                    Subscription.subscribeService(new Group(groupId), new PttBeautyService(config));
            return isSuccess ? new TextMessage("Subscribe!") : null;
        }
        return null;
    }

    @GetMapping
    public String getRequest() throws IOException {
        PttBeautyService pttBeautyService = new PttBeautyService(config);
        Map<String, Link> linkMap = pttBeautyService.getLinkMap();

        return new ObjectMapper().writeValueAsString(linkMap);
    }
}

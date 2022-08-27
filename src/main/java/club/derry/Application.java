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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Accept the user event, check for subscription
 */
@LineMessageHandler
@Slf4j
@RestController
public final class Application {

    private final Config config;
    private final ObjectMapper objectMapper;
    private final PttBeautyService pttBeautyService;

    @Autowired
    public Application(Config config, ObjectMapper objectMapper) {
        this.config = config;
        this.objectMapper = objectMapper;
        this.pttBeautyService = new PttBeautyService(config);
    }

    @EventMapping
    public Message handleLineCommand(MessageEvent<TextMessageContent> event) {
        log.info("event: {}", event);

        String text = event.getMessage().getText();

        if (text.toLowerCase().contains("/subscribe")) {

            String groupId = event.getSource().getSenderId();

            boolean isSuccess =
                    Subscription.subscribeService(new Group(groupId), pttBeautyService);

            return isSuccess ? new TextMessage("Subscribe!") : null;
        }
        return null;
    }

    @GetMapping
    public String getRequest() throws IOException {

        Map<String, Link> linkMap = pttBeautyService.getLinkMap();

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(linkMap);
    }
}

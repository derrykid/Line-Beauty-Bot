/* (C)2022 */
package club.derry.lineserver;

import club.derry.Link;
import club.derry.config.Config;
import club.derry.userinfo.Group;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineServerInteractor {

    /**
     * Return a client that can send message to Line Server
     *
     * @return {@link LineMessagingClient} a client that can send message to Line Server
     */
    private static LineMessagingClient getClient(Config config) {
        String token = config.getLineConfig().getChannelToken();
        return LineMessagingClient.builder(token).build();
    }

    /**
     * Send text message to the target
     *
     * @param config: token and secrets
     * @param group: the target chat
     * @param textMessage: the message to be sent
     */
    public static void sendTextMessage(Config config, Group group, TextMessage textMessage) {
        LineMessagingClient client = getClient(config);
        client.pushMessage(new PushMessage(group.getGroupId(), textMessage));
    }

    /**
     * Send pictures to Chat room
     *
     * @param linkMap: Map view of key being "beauty", "instagram", and "post", respond values are
     *     the links
     * @param config: config token, secrets
     * @param group: the chat room unique id
     */
    public static void sendImageMessage(Map<String, Link> linkMap, Config config, Group group) {
        LineMessagingClient client = getClient(config);
        PushMessage pushMessage = convertToPushMessage(linkMap, group);
        client.pushMessage(pushMessage);
    }

    /**
     * Convert a list of links to a list of ready-made push message
     *
     * @return List of Message
     */
    private static PushMessage convertToPushMessage(Map<String, Link> linkMap, Group group) {

        List<Message> imageMessages =
                linkMap.get("beauty").getLinks().stream()
                        .map(URI::create)
                        .map(uri -> (Message) new ImageMessage(uri, uri))
                        .toList();

        return new PushMessage(group.getGroupId(), imageMessages);
    }
}

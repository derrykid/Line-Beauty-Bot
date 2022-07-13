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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineServerInteractor {

    private LineServerInteractor() {
    }

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
     * @param config:      token and secrets
     * @param group:       the target chat
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
     *                 the links
     * @param config:  config token, secrets
     * @param group:   the chat room unique id
     */
    public static void sendImageMessage(Map<String, Link> linkMap, Config config, Group group) {
        LineMessagingClient client = getClient(config);
        List<PushMessage> pushMessages = convertToPushMessage(linkMap, group);

        pushMessages.forEach(client::pushMessage);
    }

    /**
     * Convert a list of links to a list of ready-made push message
     *
     * @return List of Message
     */
    private static List<PushMessage> convertToPushMessage(Map<String, Link> linkMap, Group group) {

        List<Message> imageMessages =
                linkMap.get("beauty").getLinks().stream()
                        .map(URI::create)
                        .map(uri -> (Message) new ImageMessage(uri, uri))
                        .toList();

        // push message can only have 5 messages maximun
        List<List<Message>> dividedBy5 = partition(imageMessages, 5);

        return dividedBy5.stream()
                .map(messageList -> new PushMessage(group.getGroupId(), messageList))
                .toList();
    }

    /**
     * User can only push a message of size 5 at a time, this method is help to aid to slice the
     * list of message to the size we desire
     *
     * @param collection,    list of messages
     * @param partitionSize, the size we want
     * @return generic List of lists
     */
    private static <T> List<List<T>> partition(List<T> collection, int partitionSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + partitionSize)
                .mapToObj(
                        i -> collection.subList(i, Math.min(i + partitionSize, collection.size())))
                .collect(Collectors.toList());
    }
}

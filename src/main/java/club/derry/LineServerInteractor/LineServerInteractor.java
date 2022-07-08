package club.derry.LineServerInteractor;

import club.derry.config.Config;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.ImageMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LineServerInteractor {

    /**
     * Send pictures to Chat
     * @param uriList: list of img links
     * @param path: path of config file
     * @param groupId: the chat room unique id
     */
    public static void sendPictures(List<URI> uriList, String path, String groupId) {

        LineMessagingClient client = getClient(path);

        List<PushMessage> pushMessageList = getPushMessageList(uriList, groupId);

        pushMessageList.forEach(client::pushMessage);

    }

    /**
     * Convert a list of links to a list of ready made push message
     *
     * @param uriList   list of image links
     * @param targetId: push message target, can be a group, or a person's userId
     * @return List of Message
     */
    private static List<PushMessage> getPushMessageList(List<URI> uriList, String targetId) {
        return uriList.stream()
                .map(uri -> new PushMessage(targetId, new ImageMessage(uri, uri)))
                .collect(Collectors.toList());
    }

    /**
     * Return a client that can send message to Line Server
     * @param path: path of Config file
     * @return {@link LineMessagingClient} a client that can send message to Line Server
     */
    private static LineMessagingClient getClient(String path) {
        String token = null;
        try {
            token = Config.load(path).getLineConfig().getChannelToken();
        } catch (IOException e) {
            log.error("get channel token fails: {}", e);
        }
        return LineMessagingClient.builder(token).build();
    }
}

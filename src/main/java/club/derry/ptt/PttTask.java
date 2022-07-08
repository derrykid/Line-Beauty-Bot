package club.derry.ptt;

import club.derry.LineServerInteractor.LineServerInteractor;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A task that
 * <ol>
 *   <li>go to ptt beauty</li>
 *   <li>find the top post</li>
 *   <li>get the image links</li>
 *   <li>send to chat room</li>
 * </ol>
 */
@AllArgsConstructor
public class PttTask implements Runnable {

    private String groupId;
    private String configPath;

    @Override
    public void run() {
        PttBeauty ptt = new PttBeauty();

        List<URI> uris = ptt.getPictures().stream().map(each -> {
            try {
                return new URI(each);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        LineServerInteractor.sendPictures(uris, configPath, groupId);
    }
}

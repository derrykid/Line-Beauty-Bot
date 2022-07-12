/* (C)2022 */
package club.derry.service.ptt.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.Getter;

@JsonRootName("beauty-forum-url")
@Getter
public class BeautyForumConfig {

    private final URI uri;
    private final String previousPageSelector;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private BeautyForumConfig(
            @JsonProperty("forum-url") String uri,
            @JsonProperty("previous-page-selector") String previousPageSelector) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.previousPageSelector = previousPageSelector;
    }
}

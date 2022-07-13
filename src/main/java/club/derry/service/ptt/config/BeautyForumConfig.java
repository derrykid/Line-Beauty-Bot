/* (C)2022 */
package club.derry.service.ptt.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import java.net.URI;

@JsonRootName("beauty-forum-url")
@Getter
public class BeautyForumConfig {

    private final URI uri;
    private final String previousPageSelector;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private BeautyForumConfig(
            @JsonProperty("forum-url") String uri,
            @JsonProperty("previous-page-selector") String previousPageSelector) {
        this.uri = URI.create(uri);
        this.previousPageSelector = previousPageSelector;
    }
}

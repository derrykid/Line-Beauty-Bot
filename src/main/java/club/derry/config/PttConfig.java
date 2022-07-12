/* (C)2022 */
package club.derry.config;

import club.derry.service.ptt.config.BeautyForumConfig;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@JsonRootName("ptt")
@Getter
public class PttConfig {

    private final String baseUrl;
    private final BeautyForumConfig beautyForumConfig;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private PttConfig(
            @JsonProperty("base-url") String baseUrl,
            @JsonProperty("beauty-forum") BeautyForumConfig beautyForumConfig) {
        this.baseUrl = baseUrl;
        this.beautyForumConfig = beautyForumConfig;
    }
}

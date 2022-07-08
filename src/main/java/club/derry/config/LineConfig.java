package club.derry.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@JsonRootName("line.bot")
@Getter
public class LineConfig {

    private String channelToken;
    private String channelSecret;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private LineConfig(@JsonProperty("channel-token") String token,
                       @JsonProperty("channel-secret") String secret) {
        this.channelToken = token;
        this.channelSecret = secret;
    }

}

/* (C)2022 */
package club.derry.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Config {

    private static final String configPath = "src/main/resources/application.yml";

    private final LineConfig lineConfig;
    private final PttConfig pttConfig;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(
            @JsonProperty("line.bot") LineConfig lineConfig,
            @JsonProperty("ptt") PttConfig pttConfig) {
        this.lineConfig = lineConfig;
        this.pttConfig = pttConfig;
    }

    public static Config load() {
        try {
            return new ObjectMapper(new YAMLFactory())
                    .readValue(new File(configPath), Config.class);
        } catch (IOException e) {
            log.error("Load config throws exception: ", e);
            throw new RuntimeException(e);
        }
    }
}

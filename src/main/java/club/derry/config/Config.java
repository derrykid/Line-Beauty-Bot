/* (C)2022 */
package club.derry.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
@Getter
public class Config {

    private final LineConfig lineConfig;
    private final PttConfig pttConfig;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(
            @JsonProperty("line.bot") LineConfig lineConfig,
            @JsonProperty("ptt") PttConfig pttConfig) {
        this.lineConfig = lineConfig;
        this.pttConfig = pttConfig;
    }

    public static Config load(String path) throws IOException {
        return new ObjectMapper(new YAMLFactory())
                .readValue(new File(path), Config.class);
    }
}

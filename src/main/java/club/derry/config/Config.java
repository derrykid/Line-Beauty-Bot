package club.derry.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {

    private LineConfig lineConfig;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(@JsonProperty("line.bot") LineConfig lineConfig) {
        this.lineConfig = lineConfig;
    }

    public static Config load(String path) throws IOException {
        return new ObjectMapper(new YAMLFactory()).readValue(new File(path), Config.class);
    }

}

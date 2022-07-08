package club.derry.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Config {

    private LineConfig lineConfig;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(@JsonProperty("line.bot") LineConfig lineConfig) {
        this.lineConfig = lineConfig;
    }

}

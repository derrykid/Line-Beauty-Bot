/*
 * Copyright (c) 2022
 * derry.club
 */

package club.derry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ProjectConfig {

    @Bean
    public Config getConfig() throws IOException {
        String path = "src/main/resources/application.yml";
        return Config.load(path);
    }

    @Bean
    public PttConfig getPttConfig(Config config) {
        return config.getPttConfig();
    }

    @Bean
    public LineConfig getLineConfig(Config config) {
        return config.getLineConfig();
    }
}

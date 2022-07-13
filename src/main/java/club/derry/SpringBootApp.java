/* (C)2022 */
package club.derry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

        String path = "src/main/resources/application.yml";
        try {
            new Application(path);
        } catch (IOException e) {
            log.error("Unable to load the configuration file from path, {}", path, e );
        }
    }
}

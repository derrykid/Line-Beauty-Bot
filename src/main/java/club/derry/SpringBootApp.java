package club.derry;

import club.derry.basic.PingTask;
import club.derry.routine.Routine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

        String groupId = "a";
        String configPath = "src/main/resources/application.yml";

        Routine.registerRoutine(groupId, new PingTask(groupId, configPath),
                1, TimeUnit.SECONDS);
    }
}

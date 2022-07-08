package club.derry;

import club.derry.basic.AlarmTask;
import club.derry.basic.PingTask;
import club.derry.routine.Routine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
        Routine.registerRoutine(new AlarmTask(), 20, TimeUnit.MINUTES);
    }
}

/* (C)2022 */
package club.derry.subscription;

import club.derry.service.ptt.PttBeautyService;
import club.derry.service.ptt.PttTask;
import club.derry.service.ptt.Service;
import club.derry.userinfo.Group;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User subscribe to the routine service <br>
 * Responsibility: Only responsible for subscribe the service
 */
@Getter
public class Subscription {

    private static Map<Group, List<Service>> routineSubscription = new HashMap<>();

    private Subscription() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean subscribeService(Group group, Service service) {

        if (group.getSubscriptions().contains(service)) {
            return false;
        }

        Runnable task = identifyService(service);

        Routine.registerRoutine(task, 24, TimeUnit.HOURS);
        return group.subscribe(service);
    }

    private static Runnable identifyService(Service service) {

        var out = service.getClass();



        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi");
            }
        };
    }
}

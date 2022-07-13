/* (C)2022 */
package club.derry.subscription;

import club.derry.service.ptt.PttTask;
import club.derry.service.ptt.Service;
import club.derry.userinfo.Group;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

/**
 * User subscribe to the routine service <br>
 * Responsibility: Only responsible for subscribe the service
 */
@Getter
public class Subscription {

    private static Map<Group, List<Service>> routineSubscription = new HashMap<>();

    private Subscription() {}

    public static boolean subscribeService(Group group, Service service) {
        if (group.getSubscriptions().contains(service)) {
            return false;
        }
        Routine.registerRoutine(new PttTask(group, service), 24, TimeUnit.HOURS);
        return group.subscribe(service);
    }
}

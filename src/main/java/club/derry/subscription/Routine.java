/* (C)2022 */
package club.derry.subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Routine {

    private Routine() {}

    private static final ScheduledExecutorService routineService =
            Executors.newSingleThreadScheduledExecutor();

    /**
     * Register a task routine that will execute at every given period
     *
     * @param runnable: the routine to do
     * @param period: number of length
     * @param timeUnit: time unit, e.g. hours, mins
     */
    static void registerRoutine(Runnable runnable, long period, TimeUnit timeUnit) {
        routineService.scheduleAtFixedRate(runnable, 0, period, timeUnit);
    }

}

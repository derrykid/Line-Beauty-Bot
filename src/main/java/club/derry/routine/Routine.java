package club.derry.routine;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Routine {

    private static ScheduledExecutorService routineService
            = Executors.newSingleThreadScheduledExecutor();

    /**
     * Register a runnable routine that will execute at every given period
     *
     * @param runnable: the routine to do
     * @param period:   number of length
     * @param timeUnit: time unit, e.g. hours, mins
     */
    public static void registerRoutine(Runnable runnable, int period, TimeUnit timeUnit) {
        routineService.scheduleAtFixedRate(runnable, 0, period, timeUnit);
    }

}

package club.derry.routine;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Routine {

    private static ScheduledExecutorService routineService
            = Executors.newSingleThreadScheduledExecutor();

    private static HashSet<String> registerRoutineSet = new HashSet<>();

    /**
     * Register a runnable routine that will execute at every given period
     *
     * @param runnable: the routine to do
     * @param period:   number of length
     * @param timeUnit: time unit, e.g. hours, mins
     */
    public static void registerRoutine(String groupId, Runnable runnable, int period, TimeUnit timeUnit) {
        if (isRegister(groupId)) {
            routineService.scheduleAtFixedRate(runnable, 0, period, timeUnit);
        }
    }

    /**
     * Check if the chat room has already registered the routine yet
     * @param groupId: unique id
     * @return boolean
     */
    private static boolean isRegister(String groupId) {
        return !registerRoutineSet.contains(groupId);
    }

}

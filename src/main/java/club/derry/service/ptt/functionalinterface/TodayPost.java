/* (C)2022 */
package club.derry.service.ptt.functionalinterface;

import java.time.LocalDate;
import java.util.function.Predicate;

public class TodayPost implements Predicate<String[]> {
    @Override
    public boolean test(String[] strings) {
        int yesterdayDay = LocalDate.now().getDayOfMonth();
        int postDay = Integer.parseInt(strings[1]);
        return postDay == yesterdayDay;
    }
}

/* (C)2022 */
package club.derry.service.ptt.functionalinterface;

import java.util.function.Predicate;

public class TodayOrYesterdayPost {
    public static Predicate<String[]> getPredicate() {
        return new TodayPost().or(new YesterdayPost());
    }
}

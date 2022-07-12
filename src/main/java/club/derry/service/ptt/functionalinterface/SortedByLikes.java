/* (C)2022 */
package club.derry.service.ptt.functionalinterface;

import java.util.Comparator;

public class SortedByLikes implements Comparator<String> {
    @Override
    public int compare(String e1, String e2) {
        String numOfLikes1 = e1.substring(0, 3);
        String numOfLikes2 = e2.substring(0, 3);
        return -numOfLikes1.compareTo(numOfLikes2);
    }
}

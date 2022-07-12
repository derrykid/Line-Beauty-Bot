/* (C)2022 */
package club.derry.service.ptt.functionalinterface;

import java.util.function.Predicate;
import org.jsoup.nodes.Element;

public class PostIsHit implements Predicate<Element> {
    @Override
    public boolean test(Element element) {
        return element.html().contains("hl f1");
    }
}

package club.derry.ptt.predicate;

import org.jsoup.nodes.Element;

import java.util.function.Predicate;

public class NotBulletin implements Predicate<Element> {
    @Override
    public boolean test(Element element) {
        return !element.html().contains("公告");
    }
}

package club.derry.ptt.predicate;

import org.jsoup.nodes.Element;

import java.util.function.Predicate;

public class OnlyBeauty implements Predicate<Element> {

    @Override
    public boolean test(Element element) {
        return element.html().contains("正妹");
    }
}

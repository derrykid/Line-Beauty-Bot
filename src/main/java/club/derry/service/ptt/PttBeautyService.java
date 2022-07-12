/* (C)2022 */
package club.derry.service.ptt;

import club.derry.Link;
import club.derry.config.Config;
import club.derry.service.ptt.functionalinterface.*;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** This class is responsible for getting a List of URI from yesterday posts. */
@Slf4j
public class PttBeautyService implements Service {

    @Getter private final Config config;
    private final URI beautyForumUri;
    private final String previousPageSelector;

    public PttBeautyService(Config config) {
        this.config = config;
        this.beautyForumUri = config.getPttConfig().getBeautyForumConfig().getUri();
        this.previousPageSelector =
                config.getPttConfig().getBeautyForumConfig().getPreviousPageSelector();
    }

    /**
     * Get a map that contains image links, the post address, and instagram link (if exist)
     *
     * @return Map of key being the "beauty", "instagram", and "post"
     * @throws IOException
     */
    public Map<String, Link> getLinkMap() throws IOException {
        Document indexDoc = Jsoup.connect(beautyForumUri.toString()).cookie("over18", "1").get();

        Set<Element> scrapElements = getElementsOfBeauty(indexDoc, new HashSet<>());

        SortedSet<String> linksSortByLikes = getYesterdayTopPost(scrapElements);

        String targetPostLink = linksSortByLikes.first();

        List<String> linksInPost =
                fetchLinks(Jsoup.connect(targetPostLink).cookie("over18", "1").get());

        return getLinkMap(linksInPost, targetPostLink);
    }

    /**
     * @param linksInPost: all links in the post, includes image links, post links, comment section
     *     links
     * @param thePostLink: post links
     * @return Map view of beauty image links, instagram links (if exist), post url
     */
    private Map<String, Link> getLinkMap(List<String> linksInPost, String thePostLink) {
        Map<String, Link> linkMap = new HashMap<>();
        List<String> beautyImages = linksBeforeCommentSection(linksInPost, thePostLink);
        linkMap.put("beauty", new Link(beautyImages));

        List<String> igLinks = getInstagramLink(linksInPost);
        linkMap.put("instagram", new Link(igLinks));

        linkMap.put("post", new Link(List.of(thePostLink)));

        return linkMap;
    }

    /**
     * Return the links, if the Instagram address exists
     *
     * @return Instagram links
     */
    private List<String> getInstagramLink(List<String> linksInPost) {
        Optional<String> igLink =
                linksInPost.stream().filter(link -> link.contains("instagram.com")).findFirst();

        return igLink.map(List::of).orElseGet(ArrayList::new);
    }

    /**
     * Recursive methods that continueously adding up until the posts of 2 days ago showing up
     *
     * <p>We only look for the posts that are yesterday, other than that is eliminated from the
     * return set For example, today is 7/11, then this method will will go to index page and
     * eliminate the element of 7/11, add the posts of 7/10 to the collection. And it will go to
     * previous page, collect posts that were posted on 7/10. It'll stop when <b>the post date is
     * 7/9.</b>
     *
     * @param document, index document
     * @param elementSet, the set that save all the element (include title, date, links, etc)
     * @return the set of desired elements (include title, date, links, etc)
     * @throws IOException
     */
    private Set<Element> getElementsOfBeauty(Document document, Set<Element> elementSet)
            throws IOException {
        Elements divElement = document.getElementsByClass("r-ent");

        List<Element> noBulletinOnlyYesterday = onlyYesterdayAllowElementList(divElement);

        List<Element> onlyBeauty = onlyBeauty(noBulletinOnlyYesterday);

        elementSet.addAll(onlyBeauty);

        boolean shallReturn = shallReturn(divElement);

        if (shallReturn) {
            return elementSet;
        }

        Document previousPageDoc =
                Jsoup.connect(getPreviousPageLink(document)).cookie("over18", "1").get();

        return getElementsOfBeauty(previousPageDoc, elementSet);
    }

    /**
     * Get a list of links of only beauty. Sorted by likes
     *
     * @param elementSet: element that contains title, day, etc
     * @return List: a list of links, sorted by likes, the hottest is the first element in list
     */
    private SortedSet<String> getYesterdayTopPost(Set<Element> elementSet) {
        if (hitExist(elementSet)) {
            return elementSet.stream()
                    .filter(element -> element.select("span").text().contains("爆"))
                    .map(element -> element.select("a").attr("abs:href"))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
        return elementSet.stream()
                .map(e -> e.select("a").attr("abs:href"))
                .collect(Collectors.toCollection(() -> new TreeSet<>(new SortedByLikes())));
    }

    /**
     * @param divElements
     * @return true when the post date wasn't yesterday <br>
     *     false means we're still on the page where posts are today's
     */
    private boolean shallReturn(Elements divElements) {
        return divElements.stream()
                .filter(new NotBulletin())
                .map(this::getDateStringArray)
                .filter(new TodayPost().negate())
                .anyMatch(new YesterdayPost().negate());
    }

    /**
     * Get the div that isn't bulletin. It also has to be yesterday post
     *
     * @param divElement: the ArrayList of element
     * @return a List of Element, this element contains title, url, date div as well
     */
    private List<Element> onlyYesterdayAllowElementList(Elements divElement) {
        return divElement.stream()
                .filter(new NotBulletin())
                .filter(
                        each -> {
                            String[] strings = getDateStringArray(each);
                            return new YesterdayPost().test(strings);
                        })
                .collect(Collectors.toList());
    }

    /**
     * Eliminate unrelated post, only beauty remain
     *
     * @param elementList, consists of title, date, etc
     * @return a list of element
     */
    private List<Element> onlyBeauty(List<Element> elementList) {
        return elementList.stream().filter(new OnlyBeauty()).collect(Collectors.toList());
    }

    /**
     * Get the document's all date (format: 7/22) as String[]
     *
     * @param element of div, includes title, date, etc
     * @return a list of String[], String[0] being the month, [1] being the day
     */
    private String[] getDateStringArray(Element element) {
        return element.select("div.date").text().split("/");
    }

    /**
     * Fetch the image links in the post
     *
     * @param document, the post we want
     * @return a list of all url in the post
     */
    private List<String> fetchLinks(Document document) {
        return document.getElementById("main-container").select("a").stream()
                .map(each -> each.attr("abs:href"))
                //                .filter(each -> each.contains("imgur"))
                .collect(Collectors.toList());
    }

    /**
     * Use Selector to get the previous page link
     *
     * @param document: jsoup document, which is instantialized from connection
     * @return String: the link
     */
    private String getPreviousPageLink(Document document) {
        return document.select(previousPageSelector).select("a").attr("abs:href");
    }

    /**
     * Check if there is a post that is a hit
     *
     * @param element: All the post on that page
     * @return boolean: true if there is
     */
    private boolean hitExist(Set<Element> element) {
        return element.stream().anyMatch(new PostIsHit());
    }

    /**
     * Get the links before the comment section
     *
     * <p>Memes usually located at comment section Remove pictures in comment section can prevent
     * bot from scraping these memes
     *
     * @return a list of url that before comment section, might contain IG account address
     */
    private List<String> linksBeforeCommentSection(List<String> links, String postLink) {
        return links.subList(0, links.indexOf(postLink));
    }
}

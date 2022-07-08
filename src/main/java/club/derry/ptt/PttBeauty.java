package club.derry.ptt;

import club.derry.ptt.predicate.NotBulletin;
import club.derry.ptt.predicate.OnlyBeauty;
import club.derry.ptt.predicate.PostIsHit;
import club.derry.ptt.predicate.PostsHave10PlusLikes;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class PttBeauty {

    private static String url = "https://www.ptt.cc/bbs/Beauty/index.html";
    private static String previousPageSelector = "#action-bar-container > div > div.btn-group.btn-group-paging > a:nth-child(2)";

    public List<String> getPictures() {
        Document pttDoc = null;
        try {
            pttDoc = Jsoup.connect(url).cookie("over18", "1").get();
        } catch (IOException e) {
            log.info("Connect to ptt fails, {}", e);
        }

        String homePagePreviousPageLink = getPreviousPageLink(pttDoc);

        try {
            Document previousPageDoc = getPreviousPageDocument(homePagePreviousPageLink);
            List<String> imgLinks = fetchTheLinksOfPictureInHotPosts(previousPageDoc);
            return imgLinks;
        } catch (IOException e) {
            log.info("Connect to previous page fails, {}", e);
        }
        return new ArrayList<>();
    }

    private List<String> fetchTheLinksOfPictureInHotPosts(Document document) throws IOException {

        List<String> topBeautyLinks = getBeautyLinks(document);

        String hottestPageUrl = topBeautyLinks.get(0);

        Document thePostDocument = Jsoup.connect(hottestPageUrl).cookie("over18", "1").get();

        return thePostDocument.getElementById("main-container").select("a").stream()
                .map(each -> each.attr("abs:href"))
                .filter(each -> !each.contains("imgur"))
                .collect(Collectors.toList());
    }

    /**
     * Get the previous page document.
     * <p>
     * At times, the index.html (the first page) we visit hasn't received enough views from
     * visitors. Therefore, it's the best to go fetch the previous page posts.
     * </p>
     *
     * @param url: url of previous page
     * @return document of previous page
     * @throws IOException fail of connection
     */
    private Document getPreviousPageDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).cookie("over18", "1").get();
        return document;
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
     * Get a list of links of only beauty. Sorted by likes
     *
     * @param document: Jsoup connection document
     * @return List: a list of links, sorted by likes, the hottest is the first element in list
     */
    private List<String> getBeautyLinks(Document document) {

        final String divSelector = "r-ent";

        Elements divElements = document.getElementsByClass(divSelector);

        if (hitExist(divElements)) {
             List<String> links = divElements.stream()
                    .filter(element -> element.select("span").text().contains("爆"))
                    .map(element -> element.select("a").attr("abs:href"))
                     .collect(Collectors.toList());

             return removeMeme(links);
        }

        Comparator<Element> sortedByLikes = (e1, e2) -> {
            String numOfLikes1 = e1.select("span").text().substring(0, 3);
            String numOfLikes2 = e2.select("span").text().substring(0, 3);
            return -numOfLikes1.compareTo(numOfLikes2);
        };

        List<String> value = divElements.stream()
                .filter(new OnlyBeauty().and(new PostsHave10PlusLikes()).and(new NotBulletin()))
                .sorted(sortedByLikes)
                .map(each -> each.select("a").attr("abs:href"))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return removeMeme(value);
    }

    /**
     * Check if there is a post that is a hit
     * @param divElements: every posts element
     * @return boolean: true if there is
     */
    private boolean hitExist(Elements divElements) {
        return divElements.stream().anyMatch(new PostIsHit().and(new OnlyBeauty()));
    }

    /**
     * remove the memes from the list
     * <p>
     *      Memes usually located at comment section
     *      Remove pictures in comment section might prevent bot from scraping these memes
     * </p>
     * @param linkList: links of images
     * @return List of url before comment section
     */
    private List<String> removeMeme(List<String> linkList) {

       Optional<String> positionOfPostLink = linkList.stream().filter(e -> e.contains("www.ptt.cc")).findAny();

       if (positionOfPostLink.isPresent()) {
           String url = positionOfPostLink.get();
           int index = linkList.indexOf(url);
           linkList.subList(0, index);
       }

       return linkList;
    }
}

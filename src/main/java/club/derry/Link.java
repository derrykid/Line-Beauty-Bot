/* (C)2022 */
package club.derry;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class Link {

    private final List<String> links;

    public Link(List<String> list) {
        this.links = list;
    }
}

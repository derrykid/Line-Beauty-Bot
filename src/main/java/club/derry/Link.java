/* (C)2022 */
package club.derry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.Getter;

@Getter
public class Link {

    private final List<String> links;

    public Link(List<String> list) {
        this.links = list;
    }

    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();

        String result = "";
        for (int i = 0; i < links.size(); i++) {
            String output = null;
            try {
                output = om.writeValueAsString(links.get(i));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            result = result + output;
        }
        return result;
    }
}

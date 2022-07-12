/* (C)2022 */
package club.derry.userinfo;

import club.derry.service.ptt.Service;
import java.util.HashSet;
import java.util.Set;
import lombok.Value;

/**
 * For now, only group chat can subscribe to the service <br>
 * The goal of this bot is to send pictures in group chat instead of being in personal use
 */
@Value
public class Group {
    String groupId;
    Set<Service> subscriptions;

    public Group(String groupId) {
        this.groupId = groupId;
        this.subscriptions = new HashSet<>();
    }

    public boolean subscribe(Service service) {
        return subscriptions.add(service);
    }
}

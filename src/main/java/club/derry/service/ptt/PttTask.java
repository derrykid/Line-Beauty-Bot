/* (C)2022 */
package club.derry.service.ptt;

import club.derry.Link;
import club.derry.config.Config;
import club.derry.lineserver.LineServerInteractor;
import club.derry.userinfo.Group;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * A task that
 *
 * <ol>
 *   <li>go to ptt beauty
 *   <li>find the top post of yesterday
 *   <li>get the image links
 *   <li>send to chat room
 * </ol>
 */
@Slf4j
public class PttTask implements Runnable {

    private final Service service;
    private final Config config;
    private final Group group;

    public PttTask(Group group, Service service) {
        this.group = group;
        this.service = service;
        this.config = service.getConfig();
    }

    @Override
    public void run() {
        PttBeautyService pttBeautyService = (PttBeautyService) service;

        Map<String, Link> linkMap = null;
        try {
            linkMap = pttBeautyService.getLinkMap();
        } catch (IOException e) {
            log.warn("Error occurs while running ptt beauty task: ", e);
        }

        LineServerInteractor.sendImageMessage(linkMap, config, group);
    }
}

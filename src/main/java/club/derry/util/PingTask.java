/* (C)2022 */
package club.derry.util;

import club.derry.config.Config;
import club.derry.lineserver.LineServerInteractor;
import club.derry.userinfo.Group;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;

/** Ping command that used to test if the push message is successfully sent */
@AllArgsConstructor
public class PingTask implements Runnable {

    private final Config config;
    private final Group group;

    @Override
    public void run() {
        LineServerInteractor.sendTextMessage(config, group, new TextMessage("Pong"));
    }
}

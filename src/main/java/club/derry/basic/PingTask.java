package club.derry.basic;

import club.derry.LineServerInteractor.LineServerInteractor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PingTask implements Runnable {

    private String groupId;
    private String configPath;

    @Override
    public void run() {
        LineServerInteractor.sendPing(configPath, groupId);
    }
}

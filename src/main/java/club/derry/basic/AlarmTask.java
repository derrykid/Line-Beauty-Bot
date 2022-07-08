package club.derry.basic;

/**
 * This task is to send a regular message to system to bypass the heroku timeout when app is idle
 */
public class AlarmTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Wake up!");
    }
}

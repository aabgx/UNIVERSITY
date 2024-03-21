package runner;

import utils.Constants;

import java.time.LocalDateTime;

public class DelayTaskRunner extends AbstractTaskRunner{

    public DelayTaskRunner(TaskRunner runner) {super(runner);}
    @Override
    public void executeOneTask(){
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        runner.executeOneTask();
    }

}

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by fengxuming on 2017/6/5.
 */
public class MyJob implements org.quartz.Job {
    public MyJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
    }
}

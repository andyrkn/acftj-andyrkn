package quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Singleton;

@Singleton
public class QuartzSchedulerFactory {

    private Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

    public QuartzSchedulerFactory() throws SchedulerException {
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}

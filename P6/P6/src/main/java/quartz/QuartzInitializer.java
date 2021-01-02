package quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Singleton
public class QuartzInitializer implements ServletContextListener {

    @Inject
    QuartzJobFactory factory;

    @Inject
    QuartzSchedulerFactory schedulerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LoggerFactory.getLogger(getClass()).info("Quartz CDI Factory set.");
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(factory);

            scheduler.addJob(QuartzJobFactory.itemQueueingJob(), true, true);
            scheduler.addJob(QuartzJobFactory.emagScrapJob(), true, true);
            scheduler.scheduleJob(QuartzJobFactory.itemQueueingTrigger());

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

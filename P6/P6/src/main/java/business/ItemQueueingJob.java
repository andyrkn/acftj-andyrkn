package business;

import domain.MonitorableItem;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MonitorableItemsRepo;
import quartz.QuartzSchedulerFactory;
import quartz.QuartzTriggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ItemQueueingJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    MonitorableItemsRepo monitorableItemsRepo;

    @Inject
    QuartzSchedulerFactory schedulerFactory;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Scheduler scheduler = schedulerFactory.getScheduler();

        List<MonitorableItem> items = monitorableItemsRepo.GetAllDocuments();
        items.forEach(item -> {
            item.urls.forEach(url -> {
                try {
                    scheduler.scheduleJob(QuartzTriggerFactory.itemScrapTrigger(url, item.name));
                } catch (SchedulerException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            });
        });
    }
}

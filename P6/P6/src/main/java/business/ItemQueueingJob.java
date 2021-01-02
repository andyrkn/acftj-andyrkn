package business;

import domain.MonitorableItem;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MonitorableItemsRepo;
import quartz.QuartzJobFactory;
import quartz.QuartzSchedulerFactory;

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
            item.Urls.forEach(url -> {
                Trigger t = TriggerBuilder.newTrigger()
                        .startNow()
                        .usingJobData("item", item.Name)
                        .usingJobData("url", url)
                        .forJob("scrap-price", "scrap-group")
                        .build();
                try {
                    scheduler.scheduleJob(t);
                } catch (SchedulerException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            });
        });
    }
}

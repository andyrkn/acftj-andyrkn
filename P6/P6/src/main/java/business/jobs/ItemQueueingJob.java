package business.jobs;

import domain.MonitorableItem;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.MonitorableItemsRepo;
import quartz.QuartzSchedulerFactory;
import quartz.QuartzTriggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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

        int extraSeconds = 5;

        for(MonitorableItem item : items) {
            for (String url : item.urls) {
                extraSeconds+=5;
                try {
                    Date startAt = Date.from(LocalDateTime.now().plusSeconds(extraSeconds).toInstant(ZoneOffset.ofHours(2)));
                    scheduler.scheduleJob(QuartzTriggerFactory.itemScrapTrigger(url, item.name, startAt));
                } catch (SchedulerException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}

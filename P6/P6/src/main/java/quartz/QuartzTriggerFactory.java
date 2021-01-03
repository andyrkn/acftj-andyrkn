package quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public final class QuartzTriggerFactory {

    public static Trigger itemQueueingTrigger() {
        return TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(360)
                        .repeatForever())
                .forJob(QuartzJobConstants.QUEUE_SEARCH_KEY, QuartzJobConstants.QUEUE_SEARCH_GROUP)
                .build();
    }

    public static Trigger itemScrapTrigger(String url, String itemName){
        return TriggerBuilder.newTrigger()
                .startNow()
                .usingJobData("item", itemName)
                .usingJobData("url", url)
                .forJob(QuartzJobConstants.ITEM_SCRAP_KEY, QuartzJobConstants.ITEM_SCRAP_GROUP)
                .build();
    }
}

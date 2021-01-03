package quartz;

import business.EmagPriceScrapJob;
import business.ItemQueueingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public final class QuartzJobDetailFactory {

    public static JobDetail emagScrapJob() {
        return JobBuilder.newJob(EmagPriceScrapJob.class)
                .withIdentity(QuartzJobConstants.ITEM_SCRAP_KEY, QuartzJobConstants.ITEM_SCRAP_GROUP)
                .usingJobData("item", "-")
                .usingJobData("url", "-")
                .storeDurably()
                .build();
    }

    public static JobDetail itemQueueingJob() {
        return JobBuilder.newJob(ItemQueueingJob.class)
                .withIdentity(QuartzJobConstants.QUEUE_SEARCH_KEY, QuartzJobConstants.QUEUE_SEARCH_GROUP)
                .build();
    }
}
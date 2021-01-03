package quartz;

import business.ItemQueueingJob;
import business.EmagPriceScrapJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuartzJobFactory implements JobFactory {

    @Inject
    BeanManager beanManager;

    @Override
    public Job newJob(final TriggerFiredBundle bundle, final Scheduler scheduler) throws SchedulerException {
        final Class<Job> jobClazz = (Class<Job>) bundle.getJobDetail().getJobClass();
        final Bean<Job> bean = (Bean<Job>) beanManager.getBeans(jobClazz).stream().findAny().orElseThrow(IllegalStateException::new);
        final CreationalContext<Job> ctx = beanManager.createCreationalContext(bean);

        return (Job) beanManager.getReference(bean, jobClazz, ctx);
    }

    public static JobDetail emagScrapJob() {
        return JobBuilder.newJob(EmagPriceScrapJob.class)
                .withIdentity("scrap-price", "scrap-group")
                .usingJobData("item", "-")
                .usingJobData("url", "-")
                .storeDurably()
                .build();
    }

    public static JobDetail itemQueueingJob() {
        return JobBuilder.newJob(ItemQueueingJob.class)
                .withIdentity("item-queue","search-group")
                .build();
    }


    public static Trigger itemQueueingTrigger() {
        return TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .forJob("item-queue","search-group")
                .build();
    }
}

/*
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
 */

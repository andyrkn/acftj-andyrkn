package quartz;

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

}

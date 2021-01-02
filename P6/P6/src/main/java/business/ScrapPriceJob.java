package business;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScrapPriceJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String item = jobExecutionContext.getMergedJobDataMap().getString("item");
        String url = jobExecutionContext.getMergedJobDataMap().getString("url");

        logger.info("Scraping " + item + "on " + url);
    }
}

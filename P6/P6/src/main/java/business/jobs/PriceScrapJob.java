package business.jobs;

import business.ScrapItemPriceService;
import domain.ItemState;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ItemStateRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class PriceScrapJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private ScrapItemPriceService scapService;

    @Inject
    private ItemStateRepo repo;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String item = jobExecutionContext.getMergedJobDataMap().getString("item");
        String url = jobExecutionContext.getMergedJobDataMap().getString("url");
        logger.info("Scraping " + item + " on " + url);

        String price = scapService.scrapEmag(url);
        if(price != null) {
            repo.InsertSpecificItem(new ItemState(url, price, LocalDateTime.now()), item);
        }
    }
}

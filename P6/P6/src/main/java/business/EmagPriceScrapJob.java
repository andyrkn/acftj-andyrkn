package business;

import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import domain.ItemState;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.html.HTMLElement;
import persistence.ItemStateRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class EmagPriceScrapJob implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private WebClientService webClientService;

    @Inject
    ItemStateRepo repo;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String item = jobExecutionContext.getMergedJobDataMap().getString("item");
        String url = jobExecutionContext.getMergedJobDataMap().getString("url");
        logger.info("Scraping " + item + " on " + url);

        try {
            HtmlPage page = webClientService.getClient().getPage(url);
            String price = null;
            int tries = 15;
            while(tries > 0 ) {
                tries--;
                synchronized (page) {
                    page.wait(1000);
                }
                HtmlElement el = get(page,"//div[contains(@class, 'product-page-pricing')]//p[contains(@class, 'product-new-price')]");
                if(el != null){
                    price = el.getFirstChild().asText();
                    break;
                }
            }
            if(price != null) {
                logger.info(price);
                repo.InsertSpecificItem(new ItemState(url, price, LocalDateTime.now()),item);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private HtmlElement get(HtmlPage page, String xpath){
        return ((HtmlElement) page.getFirstByXPath(xpath));
    }
}

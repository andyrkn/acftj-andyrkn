package business;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ScrapItemPriceService {

    @Inject
    private WebClientService webClientService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String scrapEmag(String url) {
        String xPath = "//div[contains(@class, 'product-page-pricing')]//p[contains(@class, 'product-new-price')]";
        return startPolling(url, xPath);
    }

    private String startPolling(String url , String xPath){
        try {
            String price = pollAtXpath(url, xPath);
            return price;
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    private String pollAtXpath(String url, String xPath) throws IOException, InterruptedException {
        HtmlPage page = webClientService.getClient().getPage(url);
        String price = null;

        int tries = 45;
        while (tries > 0) {
            tries--;
            synchronized (page) {
                page.wait(350);
            }
            HtmlElement el = getByXpath(page, xPath);
            if (el != null) {
                price = el.getFirstChild().asText();
                break;
            }
        }

        page.getWebClient().close();
        page.cleanUp();

        return price;
    }

    private HtmlElement getByXpath(HtmlPage page, String xpath){
        return ((HtmlElement) page.getFirstByXPath(xpath));
    }
}

package business;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class ScrapItemPriceService {

    @Inject
    private WebClientService webClientService;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<String> xPaths= new ArrayList<String> ()
    {
        {
            add("//div[contains(@class, 'product_sell')]//p[contains(@class,'ps_sell_price')]//span[contains(@class, 'price_num')]"); //pcgarage
            add("//div[contains(@class, 'product-page-pricing')]//p[contains(@class, 'product-new-price')]"); // emag
            add("//div[contains(@class, 'pret_tabela')]//span[contains(@class, 'productPrice')]"); // cel
            add("//div[contains(@class, 'sdp-wrapp')]//div[contains(@class,'produs-price')]//span[contains(@class, 'price')]"); // flanco
        }
    };

    public String scrapUrl(String url) {
        try {
            String price = pollAtXpath(url);
            return price;
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    private String pollAtXpath(String url) throws IOException, InterruptedException {
        HtmlPage page = webClientService.getClient().getPage(url);
        String price = null;

        int tries = 45;
        while (tries > 0) {
            tries--;
            synchronized (page) {
                page.wait(350);
            }

            List<HtmlElement> els = xPaths.stream()
                    .map(xPath -> getByXpath(page, xPath))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if(els.size() > 0){
                price = els.get(0).getFirstChild().asText().split(",")[0];
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

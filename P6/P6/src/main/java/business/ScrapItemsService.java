package business;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Singleton
public class ScrapItemsService {

    @Inject
    private WebClientService webClientService;
    private HashMap<String, String> sites = new HashMap<String, String> () {
        {
            put("https://www.cel.ro/cauta/%s/", "//div[contains(@class, 'productListing')]//div[contains(@class, 'productListing-poza')]//a");
            put("https://www.pcgarage.ro/cauta/%s","//div[contains(@class, 'grid-products container-fluid')]//div[contains(@class, 'product_box_image')]//a");
            put("https://www.flanco.ro/catalogsearch/result/?q=%s", "//div[contains(@class, 'listing-wrapper')]//div[contains(@class, 'produs-img')]//a[contains(@class, 'product-new-link')]");
            put("https://www.emag.ro/search/%s?ref=effective_search", "//div[contains(@class, 'card-section-mid')]//a[contains(@class, 'js-product-url')]");
            //put("https://altex.ro/cauta/filtru/order/price/dir/asc/?q=%s", "//ul[contains(@class, 'Products Products--grid')]//a[contains(@class, 'Product-photoTrigger')]");
            //put("https://mediagalaxy.ro/cauta/filtru/order/price/dir/asc/?q=%s", "//ul[contains(@class, 'Products Products--grid')]//a[contains(@class, 'Product-photoTrigger')]");
        }
    };

    public List<String> scrap(String itemName) throws UnsupportedEncodingException {
        String param = URLEncoder.encode(itemName, "UTF-8");

        return sites
                .entrySet()
                .parallelStream()
                .map(entry -> CompletableFuture.supplyAsync(() ->
                        scrapWebsite(String.format(entry.getKey(), param), entry.getValue())))
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<String> scrapWebsite(String url, String xPath) {
        try {
            HtmlPage page = webClientService.getClient().getPage(url);

            int tries = 45;
            while(tries > 0) {
                tries--;
                synchronized (page) {
                    page.getWebClient().waitForBackgroundJavaScript(350);
                    page.wait(350);
                }

                List<HtmlAnchor> items = page.getByXPath(xPath);
                if (items.size() > 0) {
                    return items.stream().map(HtmlAnchor::getHrefAttribute)
                            .limit(5)
                            .collect(Collectors.toList());
                }
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

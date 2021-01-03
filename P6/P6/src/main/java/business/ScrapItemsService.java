package business;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ScrapItemsService {

    @Inject
    private WebClientService webClientService;

    public List<String> scrapEmag(String itemName) throws IOException, InterruptedException {
        String param = URLEncoder.encode(itemName, "UTF-8");
        String url = "https://www.emag.ro/search/"+ param +"?ref=effective_search";

        HtmlPage page = webClientService.getClient().getPage(url);

        synchronized (page) {
            page.wait(2500);
        }

        List<HtmlAnchor> items = page.getByXPath("//div[contains(@class, 'card-section-mid')]//a[contains(@class, 'js-product-url')]");
        List<String> urls = items.stream().map(item -> {
            String title = item.getAttribute("title");
            String itemUrl = item.getHrefAttribute();
            return itemUrl;
        }).collect(Collectors.toList());

        return urls;
    }
}

//product-title js-product-url

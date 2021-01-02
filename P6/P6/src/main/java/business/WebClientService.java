package business;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Singleton
public class WebClientService {

    private WebClient client;
    private LocalDateTime setAt;

    public WebClientService() {
        initClient();
    }

    public synchronized WebClient getClient() {
        if(ChronoUnit.MINUTES.between(setAt, LocalDateTime.now()) > 30) {
            initClient();
        }

        return client;
    }

    private void initClient() {
        client = new WebClient(BrowserVersion.CHROME);

        client.getOptions().setCssEnabled(false);
        client.waitForBackgroundJavaScript(500);
        client.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                return true;
            }
        });

        setAt = LocalDateTime.now();
    }
}

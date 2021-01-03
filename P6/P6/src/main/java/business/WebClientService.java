package business;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import javax.inject.Singleton;

@Singleton
public class WebClientService {

    public synchronized WebClient getClient() {
        WebClient client = new WebClient(BrowserVersion.CHROME);

        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        client.getOptions().setCssEnabled(false);
        client.waitForBackgroundJavaScript(500);
        client.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                return true;
            }
        });

        client.setCssErrorHandler(new SilentCssErrorHandler());
        client.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());

        return client;
    }
}

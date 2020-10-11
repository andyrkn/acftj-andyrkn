import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "WordsServlet",urlPatterns = {"/words" })
public class WordsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = new PrintWriter(resp.getWriter());

        String words = req.getParameter("letters");
        ArrayList<String> validWords = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(getServletContext().getRealPath(".")+"/words.txt"));
        String line = "";
        while(line != null){
            line = reader.readLine();
            if(line == null){break;}
            if(Valid(line, words)){
                validWords.add(line);
            }
        }

        String userAgent = req.getHeader("User-Agent");
        if(userAgent.matches("(?:^|\\W).*(Chrome|Firefox|Safari|Opera|Trident).*[\\w]+(?:\\s|$)")){
            out.println("<div><ul>");
            validWords.forEach(word -> out.println("<li>" + word + "</li>"));
            out.println("</ul></div>");
            out.close();
        }
        else{
            String output = new Gson().toJson(validWords);
            out.print(output);
        }
        getServletContext()
                .log(String.format(
                        "Method:%s IP:%s UserAgent:%s Languages:%s Params:%s",
                        req.getMethod(),
                        req.getLocalAddr(),
                        userAgent,
                        req.getLocale(),
                        new Gson().toJson(req.getParameterMap())));
    }

    private boolean Valid(String word, String incomingLetters) {
        char[] expectedletters = word.toCharArray();
        char[] letters = incomingLetters.toCharArray();

        for(int i = 0;i<expectedletters.length;i++){
            for(int j = 0;j<letters.length;j++){
                if(expectedletters[i] == letters[j]){
                    expectedletters[i] = '#';
                    letters[j] = '#';
                }
            }
        }

        for(int i = 0;i<expectedletters.length;i++){
            if(expectedletters[i]!= '#') {
                return false;
            }
        }
        return true;
    }
}

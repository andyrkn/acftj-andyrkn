package api;

import com.google.gson.Gson;
import domain.Word;
import persistance.IWordsRepository;
import persistance.WordsRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WordsDictionary",urlPatterns = {"/words" })
public class WordsServlet extends HttpServlet {

    IWordsRepository repo = new WordsRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Word word = new Word(req.getParameter("Language"), req.getParameter("Word"), req.getParameter("Definition"));
        boolean result = repo.AddWord(word);

        resp.setContentType("text/html");
        if(result){
            req.setAttribute("result", repo.GetAllWords());
            req.getRequestDispatcher("/result.jsp").forward(req,resp);
        }
        else {
            req.setAttribute("result", "nay");
            req.getRequestDispatcher("/error.jsp").forward(req,resp);
        }
    }
}

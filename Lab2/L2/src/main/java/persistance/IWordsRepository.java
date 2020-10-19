package persistance;

import domain.Word;

import java.util.ArrayList;

public interface IWordsRepository {

    ArrayList<Word> GetAllWords();
    boolean AddWord(Word word);
}

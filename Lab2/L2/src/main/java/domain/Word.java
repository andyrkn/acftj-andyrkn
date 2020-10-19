package domain;

public class Word {

    public String Language;
    public String Word;
    public String Definition;

    public Word(){ }

    public Word(String language, String word, String definition) {
        Language = language;
        Word = word;
        Definition = definition;
    }
}
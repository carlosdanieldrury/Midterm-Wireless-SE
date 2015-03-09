package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Carlos on 3/3/2015.
 */
public class Line implements Serializable {
    private int size;
    private ArrayList<Word> wordList;

    private int availableSize;

    public Line(int size) {
        this.size = size;
        wordList = new ArrayList<Word>();
        this.availableSize = size;

    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(int acumulatedSize) {
        this.availableSize = acumulatedSize;
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }

    public void addWord(Word word){
        if (word.getSize() <= getSize()){
            wordList.add(word);
            availableSize -= word.getSize();
        }
    }

    public Line clone(){
        Line line = new Line(getSize());
        ArrayList<Word> words = new ArrayList<Word>();
        for (Word w: getWordList()){
            words.add(w.clone());
        }
        line.setWordList(words);
        return line;
    }

    @Override
    public String toString() {
        String string = "";
        if (wordList.isEmpty())
            return string;
        for(int i=0; i<wordList.size(); i++)
            string += wordList.get(i) + " ";
        return string;
    }
}

package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import java.io.Serializable;

/**
 * Created by Carlos on 3/3/2015.
 */
public class Word implements Serializable {

    private String word;
    private int size;

    public Word(String word, int size) {
        this.word = word;
        this.size = size;
    }

    public Word(){
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWordFromOrigin(String string){
        this.size = Integer.parseInt(String.valueOf(string.charAt(0)));
        this.word = string.substring(1);

    }

    public String toString(){
        return getWord();
    }

    public Word clone(){
        Word w = new Word(getWord(), getSize());
        return w;
    }

}

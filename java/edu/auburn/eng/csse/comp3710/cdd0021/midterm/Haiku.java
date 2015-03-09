package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Carlos on 3/5/2015.
 */
public class Haiku implements Serializable {
    private ArrayList<Line> lines;
    private int line=0;


    public Haiku (){
        Line l1 = new Line(5);
        Line l2 = new Line(7);
        Line l3 = new Line(5);

        lines = new ArrayList<Line>(3);
        lines.add(l1);
        lines.add(l2);
        lines.add(l3);

    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public void setLine(int l,Line line){
        if ((l >= 0) && (l < lines.size())){
            lines.set(l,line);
        }
    }

    public void addWord(Word w){
        Line l;
        for (int i=0; i < lines.size(); i++){
            l = lines.get(i);
            if (l.getAvailableSize()>0){
                if (l.getAvailableSize() >= w.getSize()) {
                    l.addWord(w);
                    line = i;
                    break;
                }
            }
        }
    }

    public int getAvailableSpace(){
        int available = 0;
        Line l;
        for (int i=0; i < lines.size(); i++){
            l = lines.get(i);
                if (l.getAvailableSize() != 0) {
                    available = l.getAvailableSize();
                    line = i;
                    break;
                }
        }
        return available;
    }

    public String toString(){
        String result = "";
        for (Line l : lines){
            result.concat(l.toString()+"\n");
        }
        return result;
    }

    public String printLine(int line){
        String result = "";

        if ((line >= 0) && (line < lines.size())){
            result.concat(lines.get(line).toString());
        }

        return result;
    }

    public Haiku clone(){
        Haiku newHaiku = new Haiku();
 /*       Line newLine1 = new Line(5);
        Line newLine2 = new Line(7);
        Line newLine3 = new Line(5);

        ArrayList<Word> words1 = new ArrayList<Word>();
        ArrayList<Word> words2 = new ArrayList<Word>();
        ArrayList<Word> words3 = new ArrayList<Word>();*/

        for (int i=0; i < lines.size(); i++){
            for (Word w : lines.get(i).getWordList()){
                Word word = new Word(w.getWord(), w.getSize());
                newHaiku.addWord(word);

            }
        }


       /* newLine1.setWordList(lines.get(0).getWordList().clone());
        for(Line l: lines){
            newHaiku.setLine(0,newLine1);
        }*/

        return newHaiku;
    }


}

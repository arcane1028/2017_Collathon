package com.naver.naverspeech.collathon;

/**
 * Created by JSH on 2017-10-27.
 */

public class PhraseItem {

    private String phrase;
    private int time;

    public PhraseItem(String phrase, int time){
        this.phrase = phrase;
        this.time = time;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

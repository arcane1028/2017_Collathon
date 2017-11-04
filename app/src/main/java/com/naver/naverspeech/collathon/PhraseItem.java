package com.naver.naverspeech.collathon;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JSH on 2017-10-27.
 */

public class PhraseItem {

    private String phrase;
    private String time;
    //rank는 name/score의 형태로 저장되며 사용시 파싱해서 사용한다.
    List<String> rank = new ArrayList<>();

    public PhraseItem() {
    }

    public PhraseItem(String phrase, String time) {
        this.phrase = phrase;
        this.time = time;
        for(int i=0;i<3;i++)
            rank.add(String.format("%-10s %10d", "newman",0));
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getRank() {
        return rank;
    }

    public void setRank(List<String> rank) {
        this.rank = rank;
    }

    public boolean isRank(int input_score){
        int score=Integer.valueOf(rank.get(2).substring(11,21));
        if(score>input_score)
            return false;
        return true;
    }

    public void addRank(String input_name,int input_score){
        rank.remove(2);
        rank.add(String.format("%-10s %10d",input_name,input_score));

        int score=Integer.valueOf(rank.get(1).substring(11,21).replaceAll(" ", ""));
        if(score<input_score) {
            Collections.swap(rank, 1, 2);
            score=Integer.valueOf(rank.get(0).substring(11,21).replaceAll(" ", ""));
            if(score<input_score)
                Collections.swap(rank, 0, 1);
        }

    }
}

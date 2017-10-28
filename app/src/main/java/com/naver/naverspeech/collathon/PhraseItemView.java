package com.naver.naverspeech.collathon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by JSH on 2017-10-27.
 */

public class PhraseItemView extends LinearLayout{
    TextView phraseView;
    TextView phraseTimeView;

    public PhraseItemView(Context context) {
        super(context);
        init(context);
    }
    public PhraseItemView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.phrase_item, this, true);

        phraseView = (TextView)findViewById(R.id.phrase);
        phraseTimeView = (TextView)findViewById(R.id.phraseTime);
    }
    public void setPhrase(String phrase){ phraseView.setText(phrase);}
    public void setPhraseTime(int phraseTime){ phraseTimeView.setText(Integer.toString(phraseTime));}
}

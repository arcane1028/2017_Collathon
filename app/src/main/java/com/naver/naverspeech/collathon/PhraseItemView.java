package com.naver.naverspeech.collathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JSH on 2017-10-27.
 */

public class PhraseItemView extends BaseAdapter{
    Context c;
    ArrayList<PhraseItem> PhraseItems;

    public PhraseItemView(Context c, ArrayList<PhraseItem> PhraseItems) {
        this.c = c;
        this.PhraseItems = PhraseItems;
    }

    @Override
    public int getCount() {
        return PhraseItems.size();
    }

    @Override
    public Object getItem(int position) {
        return PhraseItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.phrase_item,parent,false);
        }

        TextView phraseTxt= (TextView) convertView.findViewById(R.id.phrase);
        TextView timeTxt= (TextView) convertView.findViewById(R.id.phraseTime);

        final PhraseItem s= (PhraseItem) this.getItem(position);

        phraseTxt.setText(s.getPhrase());
        timeTxt.setText(s.getTime());

        //ONITECLICK
        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,s.getPhrase(),Toast.LENGTH_SHORT).show();
            }
        });
         */
        return convertView;
    }
}
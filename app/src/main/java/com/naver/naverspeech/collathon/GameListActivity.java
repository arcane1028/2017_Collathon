package com.naver.naverspeech.collathon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {
    private Button create;
    private Button select;
    private Button random;
    private ListView phraseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);

        phraseList = (ListView) findViewById(R.id.phraseListView);
        create = (Button)findViewById(R.id.createButton);
        select = (Button)findViewById(R.id.selectButton);
        random = (Button)findViewById(R.id.randomButton);

        //TODO 리스트 생성 FireBase
        /*
            adapter = new SingerAdapter();
            adapter.addItem(new SingerItem("소녀시대", "010-1000-1000", 2007, R.drawable.girlsgeneration));
            listView.setAdapter(adapter);
         */

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 파이어베이스 추가 기능
                startActivity(new Intent(GameListActivity.this, CreateSentence.class));
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 선택 기능
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 랜덤 기능
                Intent intent = new Intent(GameListActivity.this, GameStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private class PhraseAdapter extends BaseAdapter {
        ArrayList<PhraseItem> items = new ArrayList<PhraseItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(PhraseItem item){ items.add(item);}

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PhraseItemView view = new PhraseItemView(getApplicationContext());

            PhraseItem item = items.get(position);
            view.setPhrase(item.getPhrase());
            view.setPhraseTime(item.getTime());

            return view;
        }
    }
}

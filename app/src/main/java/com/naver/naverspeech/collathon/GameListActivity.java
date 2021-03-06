package com.naver.naverspeech.collathon;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class GameListActivity extends AppCompatActivity {
    private Button create;
    private Button select;
    private Button random;
    private ListView phraseList;

    static DatabaseReference db;
    static FirebaseHelper helper;
    PhraseItemAdapter adapter;
    ListView listView;
    EditText phraseEditTxt, timeEditTxt;
    Button add;
    View selectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelist);
        final Intent activityIntent = new Intent(GameListActivity.this, GameStartActivity.class);

        phraseList = (ListView) findViewById(R.id.phraseListView);
        create = (Button) findViewById(R.id.createButton);
        select = (Button) findViewById(R.id.selectButton);
        random = (Button) findViewById(R.id.randomButton);
        add = (Button) findViewById(R.id.add_btn);
        phraseEditTxt = (EditText) findViewById(R.id.add_phrase);
        timeEditTxt = (EditText) findViewById(R.id.add_time);
        add = (Button) findViewById(R.id.add_btn);


        //파이어베이스 DB 초기화
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        //ADAPTER
        adapter = new PhraseItemAdapter(this);
        phraseList.setAdapter(adapter);
        helper.retrieve(adapter);

        phraseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PhraseItem phraseItem = (PhraseItem) adapterView.getAdapter().getItem(position);
                activityIntent.putExtra("PHRASE", phraseItem.getPhrase());
                activityIntent.putExtra("TIME", phraseItem.getTime());
                activityIntent.putExtra("DATA_KEY", phraseItem.getDataKey());
                activityIntent.putExtra("RANK0", phraseItem.getRank(0));
                activityIntent.putExtra("RANK1", phraseItem.getRank(1));
                activityIntent.putExtra("RANK2", phraseItem.getRank(2));
                if (selectView != null)
                    selectView.setVisibility(View.GONE);
                selectView = view.findViewById(R.id.rankList);
                selectView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 파이어베이스 추가 기능
                displayInputDialog();
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 선택 기능
                if (selectView == null) {
                    Toast.makeText(GameListActivity.this, "문장을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(activityIntent);
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 랜덤 기능
                Random random = new Random();
                if (adapter.getCount()<=0){
                    Toast.makeText(GameListActivity.this, "잠시만 기다려 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                int randomPosition = random.nextInt(adapter.getCount());
                PhraseItem randomPhraseItem = (PhraseItem) adapter.getItem(randomPosition);

                activityIntent.putExtra("PHRASE", randomPhraseItem.getPhrase());
                activityIntent.putExtra("TIME", randomPhraseItem.getTime());
                activityIntent.putExtra("DATA_KEY", randomPhraseItem.getDataKey());
                startActivity(activityIntent);
            }
        });

    }

    //문장 추가 다이얼로그
    private void displayInputDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.sentence_add_popup);

        phraseEditTxt = (EditText) dialog.findViewById(R.id.add_phrase);
        timeEditTxt = (EditText) dialog.findViewById(R.id.add_time);
        add = (Button) dialog.findViewById(R.id.add_btn);

        //SAVE
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String phrase = phraseEditTxt.getText().toString();
                String time = timeEditTxt.getText().toString();

                //SET DATA
                PhraseItem savePhraseItem = new PhraseItem(phrase, time);

                //SIMPLE VALIDATION
                if (phrase != null && phrase.length() > 0 && time != null && time.length() > 0) {
                    //THEN SAVE
                    if (helper.save(savePhraseItem)) {
                        //IF SAVED CLEAR EDITXT
                        phraseEditTxt.setText("");
                        timeEditTxt.setText("");

                        dialog.dismiss();

                        Toast.makeText(GameListActivity.this,
                                "\"" + phrase + "\"" + " 문장이 추가 되었습니다",
                                Toast.LENGTH_SHORT).show();

                        adapter = new PhraseItemAdapter(GameListActivity.this);
                        phraseList.setAdapter(adapter);
                        helper.retrieve(adapter);
                    }
                } else {
                    Toast.makeText(GameListActivity.this,
                            "추가하실 문장 및 제한시간이 입력됬는지 확인해주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}


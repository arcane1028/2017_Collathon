package com.naver.naverspeech.collathon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameResultActivity extends AppCompatActivity {
    EditText input_name;
    String data_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_result);

        Button button = (Button)findViewById(R.id.result_btn_back);
        TextView resultTextView = (TextView)findViewById(R.id.result_text_score);
        Intent intent = getIntent();
        //TODO 결과 보이기
        resultTextView.setText(Integer.toString(intent.getExtras().getInt("RESULT_SCORE")));

        final int result_score=intent.getExtras().getInt("RESULT_SCORE");
        resultTextView.setText(Integer.toString(result_score));

        Log.e("TEST PHRASE", intent.getExtras().get("RESULT_PHRASE").toString());
        Log.e("TEST SCORE", Integer.toString(result_score));
        Log.e("TEST SCORE", intent.getExtras().get("DATA_KEY").toString());

        data_key=intent.getExtras().get("DATA_KEY").toString();
        final List<String> rank = new ArrayList<>();
        for(int i=0;i<3;i++)
            rank.add(intent.getExtras().get("RANK"+i).toString());



        if(isRank(rank,result_score)){
            AlertDialog.Builder rank_dialog= new AlertDialog.Builder(this);
            rank_dialog.setTitle("이름을 입력하세요.");

            input_name=new EditText(this);
            input_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

            rank_dialog.setView(input_name);

            rank_dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference resultDb;
                    resultDb=FirebaseDatabase.getInstance().getReference("List/"+data_key);

                    resultDb.child("rank").setValue(addRank(rank,input_name.getText().toString(),result_score));

                    dialogInterface.dismiss();
                }
            });

            rank_dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            rank_dialog.show();

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public boolean isRank( List<String> rank,int input_score){
        int score=Integer.valueOf(rank.get(2).substring(11,21).replaceAll(" ", ""));
        if(score>input_score)
            return false;
        return true;
    }

    public List<String> addRank( List<String> rank,String input_name,int input_score){
        rank.remove(2);
        rank.add(String.format("%-10s %10d",input_name,input_score));

        int score=Integer.valueOf(rank.get(1).substring(11,21).replaceAll(" ", ""));
        if(score<input_score) {
            Collections.swap(rank, 1, 2);
            score=Integer.valueOf(rank.get(0).substring(11,21).replaceAll(" ", ""));
            if(score<input_score)
                Collections.swap(rank, 0, 1);
        }
        return rank;
    }
}

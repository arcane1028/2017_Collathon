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

public class GameResultActivity extends AppCompatActivity {
    EditText input_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_result);

        Button button = (Button)findViewById(R.id.result_btn_back);
        TextView resultTextView = (TextView)findViewById(R.id.result_text_score);

        Intent intent = getIntent();
        //TODO 결과 보이기
        resultTextView.setText(Integer.toString(intent.getExtras().getInt("RESULT_SCORE")));
        Log.d("TEST PHRASE", intent.getExtras().get("RESULT_PHRASE").toString());
        if(true){
            AlertDialog.Builder rank_dialog= new AlertDialog.Builder(this);
            rank_dialog.setTitle("이름을 입력하세요.");

            input_name=new EditText(this);
            input_name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});


            rank_dialog.setView(input_name);

            rank_dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

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
}

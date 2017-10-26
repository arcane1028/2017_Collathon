package com.naver.naverspeech.collathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_result);

        Button button = (Button)findViewById(R.id.result_btn_back);
        TextView resultTextView = (TextView)findViewById(R.id.result_text_score);

        Intent intent = getIntent();

        resultTextView.setText(Integer.toString(intent.getExtras().getInt("RESULT_SCORE")));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

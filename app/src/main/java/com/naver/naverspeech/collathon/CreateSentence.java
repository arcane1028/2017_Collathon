/**
 * 후에 유저아이디(네이버아이디)마다 리스트를 만들어 저장해야 할 것 같음.(현재 임시로 USER로해놓음)
 * 혹은 다 같은 리스트에 저장. 하지만 누가 올렸는지 모름.(다른 사람이 하기 너무 어렵거나 적절하지 못한 글 차단 어려움)
 * FIREBASE 계정 어떤것으로할지 논의.
 */

package com.naver.naverspeech.collathon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Yesum on 2017-10-30.
 */

public class CreateSentence extends AppCompatActivity {

    Button add_btn;
    EditText add_s;
    TextView add_des;
    String saverepo;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sentence_add_popup);

        add_btn = (Button) findViewById(R.id.add_btn);
        add_des = (TextView) findViewById(R.id.add_des);
        add_s = (EditText)findViewById(R.id.add_textbox); // 저장할 문장

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "USER"; //TODO 네이버 로그인 아이디로 바꾸어야 함.

                SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE); // DB선언

                   int i = pref.getInt("i",1); //유저가 추가한 횟수

                saverepo = "list/"+id+"/"+i+"번째 추가문장"; //문장 저장 위치

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference s_List = database.getReference(saverepo);

                s_List.setValue(add_s.getText().toString()); //문장 저장

                    SharedPreferences.Editor editor = pref.edit();// editor에 put 하기

                    i+=1;

                    editor.putInt("i", i);

                    editor.commit();

                finish();

                Toast.makeText(CreateSentence.this, "\""+add_s.getText().toString()+"\""+" 문장을 추가했습니다.", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
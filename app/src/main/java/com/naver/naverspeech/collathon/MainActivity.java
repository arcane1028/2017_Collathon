package com.naver.naverspeech.collathon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button selectButton;
    Button randomButton;
    Button createButton;
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectButton = (Button) findViewById(R.id.main_btn_select);
        randomButton = (Button) findViewById(R.id.main_btn_random);
        createButton = (Button) findViewById(R.id.main_btn_create);
        myButton = (Button) findViewById(R.id.main_btn_My);


        DatabaseReference myRef = database.getReference("hello");
        myRef.setValue("Hello, YDH!");

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                CharSequence info[] = new CharSequence[]{"간장공장", "경찰청창살"};

                builder.setTitle("제목");

                builder.setItems(info, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, GameStartActivity.class);
                        switch (which)

                        {
                            case 0:
                                intent.putExtra("TEXT", "간장공장");
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                intent.putExtra("TEXT", "경찰청창살");
                                startActivity(intent);
                                finish();
                                break;

                        }

                        dialog.dismiss();

                    }

                });

                builder.show();

            }
        });


        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameStartActivity.class);

                startActivity(intent);
                finish();

            }
        });

    }
}

package com.naver.naverspeech.collathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.naverspeech.collathon.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

public class GameStartActivity extends Activity {

    private static final String TAG = GameStartActivity.class.getSimpleName();
    private static final String CLIENT_ID = "_m0FWGMNhjAj1tSSzEEl";
    // 1. "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    // 2. build.gradle (Module:app)에서 패키지명을 실제 개발자센터 애플리케이션 설정의 '안드로이드 앱 패키지 이름'으로 바꿔 주세요

    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;

    private TextView txtResult;
    private ImageButton startImageButton;
    private String mResult;

    private AudioWriterPCM writer;
    private GameScore gameScore;

    private int start_time;
    private int end_time;

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                //txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                //TODO 임시 결과
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                //txtResult.setText(mResult);
                break;

            case R.id.finalResult:
                //TODO 결과 다 받은 경우
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                //TODO 결과 받기
                List<String> results = speechRecognitionResult.getResults();
                /*
                StringBuilder strBuf = new StringBuilder();
            	for(String result : results) {
            		strBuf.append(result);
            		strBuf.append("\n");
            	}
                mResult = strBuf.toString();
                txtResult.setText(mResult);
                */

                Intent intent = new Intent(GameStartActivity.this, GameResultActivity.class);

                //TODO 살리기
                /*
                intent.putExtra("RESULT", results.get(0));
                double resultScore = gameScore.parseSentence(results.get(0), getIntent().getExtras().getString("TEXT"));
                //디버깅을 위한 함수로서 나중에 Log로 바꿀것
                Toast.makeText(this, results.get(0) + "\n" + getIntent().getExtras().getString("TEXT"), Toast.LENGTH_LONG).show();
                intent.putExtra("RESULT_SCORE", gameScore.calculateScore(100, 0, (int) resultScore));
                */
                startActivity(intent);
                finish();

                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                //txtResult.setText(mResult);
                //startImageButton.setText(R.string.str_start);
                startImageButton.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                //startImageButton.setText(R.string.str_start);
                startImageButton.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        txtResult = (TextView) findViewById(R.id.start_text_sentence);
        startImageButton = (ImageButton) findViewById(R.id.start_btn_recode);
        gameScore = new GameScore();

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);
        txtResult.setText(getIntent().getExtras().getString("PHRASE"));


        startImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    //txtResult.setText("Connecting...");
                    //startImageButton.setText(R.string.str_stop);
                    startImageButton.setEnabled(false);
                    naverRecognizer.recognize();
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    startImageButton.setEnabled(false);

                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mResult = "";
        //txtResult.setText("");
        //startImageButton.setText(R.string.str_start);
        startImageButton.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<GameStartActivity> mActivity;

        RecognitionHandler(GameStartActivity activity) {
            mActivity = new WeakReference<GameStartActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            GameStartActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
}

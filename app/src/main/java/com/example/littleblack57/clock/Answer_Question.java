package com.example.littleblack57.clock;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.VIBRATOR_SERVICE;

public class Answer_Question extends AppCompatActivity {

    int i = 0;
    private TextView m_tv_answer;
    private TextView m_tv;
    private StringBuilder stringBuilder = new StringBuilder();
    private Ringtone m_ringtone;
    private Vibrator m_vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer__question);
        m_tv = (TextView) findViewById(R.id.tv_question);
        m_tv_answer = (TextView) findViewById(R.id.tv_answer);
        initQuestion();
        rinrtonePlay();

    }

    public void rinrtonePlay(){

        Intent intent = this.getIntent();
        boolean b = intent.getBooleanExtra(PlayReceiver.BUNDLE_KEY_VIBRATE,false);
        String ringtone_uri = intent.getStringExtra(PlayReceiver.BUNDLE_KEY_RINGTONETITLEURI);
        RingtoneManager ringtoneManager = new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALL);
        m_ringtone = ringtoneManager.getRingtone(ringtoneManager.getRingtonePosition(Uri.parse(ringtone_uri)));
        m_ringtone.play();
        if(b){

            m_vibrator=(Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
            m_vibrator.vibrate(new long[]{300,300},0);

        }


    }

    public void ok(View view) {


        if (String.valueOf(m_tv.getText()).equals(stringBuilder.toString()) && i >= 4) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            Toast.makeText(Answer_Question.this,"YA!! 你起床了!! 不要在賴床拉!!",Toast.LENGTH_LONG).show();
            m_ringtone.stop();
            if(m_vibrator.hasVibrator()) {
                m_vibrator.cancel();
            }
            finish();

        } else if (String.valueOf(m_tv.getText()).equals(stringBuilder.toString()) && i <= 4) {
            i++;
            Log.d("answer", Integer.toString(i));
            setQuestion();
            stringBuilder.replace(0, stringBuilder.length(), " ");
            Toast.makeText(Answer_Question.this,"答對了!!  完成"+ String.valueOf(i)+"/5  !!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Answer_Question.this, "看清楚阿!! 不是 \"" + stringBuilder.toString() + "\"", Toast.LENGTH_LONG).show();
        }

    }


    public void initQuestion() {
        m_tv = (TextView) findViewById(R.id.tv_question);
        int y = (int) (Math.random() * 100);
        String question = String.valueOf(y);
        m_tv.setText(question);
    }

    public void setQuestion() {
        int x = (int) (Math.random() * 100);
        String question = String.valueOf(x);
        m_tv_answer.setText("");
        m_tv.setText(question);
    }


    public void clickBtnNum(View view) {

        switch (view.getId()) {

            case R.id.btn_num1:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "1");
                    break;
                }
                stringBuilder.append("1");
                break;

            case R.id.btn_num2:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "2");
                    break;
                }
                stringBuilder.append("2");
                break;

            case R.id.btn_num3:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "3");
                    break;
                }
                stringBuilder.append("3");
                break;

            case R.id.btn_num4:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "4");
                    break;
                }
                stringBuilder.append("4");
                break;

            case R.id.btn_num5:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "5");
                    break;
                }
                stringBuilder.append("5");
                break;

            case R.id.btn_num6:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "6");
                    break;
                }
                stringBuilder.append("6");
                break;

            case R.id.btn_num7:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "7");
                    break;
                }
                stringBuilder.append("7");
                break;

            case R.id.btn_num8:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "8");
                    break;
                }
                stringBuilder.append("8");
                break;

            case R.id.btn_num9:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "9");
                    break;
                }
                stringBuilder.append("9");
                break;

            case R.id.btn_num0:
                if (stringBuilder.toString().equals(" ")) {
                    stringBuilder.replace(0, 1, "0");
                    break;
                }
                stringBuilder.append("0");
                break;

            case R.id.btn_back:

                if (stringBuilder.length() == 0) {
                    break;
                } else if (stringBuilder.length() == 1) {
                    stringBuilder.replace(0, 1, " ");
                    break;

                } else {
                    stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                }
                break;

        }

        m_tv_answer.setText(stringBuilder);
        Log.d("btnid", String.valueOf(view.getId()));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        m_ringtone.stop();
        if(m_vibrator.hasVibrator()) {
            m_vibrator.cancel();
        }
    }
}


package com.example.littleblack57.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Answer_Question extends AppCompatActivity {

    int i = 0;
    private EditText m_et;
    private TextView m_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer__question);
        initQuestion();
    }

    public void ok(View view) {

        m_tv = (TextView) findViewById(R.id.tv_question);
        m_et = (EditText) findViewById(R.id.et_answer);


        if (String.valueOf(m_tv.getText()).equals(String.valueOf(m_et.getText())) && i >= 4) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            finish();

        } else if (String.valueOf(m_tv.getText()).equals(String.valueOf(m_et.getText())) && i <= 4){
            i++;
            Log.d("ok", Integer.toString(i));
            setQuestion();
        }else {
           setQuestion();
        }


    }


    public void initQuestion() {
        m_tv = (TextView) findViewById(R.id.tv_question);
        int y = (int) (Math.random() * 100);
        String question = String.valueOf(y);
        m_tv.setText(question);
    }

    public void setQuestion(){
        int x = (int) (Math.random() * 100);
        String question = String.valueOf(x);
        m_et.setText("");
        m_tv.setText(question);
    }


}


package com.example.android.languagelearner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizEndActivity extends AppCompatActivity {

    TextView textScore;
    Button home;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        home = findViewById(R.id.home_btn);
        retry = findViewById(R.id.quiz_retry);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizEndActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.score = 0;
                Intent intent = new Intent(QuizEndActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        textScore = (TextView) findViewById(R.id.text_score);

        if (MainActivity.score > 0){
            //textScore.setVisibility(View.VISIBLE);
        } else {
            textScore.setVisibility(View.GONE);
        }

        if (MainActivity.score > 25) {
            textScore.setText("You scored " + Integer.toString(MainActivity.score) + " points. Good job!");
        } else {
            textScore.setText("You scored " + Integer.toString(MainActivity.score) + " points. Work harder!");
        }

    }

}

package com.example.android.languagelearner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Quiz2Activity extends AppCompatActivity {


    EditText answer;
    TextView question;
    Button submit;
    LinearLayout layout;
    ImageView img;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Quiz2Activity.this, Quiz3Activity.class);
                startActivity(intent);
            }
        }, 10000);

        /*count = getRandomNumberInRange(1, 4);

        if (count == 1) {
            img = MainActivity.img1;
            countCopy = 1;
        }

        else if (count == 2){
            img = MainActivity.img2;
            countCopy = 2;
        }

        else if (count == 3){
            img = MainActivity.img3;
            countCopy = 3;
        }

        else if (count == 4){
            img = MainActivity.img4;
            countCopy = 4;
        }

        else {
            img = MainActivity.img5;
            countCopy = 5;
        }*/


        img = MainActivity.img2;

        if (img.getParent()!=null) {
            ((ViewGroup) img.getParent()).removeView(img);
            }

        layout = (LinearLayout) findViewById(R.id.img_layout);
        layout.addView(img);


        answer = (EditText) findViewById(R.id.answer_box);
        question = (TextView) findViewById(R.id.quiz_question);
        submit = (Button) findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //isCorrect();
                String answerString = answer.getText().toString().toLowerCase().trim();
                String correctAnswer = MainActivity.arrayList.get(1).toLowerCase().trim();
                if (answerString.equals(correctAnswer)){
                    Toast.makeText(getApplicationContext(), "Great Job!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Oops! Try harder!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public boolean isCorrect(){
        String answerString = answer.getText().toString().toLowerCase().trim();
        String correctAnswer = MainActivity.arrayList.get(count).trim();
        if (answerString.equals(correctAnswer)){
            Toast.makeText(getApplicationContext(), "Great Job!", Toast.LENGTH_SHORT).show();
            MainActivity.score = MainActivity.score + 10;
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Oops! Try harder!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

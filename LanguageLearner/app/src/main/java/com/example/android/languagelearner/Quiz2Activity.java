package com.example.android.languagelearner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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


        img = MainActivity.img2;

        img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 750));
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        if (img.getParent()!=null) {
            ((ViewGroup) img.getParent()).removeView(img);
        }

        layout = (LinearLayout) findViewById(R.id.img_layout);
        layout.addView(img);


        answer = (EditText) findViewById(R.id.answer_box);

        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isCorrect(s.toString().toLowerCase().trim());
            }
        });

        //question = (TextView) findViewById(R.id.quiz_question);
        submit = (Button) findViewById(R.id.skip);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz2Activity.this, Quiz3Activity.class);
                startActivity(intent);
            }
        });

        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //isCorrect();
                String answerString = answer.getText().toString().toLowerCase().trim();
                String correctAnswer = MainActivity.arrayList.get(1).toLowerCase().trim();
                if (answerString.equals(correctAnswer)){
                    Toast.makeText(getApplicationContext(), "Great Job!", Toast.LENGTH_SHORT).show();
                    MainActivity.score = MainActivity.score + 10;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Oops! Try harder!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(Quiz2Activity.this, Quiz3Activity.class);
                startActivity(intent);
            }
        });*/

    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /*public boolean isCorrect(){
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
    }*/

    public boolean isCorrect(String answerString){
        //answerString = answer.getText().toString().toLowerCase().trim();
        String correctAnswer = MainActivity.arrayList.get(1).trim();
        if (answerString.equals(correctAnswer)){
            Toast.makeText(getApplicationContext(), "Great Job!", Toast.LENGTH_SHORT).show();
            MainActivity.score = MainActivity.score + 10;
            Intent intent = new Intent(Quiz2Activity.this, Quiz3Activity.class);
            startActivity(intent);
            return true;
        }
        else {
            //Toast.makeText(getApplicationContext(), "Oops! Try harder!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
}

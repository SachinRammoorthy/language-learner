package com.example.android.languagelearner;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    EditText answer;
    TextView question;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        answer = (EditText) findViewById(R.id.answer_box);
        question = (TextView) findViewById(R.id.quiz_question);
        submit = (Button) findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerString = answer.getText().toString().toLowerCase();
                if (answerString.equals("laptop")){
                    Toast.makeText(getApplicationContext(), "Great Job!", Toast.LENGTH_SHORT).show();
                    //question.setText("Great Job!");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Oops! Try harder!", Toast.LENGTH_SHORT).show();
                    //question.setText("Oops. Try again.");
                }
            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.camera:
                        takePicture();
                        break;
                    case R.id.navigation_dashboard:
                        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_quiz:
                        break;

                }

                return false;
            }
        });
    }

    private int checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission);
    }

    private void makeRequest(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, RECORD_REQUEST_CODE);
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

}

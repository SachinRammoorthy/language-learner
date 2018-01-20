package com.example.android.languagelearner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private static final String CLOUD_VISION_API_KEY = "AIzaSyCE0d7_DpfGkB82FOUnBnt_Vc1N35vJiHc";
    private static final String TRANSLATE_API_KEY = "AIzaSyCsb-Ip4pMg8touOycpEZvj4tgWTXAabZU";

    private Feature feature;
    private Bitmap bitmap;

    public static int score = 0;

    RelativeLayout relativeLayout;

    static ImageView img;
    static ImageView img1;
    static ImageView img2;
    static ImageView img3;
    static ImageView img4;
    static ImageView img5;

    CardView cardScore;
    TextView textScore;

    //TextView textTranslate;

    ProgressBar imageUploadProgress;
    LinearLayout linearLayout;

    int count = 0;

    public static ArrayList<String> arrayList;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();

        feature = new Feature();
        feature.setType("LABEL_DETECTION");
        feature.setMaxResults(1);

        final Handler textViewHandler = new Handler();

        cardScore = findViewById(R.id.card_score);
        textScore = findViewById(R.id.text_score);

        if (score > 0){
            cardScore.setVisibility(View.VISIBLE);
        } else {
            cardScore.setVisibility(View.GONE);
        }

        if (score > 25) {
            textScore.setText("You scored " + Integer.toString(score) + " points. Good job!");
        } else {
            textScore.setText("You scored " + Integer.toString(score) + " points. Work harder!");
        }


        linearLayout = (LinearLayout) findViewById(R.id.layout);
        imageUploadProgress = (ProgressBar) findViewById(R.id.imageProgress);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.camera:
                        takePicture();
                        break;
                    case R.id.navigation_dashboard:
                        break;
                    case R.id.navigation_quiz:
                        score = 0;
                        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                        startActivity(intent);
                        break;

                }

                return false;
            }
        });

        //textTranslate = findViewById(R.id.text_translate);

/*
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(TRANSLATE_API_KEY)
                        .build();
                Translate translate = options.getService();
                final Translation translation =
                        translate.translate("Hello World",
                                Translate.TranslateOption.targetLanguage("es"));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textTranslate != null) {
                            textTranslate.setText(translation.getTranslatedText());
                        }
                    }
                });
                return null;
            }
        }.execute();
*/
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

            relativeLayout = new RelativeLayout(this);
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );

            relativeLayout.setLayoutParams(params);

            count = count + 1;

            if (count == 1){
                img1 = new ImageView(this);
                //img1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                //img1.setMaxHeight(500);
                //img1.setMaxWidth(500);
                img1.setId(count);
                img1.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img1.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                relativeLayout.addView(img1);
                img1.setImageBitmap(bitmap);
            }
            else if (count == 2){
                img2 = new ImageView(this);
                img2.setLayoutParams(new android.view.ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                //img2.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                //img2.setMaxHeight(500);
                //img2.setMaxWidth(500);
                //img2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                img2.setId(count);
                img2.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img2.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                relativeLayout.addView(img2);
                img2.setImageBitmap(bitmap);
            }
            else if (count == 3){
                img3 = new ImageView(this);
                //img3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                img3.setLayoutParams(new android.view.ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                //img3.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                //img3.setMaxHeight(500);
                //img3.setMaxWidth(500);
                img3.setId(count);
                //img3.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img3.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img3.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                relativeLayout.addView(img3);
                img3.setImageBitmap(bitmap);
            }
            else if (count == 4){
                img4 = new ImageView(this);
                img4.setLayoutParams(new android.view.ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                //img4.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                //img4.setMaxHeight(500);
                //img4.setMaxWidth(500);
                img4.setId(count);
                //img4.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img4.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img4.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                relativeLayout.addView(img4);
                img4.setImageBitmap(bitmap);
            }
            else if (count == 5){
                img5 = new ImageView(this);
                img5.setLayoutParams(new android.view.ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                //img5.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                //img5.setMaxHeight(500);
                //img5.setMaxWidth(500);
                img5.setId(count);
                img5.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                img5.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

                relativeLayout.addView(img5);
                img5.setImageBitmap(bitmap);
            }

            callCloudVision(bitmap, feature);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == RECORD_REQUEST_CODE) {
            if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_DENIED
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();
            } else {
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void callCloudVision(final Bitmap bitmap, final Feature feature) {
        imageUploadProgress.setVisibility(View.VISIBLE);
        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                //Translate
                translateText(result);
                /*arrayList.add(result);
                TextView textView = new TextView(getApplicationContext());
                textView.setText(result);
                textView.setTextColor(Color.BLACK);

                LayoutParams paramsText = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                paramsText.setMargins(100, 100, 0, 0);

                cardView.addView(textView);
                linearLayout.addView(cardView);
                imageUploadProgress.setVisibility(View.INVISIBLE);*/
            }
        }.execute();
    }

    @NonNull
    private Image getImageEncodeImage(Bitmap bitmap) {
        Image base64EncodedImage = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {

        AnnotateImageResponse imageResponses = response.getResponses().get(0);

        List<EntityAnnotation> entityAnnotations;

        String message = "LABEL_DETECTION";
        entityAnnotations = imageResponses.getLabelAnnotations();
        message = formatAnnotation(entityAnnotations);

        return message;
    }

    @SuppressLint("StaticFieldLeak")
    private String formatAnnotation(List<EntityAnnotation> entityAnnotation) {
        String message = "";

        if (entityAnnotation != null) {
            for (EntityAnnotation entity : entityAnnotation) {
                message = message + " " + entity.getDescription();
            }
        } else {
            message = "Nothing Found";
        }

        return message;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    @SuppressLint("StaticFieldLeak")
    public void translateText(final String text){
        final Handler textViewHandler = new Handler();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(TRANSLATE_API_KEY)
                        .build();
                Translate translate = options.getService();
                final Translation translation =
                        translate.translate(text,
                                Translate.TranslateOption.targetLanguage("es"));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                            //textTranslate.setText(translation.getTranslatedText());
                            arrayList.add(translation.getTranslatedText());
                            TextView textView = new TextView(getApplicationContext());
                            textView.setText(translation.getTranslatedText());
                            textView.setTextColor(Color.BLACK);

                            LayoutParams paramsText = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT);

                            paramsText.setMargins(100, 100, 0, 0);

                            relativeLayout.addView(textView);
                            linearLayout.addView(relativeLayout);
                            imageUploadProgress.setVisibility(View.INVISIBLE);

                    }
                });
                return null;
            }
        }.execute();


    }



}

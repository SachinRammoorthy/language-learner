package com.example.android.languagelearner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

    private Feature feature;
    private Bitmap bitmap;

    public static int score;

    CardView cardView;

    static ImageView img;
    static ImageView img1;
    static ImageView img2;
    static ImageView img3;
    static ImageView img4;
    static ImageView img5;

    ProgressBar imageUploadProgress;
    LinearLayout linearLayout;

    int count = 0;

    public static ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arrayList = new ArrayList<>();

        feature = new Feature();
        feature.setType("LABEL_DETECTION");
        feature.setMaxResults(1);


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
                        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                        startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

            cardView = new CardView(this);
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );

            params.setMargins(0, 10, 0, 0);
            cardView.setContentPadding(10, 10, 10, 10);

            View view = new View(this);
            LayoutParams paramView = new LayoutParams(20,20);

            view.setLayoutParams(paramView);
            cardView.setLayoutParams(params);

            count = count + 1;

            if (count == 1){
                img1 = new ImageView(this);
                img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                img1.setMaxHeight(500);
                img1.setMaxWidth(500);
                img1.setId(count);

                cardView.addView(img1);
                linearLayout.addView(view);
                img1.setImageBitmap(bitmap);
            }
            else if (count == 2){
                img2 = new ImageView(this);
                img2.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                img2.setMaxHeight(500);
                img2.setMaxWidth(500);
                img2.setId(count);

                cardView.addView(img2);
                linearLayout.addView(view);
                img2.setImageBitmap(bitmap);
            }
            else if (count == 3){
                img3 = new ImageView(this);
                img3.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                img3.setMaxHeight(500);
                img3.setMaxWidth(500);
                img3.setId(count);

                cardView.addView(img3);
                linearLayout.addView(view);
                img3.setImageBitmap(bitmap);
            }
            else if (count == 4){
                img4 = new ImageView(this);
                img4.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                img4.setMaxHeight(500);
                img4.setMaxWidth(500);
                img4.setId(count);

                cardView.addView(img4);
                linearLayout.addView(view);
                img4.setImageBitmap(bitmap);
            }
            else if (count == 5){
                img5 = new ImageView(this);
                img5.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,300));
                img5.setMaxHeight(500);
                img5.setMaxWidth(500);
                img5.setId(count);

                cardView.addView(img5);
                linearLayout.addView(view);
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
                // translate the result here. 
                LanguageTranslator service = new LanguageTranslator();
                service.setUsernameAndPassword("{username}","{password}");
                
                arrayList.add(result);
                TextView textView = new TextView(getApplicationContext());
                textView.setText(result);
                textView.setTextColor(Color.BLACK);

                LayoutParams paramsText = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                paramsText.setMargins(100, 100, 0, 0);

                cardView.addView(textView);
                linearLayout.addView(cardView);
                imageUploadProgress.setVisibility(View.INVISIBLE);
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

}

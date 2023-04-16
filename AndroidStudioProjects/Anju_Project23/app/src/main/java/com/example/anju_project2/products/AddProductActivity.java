package com.example.anju_project2.products;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.anju_project2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ImageView imageView;
    ImageView imageView1;
    HashMap<String,String> config = new HashMap<>();
    ProgressBar bar,progressBar;
    String cloudUrl = "",priceS="",titleS="",nameS="";
    EditText textInputEditText,price,title;
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setElevation(0);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(click -> clickImage());
        imageView = findViewById(R.id.imageView6);
        textInputEditText = findViewById(R.id.editTextTextPersonName);
        title = findViewById(R.id.editTextTextPersonName2);
        price = findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button3);
        button.setEnabled(false);
        progressBar = findViewById(R.id.progressBar5);
        button.setOnClickListener(listener -> addProduct());
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 nameS = charSequence.toString();
                 if(nameS.length() == 0 || titleS.length() == 0 || priceS.length() == 0 || cloudUrl.length() == 0){
                     button.setEnabled(false);
                 }else{
                     button.setEnabled(true);
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
priceS = charSequence.toString();
                if(nameS.length() == 0 || titleS.length() == 0 || priceS.length() == 0 || cloudUrl.length() == 0){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
titleS = charSequence.toString();
                if(nameS.length() == 0 || titleS.length() == 0 || priceS.length() == 0 || cloudUrl.length() == 0){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bar = findViewById(R.id.progressBar2);
        bar.setMax(100);
        config.put("cloud_name", "duk7t9wyx");
        config.put("api_key", "244938426147517");
        config.put("api_secret", "IaDQTtm6vltEoB6Vf8vTb_E2FvM");
        try {


            MediaManager.init(this, config);
        }
        catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void addProduct() {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String,String> map = new HashMap<>();
        map.put("name",nameS);
        map.put("price","$"+priceS);
        map.put("title",titleS);
        map.put("url",cloudUrl);
        firebaseFirestore.collection("products")
                .add(map)
                .addOnSuccessListener(success -> {
                      super.onBackPressed();
                }).addOnFailureListener(fail -> {
                    button.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                });

    }

    private void clickImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1000);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
//Firebase
            MediaManager.get().upload(bytes).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(AddProductActivity.this,"Adding",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d("progresss",String.valueOf((totalBytes -bytes)%100));
                    if((totalBytes -bytes)%100 == 0){
                        bar.setProgress(100);
                    }else{
                        bar.setProgress((int)(totalBytes -bytes)%100);
                    }

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    cloudUrl = resultData.get("url").toString();
                    if(nameS.length() == 0 || titleS.length() == 0 || priceS.length() == 0 || cloudUrl.length() == 0){
                        button.setEnabled(false);
                    }else{
                        button.setEnabled(true);
                    }
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                }
            }).dispatch();

        }
    }
}
package com.example.anju_project2.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.anju_project2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class ProductDetail extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    TextView textView1;
    Button button;
    Button button8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView4);
        textView1 = findViewById(R.id.textView5);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(success -> openQuantity());
            String id = getIntent().getStringExtra("id");
            Log.d("Hi",id);
        try {
            getProductDetails(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openQuantity() {




    }

    private void getProductDetails(String id) throws IOException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ClothingDto[] clothingDto = {new ClothingDto()};
        db.collection("products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              Log.d("HII",documentSnapshot.getData().toString());
              clothingDto[0] = new ClothingDto(documentSnapshot.getData().get("name").toString(),documentSnapshot.getData().get("price").toString(),documentSnapshot.getData().get("url").toString(),documentSnapshot.getId(),documentSnapshot.getData().get("title").toString());
               new Thread(new FetchImage(clothingDto[0].getUrl(),ProductDetail.this)).start();
               textView.setText(clothingDto[0].getName()+" "+clothingDto[0].getTitle());
                textView1.setText(clothingDto[0].getPrice());

            }
        });


    }

    class FetchImage implements  Runnable {
        private String url;
        Context context;
        FetchImage(String url,Context context){
            this.url = url;
            this.context = context;
        }

        @Override
        public void run() {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {

                    imageView.setImageBitmap(response);

                }
            }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

            requestQueue.add(imageRequest);

        }
    }


}
package com.example.anju_project2.products;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.anju_project2.R;
import com.example.anju_project2.iam.CheckoutActivity;
import com.example.anju_project2.iam.SignupActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    TextView textView1;
    Button button;
    Button button8, button9, button10;
    ClothingDto clothingDto1;
    Spinner spinner,spinner2;
    String quantity = "1",size = "S";
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setElevation(0);
//        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView4);
        textView1 = findViewById(R.id.textView5);
//        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar3);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "10", "11"};
        String[] sizes = new String[]{"S","M","L","XL","XXL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        spinner2.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quantity = items[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size = sizes[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        button8.setOnClickListener(success -> openQuantity());
//        button.setOnClickListener(success -> addWishList());
        button9.setOnClickListener(success -> addToBag());
        String id = getIntent().getStringExtra("id");
        Log.d("Hi", id);
        try {
            getProductDetails(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void addToBag() {
          button9.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String name = sharedPref.getString("isSignup", "");
        if (name.equals("")) {
            Log.d("CONSOLE", "name");
            Intent intent = new Intent(this, SignupActivity.class);

            startActivity(intent);
        } else {


            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            SharedPreferences sharedPref1 = getSharedPreferences("myPreferences",Context.MODE_PRIVATE);
            String id = sharedPref.getString("userId", "");
            Log.d("UserId", id);
            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> wishListData = new HashMap<>();
            wishListData.put("productId", clothingDto1.getId());
            wishListData.put("quantity", quantity);
            wishListData.put("size", size);
            wishListData.put("userId", id);
            wishListData.put("url", clothingDto1.getUrl());
            wishListData.put("name", clothingDto1.getName());
            wishListData.put("title",clothingDto1.getTitle());
            db.collection("wishlist").add(wishListData).addOnSuccessListener(success -> {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ProductDetail.this, "Added successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProductDetail.this, CheckoutActivity.class);
                intent.putExtra("uId", id);
                intent.putExtra("pId", clothingDto1.getId());
                startActivity(intent);
            }).addOnFailureListener(fail -> {

                button9.setVisibility(View.VISIBLE);
                Toast.makeText(ProductDetail.this, "Failed Try Again", Toast.LENGTH_LONG).show();


            });
        }

    }

    private void getProductDetails(String id) throws IOException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ClothingDto[] clothingDto = {new ClothingDto()};
        db.collection("products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("HII", documentSnapshot.getData().toString());
                clothingDto[0] = new ClothingDto(documentSnapshot.getData().get("name").toString(), documentSnapshot.getData().get("price").toString(), documentSnapshot.getData().get("url").toString(), documentSnapshot.getId(), documentSnapshot.getData().get("title").toString());

                String url =  clothingDto[0].getUrl();

                if(!clothingDto[0].getUrl().contains("https")){
                    url = url.replaceFirst("http","https");
                }
                Picasso.get().load(url).placeholder(R.drawable.logo).into(imageView);
                clothingDto1 = clothingDto[0];
                textView.setText(clothingDto[0].getName() + " " + clothingDto[0].getTitle());
                textView1.setText(clothingDto[0].getPrice());

            }
        });


    }

    class FetchImage implements Runnable {
        private String url;
        Context context;

        FetchImage(String url, Context context) {
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
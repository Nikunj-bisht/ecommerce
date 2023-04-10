package com.example.anju_project2.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anju_project2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ProductActivity extends AppCompatActivity implements ProductRecyclerAdapter.ProductCallback {
    private  RecyclerView recyclerView;
    private ArrayList<ClothingDto> arrayList;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.productRecycler);
        progressBar = findViewById(R.id.progressBar);
        fetchData();

    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<ClothingDto> arrayList1 = new ArrayList<>();
        db.collection("products")
                .get()
                .addOnCompleteListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots.getResult()) {
                        arrayList1.add(new ClothingDto(document.getData().get("name").toString(),document.getData().get("price").toString(),document.getData().get("url").toString(),document.getId(),document.getData().get("title").toString()));
                        Log.d("TAG", document.getId() + " => " + document.getData());

                    }

                     progressBar.setVisibility(View.INVISIBLE);
                    ProductRecyclerAdapter productRecyclerAdapter = new ProductRecyclerAdapter(arrayList1,this,this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(productRecyclerAdapter);

                })
                .addOnFailureListener(failure -> {

                });
//        ArrayList<ClothingDto> arrayList1 = new ArrayList<>(Arrays.asList(new ClothingDto("ZARA Tshirt","5000","https://img.freepik.com/free-photo/vertical-full-length-picture-isolated-image-charming-european-teenage-girl-wearing-stylish-summer-clothes_343059-3431.jpg?w=360&t=st=1680504918~exp=1680505518~hmac=f2e39becc03eb63c4f08f8ed7df9433f2292b1d4c0766617e0f29d82869b2ef6")));




    }


    public void savedata(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> products = new HashMap<>();
        products.put("name", "Allen solley");
        products.put("price", 2000);
        products.put("url", "https://img.freepik.com/free-photo/vertical-full-length-picture-isolated-image-charming-european-teenage-girl-wearing-stylish-summer-clothes_343059-3431.jpg?w=360&t=st=1680504918~exp=1680505518~hmac=f2e39becc03eb63c4f08f8ed7df9433f2292b1d4c0766617e0f29d82869b2ef6");
        db.collection("products")
                .add(products)
                .addOnSuccessListener(success -> {
                    Toast.makeText(ProductActivity.this,"Done",Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(failure -> {

                    Toast.makeText(ProductActivity.this,"Failed",Toast.LENGTH_LONG).show();
                });
    }



    @Override
    public void getProductDetails(String id) {
        Intent intent = new Intent(this,ProductDetail.class);
        intent.putExtra("id",id);
        startActivity(intent);

    }
}
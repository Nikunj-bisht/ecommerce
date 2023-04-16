package com.example.anju_project2.iam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anju_project2.R;
import com.example.anju_project2.products.GridSpacingItemDecoration;
import com.example.anju_project2.products.ProductRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<OrderDto> arrayList = new ArrayList<>();
    Button button;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        String pId = getIntent().getStringExtra("pId");
        recyclerView = findViewById(R.id.orderView);
        button = findViewById(R.id.button);
        button.setOnClickListener(success -> {
            gotoPage();
        });
        String uId = getIntent().getStringExtra("uId");
        Log.d("DATA", pId + " " + uId);
        progressBar = findViewById(R.id.progressBar4);
        fetchWishListData(uId, pId);
        button.setEnabled(false);
    }

    private void gotoPage() {
        Intent intent = new Intent(CheckoutActivity.this,PlaceOrderActivity.class);
        intent.putExtra("data",arrayList);

        startActivity(intent);
    }


    private void fetchWishListData(String uId, String pId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        firebaseFirestore.collection("wishlist")

                .whereEqualTo("userId", uId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        button.setEnabled(true);
                        for (QueryDocumentSnapshot q : task.getResult()) {
                            arrayList.add(new OrderDto(q.getData().get("url").toString(), q.getData().get("quantity").toString(), q.getData().get("name").toString(), q.getData().get("size").toString(), q.getData().get("userId").toString(), q.getId()));
                        }
                        CheckoutRecycler productRecyclerAdapter = new CheckoutRecycler(CheckoutActivity.this, arrayList);
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
                        recyclerView.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
                        recyclerView.setAdapter(productRecyclerAdapter);


                    }
                });


    }
}
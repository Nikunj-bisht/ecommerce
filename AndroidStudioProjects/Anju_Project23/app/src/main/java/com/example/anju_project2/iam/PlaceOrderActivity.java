package com.example.anju_project2.iam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.anju_project2.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    ArrayList<OrderDto> arrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        button = findViewById(R.id.button4);
        button.setOnClickListener(click -> placeOrder());
        progressBar = findViewById(R.id.progressBar8);
        arrayList = (ArrayList<OrderDto>) getIntent().getSerializableExtra("data");

    }

    private void placeOrder() {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        WriteBatch batch = firebaseFirestore.batch();
        arrayList.stream().forEach(item -> {
            batch.delete(firebaseFirestore.collection("wishlist").document(item.getId()));
        });

        batch.commit()
                .addOnSuccessListener(success -> {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
                        notificationChannel.setDescription("Get ready to prepare for interview!");
                        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(notificationChannel);
                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "channel1")
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("Order Placed with Id #0120912")
                                .setContentInfo("Get ready to prepare for interview!")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();

                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        notificationManagerCompat.notify(1, notification);
                        progressBar.setVisibility(View.INVISIBLE);
                        super.onBackPressed();
                    }
                }).addOnFailureListener(fail -> {

                });


    }

}
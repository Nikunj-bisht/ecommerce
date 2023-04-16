package com.example.anju_project2.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anju_project2.R;
import com.example.anju_project2.iam.SignupActivity;
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
    private RecyclerView recyclerView;
    private ArrayList<ClothingDto> arrayList;
    ProgressBar progressBar;
    static Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        recyclerView = findViewById(R.id.productRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        recyclerView.setLayoutManager(gridLayoutManager);
        progressBar = findViewById(R.id.progressBar);
        fetchData();

    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<ClothingDto> arrayList1 = new ArrayList<>();
        db.collection("products")
                .get()
                .addOnCompleteListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots.getResult()) {
                        arrayList1.add(new ClothingDto(document.getData().get("name").toString(), document.getData().get("price").toString(), document.getData().get("url").toString(), document.getId(), document.getData().get("title").toString()));
                        Log.d("TAG", document.getId() + " => " + document.getData());

                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    ProductRecyclerAdapter productRecyclerAdapter = new ProductRecyclerAdapter(arrayList1, this, this);

                    recyclerView.setAdapter(productRecyclerAdapter);

                })
                .addOnFailureListener(failure -> {


                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        menu.removeItem(R.id.login);
        menu.removeItem(R.id.add);
        getMenuInflater().inflate(R.menu.productmenu, menu);

        SharedPreferences sharedPref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        String name = sharedPref.getString("isSignup", "");
        if (name.equals("")) {
            Log.d("CONSOLE", "name");
            menu.removeItem(R.id.add);
            return super.onCreateOptionsMenu(menu);
        } else if (!name.equals("")) {

            menu.findItem(R.id.login).setTitle("Logout");
            if (!sharedPref.getString("userId", "").equals("3RxOxSuH6UBqqLOWXV37")) {
                menu.removeItem(R.id.add);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.login && item.getTitle().equals("Login")) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddProductActivity.class));
        } else {
            // remove user
            SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(
                    "isSignup", ""
            );
            editor.putString(
                    "userId", ""
            );
            editor.apply();

            super.onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    public void savedata() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> products = new HashMap<>();
        products.put("name", "Allen solley");
        products.put("price", 2000);
        products.put("url", "https://img.freepik.com/free-photo/vertical-full-length-picture-isolated-image-charming-european-teenage-girl-wearing-stylish-summer-clothes_343059-3431.jpg?w=360&t=st=1680504918~exp=1680505518~hmac=f2e39becc03eb63c4f08f8ed7df9433f2292b1d4c0766617e0f29d82869b2ef6");
        db.collection("products")
                .add(products)
                .addOnSuccessListener(success -> {
                    Toast.makeText(ProductActivity.this, "Done", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(failure -> {

                    Toast.makeText(ProductActivity.this, "Failed", Toast.LENGTH_LONG).show();
                });
    }


    @Override
    public void getProductDetails(String id) {
        Intent intent = new Intent(this, ProductDetail.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }

    @Override
    public void deleteProducct(String id) {


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("products").document(id)
                .delete().addOnSuccessListener(success -> {
                    fetchData();
                }).addOnFailureListener(fail -> {

                });


        // A null listener allows the button to dismiss the dialog and take no further action.

    }

    @Override
    protected void onResume() {
        super.onStart();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fetchData();
        onCreateOptionsMenu(this.menu);

    }
}
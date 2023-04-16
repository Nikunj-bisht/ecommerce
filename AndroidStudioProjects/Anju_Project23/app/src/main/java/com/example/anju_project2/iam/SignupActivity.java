package com.example.anju_project2.iam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anju_project2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Button button2;
    TextInputEditText editText;
    EditText editText2;
    String userName = "", password = "";
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editText = findViewById(R.id.username);
        editText2 = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar6);
        button2 = findViewById(R.id.button2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Typing", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userName = charSequence.toString();
                if (userName.length() == 0 || password.length() == 0) {
                    button2.setEnabled(false);
                } else {
                    button2.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString();
                if (userName.length() == 0 || password.length() == 0) {
                    button2.setEnabled(false);
                } else {
                    button2.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button2.setOnClickListener(listener -> signUp());
        button2.setEnabled(false);
    }

    private void signUp() {
        button2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("name", userName);
        userDetails.put("password", password);

        db.collection("users").whereEqualTo("name", userName)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (QueryDocumentSnapshot q : task.getResult()) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                              Toast.makeText(this,q.getId(),Toast.LENGTH_LONG).show();
                                    Log.d("UserId", q.getId());
                                    editor.putString(
                                            "isSignup", "true"
                                    );
                                    editor.putString(
                                            "userId", q.getId()
                                    );
                                    progressBar.setVisibility(View.INVISIBLE);
                                    editor.apply();
                                    SignupActivity.super.onBackPressed();


                                }
                            } else {
                                createUser();
                            }
                        }
                    }
                });


    }

    public void createUser() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("name", userName);
        userDetails.put("password", password);
        db.collection("users").add(userDetails).addOnSuccessListener(success -> {
            SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Toast.makeText(this, success.getId(), Toast.LENGTH_LONG).show();
            Log.d("UserId", success.getId());
            editor.putString(
                    "isSignup", "true"
            );
            editor.putString(
                    "userId", success.getId()
            );

            editor.apply();

            super.onBackPressed();

        }).addOnFailureListener(fail -> {

        });
    }

}
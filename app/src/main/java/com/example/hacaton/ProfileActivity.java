package com.example.hacaton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hacaton.data.PreferenceConfig;
import com.example.hacaton.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private int passedCourses;
    private int correctCourses;
    private int coins;

    private WebView webView;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        String emailPref = PreferenceConfig.getEmail(this);
        Log.d("EMAIL", emailPref);
        coins = PreferenceConfig.getACoins(this);
        name = PreferenceConfig.getName(this);
        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/profile.html"); // Завантаження локальної сторінки

        databaseReference = FirebaseDatabase.getInstance("https://hackaton-3f311-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Item 1");
        myList.add("Item 2");
        myList.add("Item 3");

        User user = new User(passedCourses, correctCourses, coins, 0, name, myList);

        saveUserDataToDatabase(emailPref, user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Обробка вибору елемента меню-
                if (item.getItemId() == R.id.profile){
                    return false;
                }else if(item.getItemId() == R.id.list){
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    Log.d("BOTTOM NAVIGATION", "PRESSED PROFILE MENU");
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish();
                } else if (item.getItemId() == R.id.game) {
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    Log.d("BOTTOM NAVIGATION", "PRESSED GAME MENU");
                    startActivity(new Intent(ProfileActivity.this, GameActivity.class));
                    finish();
                }
                return false;
            }
        });
    }


    private void saveUserDataToDatabase(String email, User user) {
        // Перетворюємо email у ключ (замінюємо спец. символи)
        String userEmailKey = email.replace(".", "_").replace("@", "_");

        // Отримуємо посилання на конкретного користувача
        DatabaseReference userRef = databaseReference.child(userEmailKey);

        // Перевіряємо, чи існує вже запис для цього користувача
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Користувач вже існує
                    Toast.makeText(ProfileActivity.this, "User already exists in the database", Toast.LENGTH_SHORT).show();
                    Log.d("DATABASE", "User already exists: " + dataSnapshot.getValue());
                } else {
                    // Користувача не існує, зберігаємо дані
                    userRef.setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "User data saved to database", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("DATABASE", "Failed to save user data: " + task.getException().getMessage());
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обробка помилки
                Toast.makeText(ProfileActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DATABASE", "Database error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
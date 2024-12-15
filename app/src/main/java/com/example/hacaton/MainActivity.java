package com.example.hacaton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hacaton.data.PreferenceConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Ініціалізація WebView
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Увімкнення підтримки JavaScript
        webView.loadUrl("file:///android_asset/courses.html"); // Завантаження локальної сторінки

        // Ініціалізація BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.list);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Обробка вибору елемента меню-
                if (item.getItemId() == R.id.profile){
                    Log.d("BOTTOM NAVIGATION", "PRESSED PROFILE MENU");
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }else if(item.getItemId() == R.id.list){
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    return false;
                }else if (item.getItemId() == R.id.game) {
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    Log.d("BOTTOM NAVIGATION", "PRESSED GAME MENU");
                    startActivity(new Intent(MainActivity.this, GameActivity.class));
                    finish();
                }
                return false;
            }
        });
    }

}
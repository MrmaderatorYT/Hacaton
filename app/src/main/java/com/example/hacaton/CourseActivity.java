package com.example.hacaton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CourseActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("taskwarriors://courses")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        // Завантаження HTML-сторінки
        webView.loadUrl("file:///android_asset/courses.html");


        // Ініціалізація BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Обробка вибору елемента меню-
                if (item.getItemId() == R.id.profile){
                    Log.d("BOTTOM NAVIGATION", "PRESSED PROFILE MENU");
                    startActivity(new Intent(CourseActivity.this, ProfileActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }else if(item.getItemId() == R.id.list){
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    return false;
                }else if (item.getItemId() == R.id.game) {
                    Log.d("BOTTOM NAVIGATION", "PRESSED LIST MENU");
                    Log.d("BOTTOM NAVIGATION", "PRESSED GAME MENU");
                    startActivity(new Intent(CourseActivity.this, GameActivity.class));
                    finish();
                }
                return false;
            }
        });

        // Initialize WebView

        }
    }
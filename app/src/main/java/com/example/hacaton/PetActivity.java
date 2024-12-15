package com.example.hacaton;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class PetActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Увімкнути JavaScript

        // Додаємо JavaScript Interface для взаємодії з Java
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setWebViewClient(new WebViewClient());

        // Завантажуємо HTML-файл
        webView.loadUrl("file:///android_asset/pet.html");
    }

    // JavaScript Interface для взаємодії з Java
    public class WebAppInterface {
        @android.webkit.JavascriptInterface
        public void onImageClicked(String imageId) {
            // Запускаємо іншу активність в залежності від натиснутого зображення
            Intent intent;
            switch (imageId) {
                case "stone":
                    intent = new Intent(PetActivity.this, GameActivity.class);
                    break;
                case "parcel":
                    intent = new Intent(PetActivity.this, ShopActivity.class);//shop
                    break;
                case "hood":
                    intent = new Intent(PetActivity.this, PetActivity.class);
                    break;
                default:
                    return;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

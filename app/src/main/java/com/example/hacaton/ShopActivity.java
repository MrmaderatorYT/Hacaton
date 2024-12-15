package com.example.hacaton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hacaton.data.PreferenceConfig;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    private WebView webView;
    private int coins; // Початкова кількість монеток
    private ArrayList<String> purchasedItems = new ArrayList<>(); // Список придбаних товарів


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        coins = PreferenceConfig.getACoins(this);
        purchasedItems = PreferenceConfig.getPurchasedItems(this);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true); // Увімкнути JavaScript

        // Додаємо JavaScript Interface для взаємодії з Java
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setWebViewClient(new WebViewClient());

        // Завантажуємо HTML-файл
        webView.loadUrl("file:///android_asset/shop.html");
    }

    // JavaScript Interface для взаємодії з Java
    public class WebAppInterface {
        @android.webkit.JavascriptInterface
        public void onBuyItem(String itemId, String price) {
            int itemPrice = Integer.parseInt(price);

            if (coins >= itemPrice) {
                // Зменшуємо кількість монеток
                coins -= itemPrice;
                // Додаємо айді товару до списку придбаних
                purchasedItems.add(itemId);
                // Зберігаємо оновлений список у SharedPreferences
                PreferenceConfig.savePurchasedItems(ShopActivity.this, purchasedItems);
                // Зберігаємо оновлену кількість монеток
                PreferenceConfig.setCoins(getApplicationContext(), coins);
            } else {
                // Повідомляємо користувача про недостатньо монеток
                Toast.makeText(ShopActivity.this, "Не вистачає коштів", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Метод для показу повідомлення про успішну покупку


    // Метод для показу повідомлення про недостатньо монеток


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
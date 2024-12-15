package com.example.hacaton;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private WebView webView;
    private int userWins = 0; // Змінна для підрахунку перемог користувача

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Увімкнути JavaScript

        // Додаємо JavaScript Interface для взаємодії з Java
        webView.addJavascriptInterface(new GameInterface(), "Android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Додаємо обробники натискань кнопок через Java
                setupButtonHandlers();
            }
        });

        // Завантажуємо HTML-файл
        webView.loadUrl("file:///android_asset/game.html");
    }

    private void setupButtonHandlers() {
        // Обробник для кнопки "Камінь"
        webView.evaluateJavascript("document.getElementById('rock').onclick = function() { Android.onPlayerChoice('rock'); }", null);

        // Обробник для кнопки "Ножиці"
        webView.evaluateJavascript("document.getElementById('scissors').onclick = function() { Android.onPlayerChoice('scissors'); }", null);

        // Обробник для кнопки "Папір"
        webView.evaluateJavascript("document.getElementById('paper').onclick = function() { Android.onPlayerChoice('paper'); }", null);
    }

    // Метод для обробки вибору гравця
    private void onPlayerChoice(String playerChoice) {
        String computerChoice = getComputerChoice();
        String result = determineWinner(playerChoice, computerChoice);

        // Оновлюємо текст результату на сторінці
        final String resultText = "Комп'ютер вибрав: " + computerChoice + ". " + result;
        webView.evaluateJavascript("document.getElementById('result').innerText = '" + resultText + "';", null);

        // Якщо користувач переміг, збільшуємо лічильник перемог
        if (result.equals("Ви виграли!")) {
            userWins++;
            // Показуємо кількість перемог
            webView.evaluateJavascript("document.getElementById('result').innerText += ' Перемог: " + userWins + "';", null);
        }
    }

    // Метод для отримання вибору комп'ютера
    private String getComputerChoice() {
        String[] choices = {"rock", "scissors", "paper"};
        int randomIndex = (int) (Math.random() * 3);
        return choices[randomIndex];
    }

    // Метод для визначення переможця
    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "Нічия!";
        } else if (
                (playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                        (playerChoice.equals("scissors") && computerChoice.equals("paper")) ||
                        (playerChoice.equals("paper") && computerChoice.equals("rock"))
        ) {
            return "Ви виграли!";
        } else {
            return "Ви програли!";
        }
    }

    // JavaScript Interface для взаємодії з Java
    private class GameInterface {
        @android.webkit.JavascriptInterface
        public void onPlayerChoice(String choice) {
            onPlayerChoice(choice);
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
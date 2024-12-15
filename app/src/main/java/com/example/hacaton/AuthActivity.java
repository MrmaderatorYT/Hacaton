package com.example.hacaton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hacaton.data.PreferenceConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AuthActivity extends AppCompatActivity {

    private WebView webView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        String email = PreferenceConfig.getEmail(this);
        password = PreferenceConfig.getPass(this);

        // Ініціалізація Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://hackaton-3f311-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        Toast.makeText(this, "HG", Toast.LENGTH_SHORT).show();
        // Ініціалізація WebView
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("taskwarriors://register")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        handleRegistration(url);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        // Завантаження HTML-сторінки
        webView.loadUrl("file:///android_asset/registration.html");
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void handleRegistration(String url) {
        // Розбір URL для отримання даних з форми
        String[] params = url.split("\\?")[1].split("&");
        String email = null, password = null, name = null;
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue[0].equals("email")) {
                email = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            } else if (keyValue[0].equals("password")) {
                password = keyValue[1];
            } else if (keyValue[0].equals("name")) {
                name = keyValue[1];
            }
        }
        Log.d("REG", "email: " + email);
        Log.d("REG", "pass: " + password);
        Log.d("REG", "name: " + name);

        if (email != null && password != null && name != null) {
            checkUserExists(email, password, name);
            PreferenceConfig.setEmail(getApplicationContext(), email);
            PreferenceConfig.setPass(getApplicationContext(), password);
            PreferenceConfig.setName(getApplicationContext(), name);
        } else {
            Toast.makeText(this, "Invalid registration data", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserExists(String email, String password, String name) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser) {
                                // Користувач не зареєстрований, виконуємо реєстрацію
                                registerUser(email, password, name);
                            } else {
                                // Користувач вже зареєстрований, виконуємо спробу входу
                                loginUser(email, password);


                            }
                            Log.d("EMAIL", email);
                        } else {
                            Toast.makeText(AuthActivity.this, "Error checking user existence: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerUser(String email, String password, String name) {
        String db_email = email.trim();
        String db_pass = password.trim();
        firebaseAuth.createUserWithEmailAndPassword(db_email, db_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Registration successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                            overridePendingTransition(0,0);
                            finish();
                        } else {
                            loginUser(email, password);

                        }
                    }
                });
    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            webView.setVisibility(View.INVISIBLE);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Login successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                            overridePendingTransition(0,0);
                            finish();
                        } else {
                            Toast.makeText(AuthActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
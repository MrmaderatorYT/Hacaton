package com.example.hacaton.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;

import java.util.ArrayList;

public class PreferenceConfig {
    public static final String PREFERENCE = "com.example.hacaton";
    public static final String AMOUNT_OF_PASSED_COURSES = "amount of passed courses";
    public static final String AMOUNT_OF_CORRECT_COURSES = "amount of correct passed courses";
    public static final String EMAIL = "email";
    public static final String PASS = "password";

    public static final String COINS = "coins";
    public static final String NAME = "name";
    public static final String PURCHASED_ITEMS = "purchased_items"; // Ключ для збереження списку придбаних товарів
    public static void setAmountOfPassedCourses(Context context, int count) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(AMOUNT_OF_PASSED_COURSES, count);
        editor.apply();
    }

    public static int getAmountOfPassesCourses(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(AMOUNT_OF_PASSED_COURSES, 0);
    }

    public static void savePurchasedItems(Context context, ArrayList<String> purchasedItems) {
        saveArrayList(context, purchasedItems, PURCHASED_ITEMS);
    }

    // Метод для отримання списку придбаних товарів
    public static ArrayList<String> getPurchasedItems(Context context) {
        return getArrayList(context, PURCHASED_ITEMS);
    }
    public static void setCoins(Context context, int count) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(COINS, count);
        editor.apply();
    }

    public static int getACoins(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(COINS, 0);
    }
    public static void setEmail(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(EMAIL, data);
        editor.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getString(EMAIL, "");
    }
    public static void setPass(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PASS, data);
        editor.apply();
    }

    public static String getPass(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getString(PASS, "");
    }
    public static void setName(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NAME, data);
        editor.apply();
    }

    public static String getName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getString(NAME, "");
    }
    public static void setAmountOfCorrectCourses(Context context, int count) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(AMOUNT_OF_CORRECT_COURSES, count);
        editor.apply();
    }

    public static int getAmountOfCorrectCourses(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return pref.getInt(AMOUNT_OF_CORRECT_COURSES, 0);
    }
    public static void saveArrayList(Context context, ArrayList<String> list, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(key, json);
        editor.apply();
    }

    public static ArrayList<String> getArrayList(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}


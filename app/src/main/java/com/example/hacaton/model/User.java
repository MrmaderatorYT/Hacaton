package com.example.hacaton.model;

import java.util.ArrayList;

public class User {
    private int amountOfPassedCourses;
    private int amountOfCorrectPassedCourses;
    private int coins;
    private int photoId;
    private String name, email;
    private ArrayList<String> passedCourses; // Список пройдених курсів

    // Конструктор за замовчуванням (потрібен для Firebase)
    public User() {
    }

    // Конструктор з параметрами
    public User(int amountOfPassedCourses, int amountOfCorrectPassedCourses, int coins, int photoId, String name, ArrayList<String> passedCourses) {
        this.amountOfPassedCourses = amountOfPassedCourses;
        this.amountOfCorrectPassedCourses = amountOfCorrectPassedCourses;
        this.coins = coins;
        this.photoId = photoId;
        this.name = name;
        this.passedCourses = passedCourses;
    }

    // Гетери та сетери для всіх полів
    public int getAmountOfPassedCourses() {
        return amountOfPassedCourses;
    }

    public void setAmountOfPassedCourses(int amountOfPassedCourses) {
        this.amountOfPassedCourses = amountOfPassedCourses;
    }

    public int getAmountOfCorrectPassedCourses() {
        return amountOfCorrectPassedCourses;
    }

    public void setAmountOfCorrectPassedCourses(int amountOfCorrectPassedCourses) {
        this.amountOfCorrectPassedCourses = amountOfCorrectPassedCourses;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPassedCourses() {
        return passedCourses;
    }

    public void setPassedCourses(ArrayList<String> passedCourses) {
        this.passedCourses = passedCourses;
    }
}
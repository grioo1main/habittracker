package com.example.habittracker.model;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private Integer id; // НЕ static - уникально для каждого объекта
    private String password;
    private ArrayList<Habit> listHabbits;
    private static Integer nextId = 0; // static счётчик для генерации ID

    public User(String name, String email, String password, ArrayList<Habit> listHabbits) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.listHabbits = listHabbits;
        this.id = ++nextId; // Автоматическая генерация ID
    }

    public User() {
        this.listHabbits = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Habit> getListHabbits() {
        return listHabbits;
    }

    public void setListHabbits(ArrayList<Habit> listHabbits) {
        this.listHabbits = listHabbits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

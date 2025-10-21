package com.example.habittracker.repository;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;
import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
// import com.google.gson.stream.JsonReader;
// import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;

// import ch.qos.logback.classic.tyler.TylerConfiguratorBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.nio.Buffer;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct; 

@Repository
public class UserRepository {
    private List<User> listUsers;
    private final Gson gson;
    private String path;

    // Конструктор для инициализации
    public UserRepository() {
        this.listUsers = new ArrayList<>();
        this.gson = new Gson();
        this.path = "/media/mint1grio/Новый том/VsCodeSpringTest/habittracker/src/main/resources/UserRepository.json";
    }

    // Загрузка данных из файла при старте
    @PostConstruct
    public void init() {
        try {
            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка пользователей из файла
    private void loadFromFile() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                this.listUsers = loaded;
            }
        }
    }

    // Сохранение пользователей в файл
    public boolean saveToFile() throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(listUsers, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addHabit(User user, Habit habit) {
        try {
            if (user.getListHabbits() == null) {
                user.setListHabbits(new ArrayList<>());
            }
            user.getListHabbits().add(habit);
            saveToFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(User user) {
        try {
            listUsers.add(user);
            saveToFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<User> getAllUsers() {
        return listUsers;
    }
}
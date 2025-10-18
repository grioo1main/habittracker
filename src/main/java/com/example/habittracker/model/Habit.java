package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private Integer id;
    private String name;
    private String description;
    private String frequency; // "daily" или "weekly"
    private LocalDate createdAt;
    private List<LocalDate> completedDates;
    private static Integer nextId = 0; // Даты выполнения
    
    // Конструктор для создания новой привычки
    public Habit(String name, String description, String frequency) {
        this.id = ++nextId; // Генерируем уникальный ID
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createdAt = LocalDate.now();
        this.completedDates = new ArrayList<>();
    }
    
    // Пустой конструктор (для десериализации из JSON)
    public Habit() {
        this.completedDates = new ArrayList<>();
    }
    
    // Отметить выполнение на дату
    public void markComplete(LocalDate date) {
        if (!completedDates.contains(date)) {
            completedDates.add(date);
        }
    }
    
    // Проверить, выполнена ли привычка на дату
    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }
    
    // Посчитать текущую серию (streak)
    public int getCurrentStreak() {
        if (completedDates.isEmpty()) {
            return 0;
        }
        
        // Сортируем даты по убыванию
        List<LocalDate> sorted = new ArrayList<>(completedDates);
        sorted.sort((d1, d2) -> d2.compareTo(d1));
        
        int streak = 0;
        LocalDate checkDate = LocalDate.now();
        
        for (LocalDate date : sorted) {
            if (date.equals(checkDate) || date.equals(checkDate.minusDays(1))) {
                streak++;
                checkDate = date.minusDays(1);
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<LocalDate> getCompletedDates() {
        return completedDates;
    }
    
    public void setCompletedDates(List<LocalDate> completedDates) {
        this.completedDates = completedDates;
    }
    
    // Красивый вывод в консоль
    @Override
    public String toString() {
        return String.format("ID: %s | %s (%s) | Streak: %d дней", 
            id , name, frequency, getCurrentStreak());
    }
}

package com.example.habittracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Связь один-ко-многим с Habit
    // cascade = CascadeType.ALL - операции с User применяются к Habit
    // orphanRemoval = true - удаляет Habit, если удалили связь с User
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Habit> habits = new ArrayList<>();
    
    // Конструкторы
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
    
    // === Вспомогательные методы для управления связями ===
    
    /**
     * Добавляет привычку к пользователю
     * Устанавливает двустороннюю связь
     */
    public void addHabit(Habit habit) {
        habits.add(habit);
        habit.setUser(this);
    }
    
    /**
     * Удаляет привычку у пользователя
     * Разрывает двустороннюю связь
     */
    public void removeHabit(Habit habit) {
        habits.remove(habit);
        habit.setUser(null);
    }
    
    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Habit> getHabits() {
        return habits;
    }
    
    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", habitsCount=" + habits.size() +
                '}';
    }
}

package com.example.habittracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    // Enum хранится как строка в БД
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    // Храним даты выполнения в отдельной таблице
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "habit_completed_dates", joinColumns = @JoinColumn(name = "habit_id", foreignKey = @ForeignKey(name = "fk_habit_completed_dates_habit", foreignKeyDefinition = "FOREIGN KEY (habit_id) REFERENCES habits(id) ON DELETE CASCADE")))
    @Column(name = "completed_date")
    private List<LocalDate> completedDates = new ArrayList<>();

    // Связь многие-к-одному с User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Enum для частоты выполнения
    public enum Frequency {
        DAILY("Ежедневно"),
        WEEKLY("Еженедельно"),
        MONTHLY("Ежемесячно");

        private final String displayName;

        Frequency(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Конструкторы
    public Habit() {
        this.createdAt = LocalDate.now();
    }

    public Habit(String name, String description, Frequency frequency) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createdAt = LocalDate.now();
    }

    // === Бизнес-методы ===

    /**
     * Отметить выполнение привычки на конкретную дату
     */
    public void markComplete(LocalDate date) {
        if (!completedDates.contains(date)) {
            completedDates.add(date);
        }
    }

    /**
     * Проверить, выполнена ли привычка на дату
     */
    public boolean isCompletedOn(LocalDate date) {
        return completedDates.contains(date);
    }

    /**
     * Подсчитать текущую серию выполнений
     */
    public int getCurrentStreak() {
        if (completedDates.isEmpty()) {
            return 0;
        }

        List<LocalDate> sorted = new ArrayList<>(completedDates);
        sorted.sort((d1, d2) -> d2.compareTo(d1)); // По убыванию

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Серия прервана, если нет выполнения сегодня или вчера
        if (!sorted.contains(today) && !sorted.contains(yesterday)) {
            return 0;
        }

        int streak = 0;
        LocalDate checkDate = sorted.contains(today) ? today : yesterday;

        for (LocalDate date : sorted) {
            if (date.equals(checkDate)) {
                streak++;
                checkDate = checkDate.minusDays(1);
            } else if (date.isBefore(checkDate)) {
                break; // Пропуск в датах
            }
        }

        return streak;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Habit{id=%d, name='%s', frequency=%s, streak=%d}",
                id, name, frequency, getCurrentStreak());
    }
}

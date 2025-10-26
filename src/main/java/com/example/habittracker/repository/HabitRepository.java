package com.example.habittracker.repository;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Habit.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    
    /**
     * Найти все привычки пользователя
     */
    List<Habit> findByUserId(Long userId);
    
    /**
     * Найти привычки пользователя по частоте
     */
    List<Habit> findByUserIdAndFrequency(Long userId, Frequency frequency);
    
    /**
     * Найти привычки, созданные после определённой даты
     */
    List<Habit> findByCreatedAtAfter(LocalDate date);
}

package com.example.habittracker.service;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HabitService {
    
    private static final Logger logger = LoggerFactory.getLogger(HabitService.class);
    
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    
    public HabitService(UserRepository userRepository, HabitRepository habitRepository) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
    }
    
    /**
     * СОЗДАНИЕ привычки для пользователя
     * ИСПРАВЛЕНО: Теперь сохраняем habit напрямую и возвращаем объект с ID
     */
    public Habit createHabit(Long userId, String name, String description, Habit.Frequency frequency) {
        logger.info("Создание привычки '{}' для пользователя ID: {}", name, userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        
        Habit habit = new Habit(name, description, frequency);
        user.addHabit(habit); // Устанавливает двустороннюю связь
        
        // КЛЮЧЕВОЕ ИЗМЕНЕНИЕ: Сохраняем habit напрямую
        Habit savedHabit = habitRepository.save(habit);
        
        logger.info("Привычка создана с ID: {}", savedHabit.getId());
        return savedHabit; // Возвращаем сохранённый объект с ID
    }
    
    /**
     * ЧТЕНИЕ всех привычек пользователя
     */
    @Transactional(readOnly = true)
    public List<Habit> getUserHabits(Long userId) {
        logger.debug("Получение привычек пользователя ID: {}", userId);
        return habitRepository.findByUserId(userId);
    }
    
    /**
     * ОТМЕТИТЬ выполнение привычки
     */
    public void markHabitComplete(Long habitId, LocalDate date) {
        logger.info("Отметка выполнения привычки ID: {} на дату: {}", habitId, date);
        
        // Проверка на null ID
        if (habitId == null) {
            logger.error("Передан null ID для отметки привычки!");
            throw new IllegalArgumentException("ID привычки не может быть null");
        }
        
        Habit habit = habitRepository.findById(habitId)
            .orElseThrow(() -> new IllegalArgumentException("Привычка не найдена"));
        
        habit.markComplete(date);
        habitRepository.save(habit); // Сохраняет изменения
        
        logger.info("Привычка отмечена. Текущая серия: {} дней", habit.getCurrentStreak());
    }
    
    /**
     * УДАЛЕНИЕ привычки
     */
    public void deleteHabit(Long habitId) {
        logger.info("Удаление привычки ID: {}", habitId);
        
        if (!habitRepository.existsById(habitId)) {
            throw new IllegalArgumentException("Привычка не найдена");
        }
        
        habitRepository.deleteById(habitId);
        logger.info("Привычка удалена");
    }
}

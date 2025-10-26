package com.example.habittracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.Habit.Frequency;
import com.example.habittracker.model.User;
import com.example.habittracker.service.HabitService;
import com.example.habittracker.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);

    private final UserService userService;
    private final HabitService habitService;

    @Autowired
    public ApplicationStartupRunner(UserService userService, HabitService habitService) {
        this.userService = userService;
        this.habitService = habitService;
    }
    

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== Инициализация приложения ===");
        
        initializeTestData();
        logger.info("=== Приложение запущено успешно ===");
        demonstrateDatabaseOperations();
        
        
    }

    private void initializeTestData() {
        logger.info("Создание тестовых данных...");
        
        try {
            
            Habit habit1 = habitService.createHabit(1L, "Test1", null, Frequency.DAILY);

            
            habitService.markHabitComplete(habit1.getId(), LocalDate.now());
            habitService.markHabitComplete(habit1.getId(), LocalDate.now().minusDays(1));
            habitService.markHabitComplete(habit1.getId(), LocalDate.now().minusDays(2));
            logger.info("✓ Отмечены выполнения привычки '{}' за 3 дня {}", habit1.getName() , habit1.getCurrentStreak());
            
            logger.info("Тестовые данные созданы успешно!");
            
        } catch (IllegalArgumentException e) {
            logger.warn("Тестовые данные уже существуют: {}", e.getMessage());
        }
    }

    /**
     * ИСПРАВЛЕНО: Используем getAllUsersWithHabits() вместо getAllUsers()
     */
    private void demonstrateDatabaseOperations() {
        logger.info("\n=== Демонстрация операций с БД ===");
        
        // КЛЮЧЕВОЕ ИЗМЕНЕНИЕ: используем метод с EAGER загрузкой
        var allUsers = userService.getAllUsersWithHabits();
        logger.info("Всего пользователей в БД: {}", allUsers.size());
        
        // Теперь можно безопасно обращаться к habits
        allUsers.forEach(user -> 
            logger.info("  - {} ({}), привычек: {}", 
                user.getName(), 
                user.getEmail(), 
                user.getHabits().size())  // ← Больше не вызовет LazyInitializationException
        );
        
        if (!allUsers.isEmpty()) {
            User firstUser = allUsers.get(0);
            var habits = habitService.getUserHabits(firstUser.getId());
            logger.info("\nПривычки пользователя '{}':", firstUser.getName());
            habits.forEach(habit -> 
                logger.info("  - {} ({}), серия: {} дней", 
                    habit.getName(), 
                    habit.getFrequency().getDisplayName(), 
                    habit.getCurrentStreak())
            );
        }
    }
}

package com.example.habittracker.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.example.habittracker.model.User;
import com.example.habittracker.model.Habit;

class HabitServiceTest {
    
    private HabitService habitService = new HabitService();

    @Test
    void testAddHabit() {
        User user = new User();
        Habit habit = new Habit("Бег", "Бегать по утрам", "daily");
        
        String result = habitService.addHabit(user, habit);
        
        assertEquals("Привычка успешно добавленна !", result);
        assertEquals(1, user.getListHabbits().size());
    }

    @Test
    void testDeleteHabitById() {
        User user = new User();
        Habit habit = new Habit("Бег", "Бегать по утрам", "daily");
        habitService.addHabit(user, habit);
        
        String result = habitService.deleteHabitById(user, habit.getId());
        
        assertEquals("Успешно удаленно !", result);
        assertTrue(user.getListHabbits().isEmpty());
    }
}
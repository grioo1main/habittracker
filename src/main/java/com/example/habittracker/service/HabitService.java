package com.example.habittracker.service;

import java.util.Iterator;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;

class HabitService {

    public String addHabit(User user, Habit habit) {
        user.getListHabbits().add(habit);
        return "Привычка успешно добавленна !";
    }

    public String deleteHabitById(User user, Integer habitId) {
        if (user == null || user.getListHabbits() == null || habitId == null)
            return "Что-то пошло не так , повторите попытку.";

        try {
            Iterator<Habit> it = user.getListHabbits().iterator();
            while (it.hasNext()) {
                Habit h = it.next();
                if (habitId.equals(h.getId())) {
                    it.remove();
                    return "Успешно удаленно !";
                }
            }
            return "Что-то пошло не так , повторите попытку.";
        } catch (Exception e) {
            return "Убедитесь в корректности кода и повторите попытку.";

        }
    }

    public void printHabit(User user){
        for (Habit habbit : user.getListHabbits()){
            System.out.println(habbit.toString());
        }
    }


}
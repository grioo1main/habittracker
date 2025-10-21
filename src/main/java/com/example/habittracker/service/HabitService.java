package com.example.habittracker.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;

@Service
public class HabitService {

    public boolean addHabit(User user, Habit habit) {
        try {
            user.getListHabbits().add(habit);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteHabitById(User user, Integer habitId) {
        if (user == null || user.getListHabbits() == null || habitId == null)
            return false;

        try {
            Iterator<Habit> it = user.getListHabbits().iterator();
            while (it.hasNext()) {
                Habit h = it.next();
                if (habitId.equals(h.getId())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;

        }
    }

    public void printHabit(User user) {
        for (Habit habbit : user.getListHabbits()) {
            System.out.println(habbit.toString());
        }
    }

}
package com.example.habittracker.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

    // @Autowired
    // private UserRepository userRepository;

    // @Test
    // public void testAddAndSaveToFile() throws IOException {
    //     User user = new User("Mark", "eblan@mail.ru", "123");
        
    //     Habit habit = new Habit("study", "fast", "mnogo");
    //     boolean res2 = userRepository.addHabit(user, habit);
        
    //     boolean res3 = userRepository.addUser(user);
        
    //     assertTrue(res3);
    //     assertTrue(res2);
    // }
}

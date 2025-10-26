package com.example.habittracker.service;

import com.example.habittracker.model.User;
import com.example.habittracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // ... существующие методы ...
    
    /**
     * ЧТЕНИЕ всех пользователей БЕЗ привычек (легковесный запрос)
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.debug("Получение всех пользователей");
        return userRepository.findAll();
    }
    
    /**
     * ЧТЕНИЕ всех пользователей С привычками (тяжелый запрос)
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsersWithHabits() {
        logger.debug("Получение всех пользователей с привычками");
        return userRepository.findAllWithHabits();
    }
    
    /**
     * ЧТЕНИЕ пользователя по ID с привычками
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByIdWithHabits(Long id) {
        logger.debug("Получение пользователя ID: {} с привычками", id);
        return userRepository.findByIdWithHabits(id);
    }
    
    // Остальные методы остаются без изменений
    public User createUser(String name, String email, String password) {
        logger.info("Создание пользователя: {}", email);
        
        if (userRepository.existsByEmail(email)) {
            logger.warn("Пользователь с email {} уже существует", email);
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        
        User user = new User(name, email, password);
        User savedUser = userRepository.save(user);
        
        logger.info("Пользователь создан с ID: {}", savedUser.getId());
        return savedUser;
    }
    
    public Optional<User> getUserById(Long id) {
        logger.debug("Поиск пользователя по ID: {}", id);
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        logger.debug("Поиск пользователя по email: {}", email);
        return userRepository.findByEmail(email);
    }
    
    public User updateUser(Long id, String name, String email) {
        logger.info("Обновление пользователя ID: {}", id);
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        
        user.setName(name);
        user.setEmail(email);
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        logger.info("Удаление пользователя ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        
        userRepository.deleteById(id);
        logger.info("Пользователь удалён");
    }
}

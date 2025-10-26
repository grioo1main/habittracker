package com.example.habittracker.repository;

import com.example.habittracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    /**
     * Загружает всех пользователей с привычками (EAGER)
     * JOIN FETCH принудительно загружает связанную коллекцию
     */
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.habits")
    List<User> findAllWithHabits();
    
    /**
     * Загружает пользователя по ID с привычками
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.habits WHERE u.id = :id")
    Optional<User> findByIdWithHabits(Long id);
}

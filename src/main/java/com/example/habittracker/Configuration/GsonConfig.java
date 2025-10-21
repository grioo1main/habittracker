package com.example.habittracker.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                // LocalDate будет сохраняться как "2024-01-15"
                .registerTypeAdapter(LocalDate.class, 
                    (com.google.gson.JsonSerializer<LocalDate>) (date, type, context) -> 
                        new com.google.gson.JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class, 
                    (com.google.gson.JsonDeserializer<LocalDate>) (json, type, context) -> 
                        LocalDate.parse(json.getAsString()))
                .create();
    }
}
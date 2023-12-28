package com.gaonkar.anuj.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@Tag(name = "Test", description = "Test Controller for Redis")
public class HelloRedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String STRING_KEY_PREFIX = "bookStore:strings:";

    @PostMapping("/strings")
    @ResponseStatus(HttpStatus.CREATED)
    public Map.Entry<String, String> setString(@RequestBody Map.Entry<String, String> kvp){
        redisTemplate.opsForValue().set(STRING_KEY_PREFIX + kvp.getKey(), kvp.getValue());
        return kvp;
    }

    @GetMapping("/strings/{key}")
    public Map.Entry<String, String> getString(@PathVariable("key") String key) {
        String value = redisTemplate.opsForValue().get(STRING_KEY_PREFIX + key);
        if (value == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Key not found");

        return new AbstractMap.SimpleEntry<String, String>(key, value);
    }

}

package com.gaonkar.anuj.bookstore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@Builder
public class Role {

    @Id
    private String id;
    private String name;

}

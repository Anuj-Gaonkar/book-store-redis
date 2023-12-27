package com.gaonkar.anuj.bookstore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
@Data
@Builder
public class Role {

    @Id
    private String id;
    @Indexed
    private String name;

}

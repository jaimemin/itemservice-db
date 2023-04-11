package com.tistory.jaimemin.itemservice.config;

import com.tistory.jaimemin.itemservice.repository.ItemRepository;
import com.tistory.jaimemin.itemservice.repository.jpa.JpaItemRepository;
import com.tistory.jaimemin.itemservice.repository.mybatis.ItemMapper;
import com.tistory.jaimemin.itemservice.repository.mybatis.MyBatisItemRepository;
import com.tistory.jaimemin.itemservice.service.ItemService;
import com.tistory.jaimemin.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager entityManager;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(entityManager);
    }
}

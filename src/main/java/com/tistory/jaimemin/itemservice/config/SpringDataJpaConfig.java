package com.tistory.jaimemin.itemservice.config;

import com.tistory.jaimemin.itemservice.repository.ItemRepository;
import com.tistory.jaimemin.itemservice.repository.jpa.JpaItemRepository;
import com.tistory.jaimemin.itemservice.repository.jpa.JpaItemRepositoryV2;
import com.tistory.jaimemin.itemservice.repository.jpa.SpringDataJpaItemRepository;
import com.tistory.jaimemin.itemservice.service.ItemService;
import com.tistory.jaimemin.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository springDataJpaItemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(springDataJpaItemRepository);
    }
}

package com.tistory.jaimemin.itemservice.config;

import com.tistory.jaimemin.itemservice.repository.ItemRepository;
import com.tistory.jaimemin.itemservice.repository.jpa.JpaItemRepositoryV2;
import com.tistory.jaimemin.itemservice.repository.jpa.JpaItemRepositoryV3;
import com.tistory.jaimemin.itemservice.repository.jpa.SpringDataJpaItemRepository;
import com.tistory.jaimemin.itemservice.repository.v2.ItemQueryRepositoryV2;
import com.tistory.jaimemin.itemservice.repository.v2.ItemRepositoryV2;
import com.tistory.jaimemin.itemservice.service.ItemService;
import com.tistory.jaimemin.itemservice.service.ItemServiceV1;
import com.tistory.jaimemin.itemservice.service.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager entityManager;

    private final ItemRepositoryV2 itemRepositoryV2;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
        return new ItemQueryRepositoryV2(entityManager);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(entityManager);
    }
}

package com.tistory.jaimemin.itemservice.config;

import com.tistory.jaimemin.itemservice.repository.ItemRepository;
import com.tistory.jaimemin.itemservice.repository.mybatis.ItemMapper;
import com.tistory.jaimemin.itemservice.repository.mybatis.MyBatisItemRepository;
import com.tistory.jaimemin.itemservice.service.ItemService;
import com.tistory.jaimemin.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }
}

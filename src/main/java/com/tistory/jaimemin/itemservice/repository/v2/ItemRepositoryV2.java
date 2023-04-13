package com.tistory.jaimemin.itemservice.repository.v2;

import com.tistory.jaimemin.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {
}

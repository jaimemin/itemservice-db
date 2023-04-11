package com.tistory.jaimemin.itemservice.repository.mybatis;

import com.tistory.jaimemin.itemservice.domain.Item;
import com.tistory.jaimemin.itemservice.repository.ItemSearchCond;
import com.tistory.jaimemin.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);
}

package com.tistory.jaimemin.itemservice.repository.jdbctemplate;

import com.tistory.jaimemin.itemservice.domain.Item;
import com.tistory.jaimemin.itemservice.repository.ItemRepository;
import com.tistory.jaimemin.itemservice.repository.ItemSearchCond;
import com.tistory.jaimemin.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcTemplateItemRepositoryV1 implements ItemRepository {

    private final JdbcTemplate template;

    public JdbcTemplateItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "INSERT INTO item(item_name, price, quantity) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            // 자동 증가 키
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getQuantity());

            return statement;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);

        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "UPDATE item SET item_name=?, price=?, quantity=? WHERE id=?";
        template.update(sql
                , updateParam.getItemName()
                , updateParam.getPrice()
                , updateParam.getQuantity()
                , itemId);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "SELECT id, item_name, price, quantity FROM item WHERE id = ?";

        try {
            Item item = template.queryForObject(sql, itemRowMapper(), id);

            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            log.error("[findById] ERROR ", e);

            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        String sql = "SELECT id, item_name, price, quantity FROM item";
        // 동적 쿼리
        if (StringUtils.hasText(itemName) || !ObjectUtils.isEmpty(maxPrice)) {
            sql += " WHERE";
        }

        boolean andFlag = false;
        List<Object> param = new ArrayList<>();

        if (StringUtils.hasText(itemName)) {
            sql += " item_name LIKE CONCAT('%', ?, '%')";
            param.add(itemName);

            andFlag = true;
        }

        if (!ObjectUtils.isEmpty(maxPrice)) {
            if (andFlag) {
                sql += " AND";
            }

            sql += " price <= ?";
            param.add(maxPrice);
        }

        log.info("동적 쿼리={}", sql);

        return template.query(sql, itemRowMapper(), param.toArray());
    }

    private RowMapper<Item> itemRowMapper() {
        return ((rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));

            return item;
        });
    }

}

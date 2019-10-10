package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.models.Inventory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryDaoJdbcTemplateImpl implements InventoryDao {

    private JdbcTemplate jdbcTemplate;

    public InventoryDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    //prepared statements
    private final static String INSERT_INVENTORY =
            "insert into inventory (product_id, quantity) values (?,?)";
    private final static String SELECT_INVENTORY =
            "select * from inventory where inventory_id = ?";
    private final static String SELECT_INVENTORY_BY_PRODUCT =
            "select * from inventory where product_id = ?";
    private final static String SELECT_ALL_INVENTORIES =
            "select * from inventory";
    private final static String UPDATE_INVENTORY =
            "update inventory set product_id=?, quantity=? where inventory_id = ?";
    private final static String DELETE_INVENTORY =
            "delete from inventory where inventory_id=?";
    private final static String DELETE_INVENTORY_BY_PRODUCT =
            "delete from inventory where product_id=?";

    private Inventory mapRowToInventory (ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getInt("inventory_id"));
        inventory.setProductId(rs.getInt("product_id"));
        inventory.setQuantity(rs.getInt("quantity"));

        return inventory;
    }

    @Override
    public Inventory addProductToInventory(Inventory newInventory) {
        jdbcTemplate.update(
                INSERT_INVENTORY,
                newInventory.getProductId(),
                newInventory.getQuantity()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        newInventory.setInventoryId(id);

        return newInventory;
    }

    @Override
    public Inventory getInventory(int inventoryId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVENTORY, this::mapRowToInventory, inventoryId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }    }

    @Override
    public List<Inventory> getInventoriesByProduct(int productId) {
        return jdbcTemplate.query(SELECT_INVENTORY_BY_PRODUCT, this::mapRowToInventory, productId);

    }

    @Override
    public List<Inventory> getAllInventories() {
        return jdbcTemplate.query(SELECT_ALL_INVENTORIES, this::mapRowToInventory);
    }

    @Override
    public void updateInventory(Inventory updateInventory) {
        jdbcTemplate.update(
                UPDATE_INVENTORY,
                updateInventory.getProductId(),
                updateInventory.getQuantity(),
                updateInventory.getInventoryId()
        );
    }

    @Override
    public void deleteInventory(int inventoryId) {
        jdbcTemplate.update(DELETE_INVENTORY, inventoryId);
    }

    @Override
    public void deleteInventoryByProduct(int productId) {
        jdbcTemplate.update(DELETE_INVENTORY_BY_PRODUCT, productId);
    }
}

package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDaoJdbcTemplateImpl implements ProductDao {

    //to create
    public static final String INSERT_PRODUCT_SQL =
            "insert into product(product_name, product_description, list_price, unit_cost) values (?, ?, ?, ?)";
    //to read
    public static final String SELECT_PRODUCT_SQL =
            "select * from product where product_id = ?";
    //to read all
    public static final String SELECT_ALL_PRODUCTS_SQL =
            "select * from product";
    //to update
    public static final String UPDATE_CUSTOMER_SQL =
            "update product set product_name = ?, product_description = ?, list_price = ?, unit_cost = ? where product_id = ?";
    //to delete
    public static final String DELETE_CUSTOMER_SQL =
            "delete from product where product_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Product mapRowToProduct(ResultSet rs, int numRow) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setListPrice(rs.getBigDecimal("list_price"));
        product.setUnitCost(rs.getBigDecimal("unit_cost"));
        return product;
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        jdbcTemplate.update(
                INSERT_PRODUCT_SQL,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost());
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        product.setProductId(id);

        return product;
    }

    @Override
    public Product getProduct(int productId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_PRODUCT_SQL, this::mapRowToProduct, productId);
        } catch (EmptyResultDataAccessException e) {
            // if there is no match for this product, return null
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS_SQL, this::mapRowToProduct);
    }

    @Override
    public void updateProduct(Product product) {
        jdbcTemplate.update(
                UPDATE_CUSTOMER_SQL,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost(),
                product.getProductId());
    }

    @Override
    public void deleteProduct(int productId) {
        jdbcTemplate.update(DELETE_CUSTOMER_SQL, productId);
    }
}










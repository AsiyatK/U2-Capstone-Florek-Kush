package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoJdbcTemplateImplTest {

    @Autowired
    protected ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        List<Product> products = productDao.getAllProducts();
        products.forEach(product -> productDao.deleteProduct(product.getProductId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addGetDeleteProduct() {

        //Arrange
        Product product = new Product();
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal("15.99"));
        product.setUnitCost(new BigDecimal("8.00"));

        //Act
        product = productDao.addProduct(product);
        Product product1 = productDao.getProduct(product.getProductId());

        //Assert
        assertEquals(product, product1);

        productDao.deleteProduct(product.getProductId());
        product1 = productDao.getProduct(product.getProductId());
        assertNull(product1);
    }



    @Test
    public void getAllProducts() {

        Product product = new Product();
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal(15.99));
        product.setUnitCost(new BigDecimal(8.00));
        productDao.addProduct(product);

        product = new Product();
        product.setProductName("Game");
        product.setProductDescription("Some Game");
        product.setListPrice(new BigDecimal(33.75));
        product.setUnitCost(new BigDecimal(19.95));
        productDao.addProduct(product);

        List<Product> products = productDao.getAllProducts();
        assertEquals(products.size(), 2);
    }

    @Test
    public void updateProduct() {

        Product product = new Product();
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal(15.99));
        product.setUnitCost(new BigDecimal(8.00));
        productDao.addProduct(product);

        product.setProductName("tshirt");
        product.setProductDescription("Cotton white tshirt with blue flowers");
        product.setListPrice(new BigDecimal("17.95"));
        product.setUnitCost(new BigDecimal("9.30"));
        productDao.updateProduct(product);

        Product  product1 = productDao.getProduct(product.getProductId());

        assertEquals(product, product1);
    }


}

























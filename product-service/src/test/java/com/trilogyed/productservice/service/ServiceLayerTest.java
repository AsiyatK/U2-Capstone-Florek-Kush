package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.dao.ProductDaoJdbcTemplateImpl;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.model.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;
    ProductDao productDao;


    @Before
    public void setUp() throws Exception {
        setUpProductDaoMock();
        service = new ServiceLayer(productDao);
    }

    @Test
    public void saveFindFindAllProduct() {

//        ProductViewModel productVM = new ProductViewModel();
//        productVM.setProductId(45);
//        productVM.setProductName("Tshirt");
//        productVM.setProductDescription("Blue tshirt with white stripes");
//        productVM.setListPrice(new BigDecimal("15.99"));
//        productVM.setUnitCost(new BigDecimal("8.00"));


        Product product = new Product();
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal(15.99));
        product.setUnitCost(new BigDecimal(8.00));

        ProductViewModel productVM = service.saveProduct(product); //<-- save

        ProductViewModel fromService = service.findProductById(productVM.getProductId()); //<-- find
        assertEquals(productVM, fromService);

        List<ProductViewModel> products = service.findAllProducts();
        assertEquals(products.size(), 1);
        assertEquals(productVM, products.get(0));

    }

    @Test
    public void updateProduct() {
        Product productUpdate = new Product();
        productUpdate.setProductId(1);
        productUpdate.setProductName("Updated");
        productUpdate.setProductDescription("Blue tshirt updated");
        productUpdate.setListPrice(new BigDecimal(10.99));
        productUpdate.setUnitCost(new BigDecimal(9.00));




        service.updateProduct(productUpdate);

        Product product = productDao.getProduct(1);
        assertEquals(product, productUpdate);
    }

    @Test
    public void removeProduct() {
        service.removeProduct(3);
        ProductViewModel productVM = service.findProductById(3);
        assertNull(productVM);
    }


    public void setUpProductDaoMock(){

        productDao = mock(ProductDaoJdbcTemplateImpl.class);

        //mock the add
        Product product = new Product();
        product.setProductId(45);
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal(15.99));
        product.setUnitCost(new BigDecimal(8.00));

        Product product2 = new Product();
        product2.setProductName("Tshirt");
        product2.setProductDescription("Blue tshirt with white stripes");
        product2.setListPrice(new BigDecimal(15.99));
        product2.setUnitCost(new BigDecimal(8.00));

        doReturn(product).when(productDao).addProduct(product2);

        //mock the get product by productId
        doReturn(product).when(productDao).getProduct(45);

        //mock get all
        List<Product> products = new ArrayList<>();
        products.add(product);
        doReturn(products).when(productDao).getAllProducts();


        //mock the update
        Product productUpdate = new Product();
        productUpdate.setProductId(1);
        productUpdate.setProductName("Updated");
        productUpdate.setProductDescription("Blue tshirt updated");
        productUpdate.setListPrice(new BigDecimal(10.99));
        productUpdate.setUnitCost(new BigDecimal(9.00));

        doNothing().when(productDao).updateProduct(productUpdate);
        doReturn(productUpdate).when(productDao).getProduct(1);

        //mock the delete

        Product productDelete = new Product();
        productDelete.setProductId(3);
        productDelete.setProductName("For deletion");
        productDelete.setProductDescription("Blue tshirt to be deleted");
        productDelete.setListPrice(new BigDecimal(20.99));
        productDelete.setUnitCost(new BigDecimal(5.00));

        doNothing().when(productDao).deleteProduct(3);
        doReturn(null).when(productDao).getProduct(3);

    }
}






















































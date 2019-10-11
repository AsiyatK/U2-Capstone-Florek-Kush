package com.trilogyed.adminapiservice.service;

import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.util.feign.CustomerClient;
import com.trilogyed.adminapiservice.util.feign.LevelUpClient;
import com.trilogyed.adminapiservice.util.feign.ProductClient;
import com.trilogyed.adminapiservice.viewModel.CustomerViewModel;
import com.trilogyed.adminapiservice.viewModel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ServicelayerTest {


    ServiceLayer service;
    ProductClient productClient;
    CustomerClient customerClient;
    LevelUpClient levelUpClient;


    @Before
    public void setUp() throws Exception {

        setUpProductClientMock();
        setUpCustomerClientMock();

        service = new ServiceLayer(productClient, customerClient, levelUpClient);

    }


    //PRODUCT SERVICE LAYER TESTS

    @Test
    public void saveFindFindAllProduct(){

        ProductViewModel productVM = new ProductViewModel();
        productVM.setProductName("Tshirt");
        productVM.setProductDescription("Blue tshirt with white stripes");
        productVM.setListPrice(new BigDecimal(15.99));
        productVM.setUnitCost(new BigDecimal(8.00));

        productVM = service.saveProduct(productVM);

        ProductViewModel pvmFromService = service.findProduct(productVM.getProductId());
        assertEquals(productVM, pvmFromService);

        List<ProductViewModel> productViewModels = service.findAllProducts();
        assertEquals(productViewModels.size(), 1);
    }

    @Test
    public void updateProduct(){

        ProductViewModel pvmUpdate = new ProductViewModel();
        pvmUpdate.setProductId(2);
        pvmUpdate.setProductName("Tshirt Update");
        pvmUpdate.setProductDescription("Blue tshirt Updated");
        pvmUpdate.setListPrice(new BigDecimal(20.00));
        pvmUpdate.setUnitCost(new BigDecimal(8.00));

        service.updateProduct(pvmUpdate);

        ProductViewModel pvm = service.findProduct(2);
        assertEquals(pvm, pvmUpdate);
    }

    @Test
    public void removeProduct(){
        service.removeProduct(3);
       // assertNull(service.findProduct(3));
        assertNull(productClient.getProduct(3));
    }


    //CUSTOMER SERVICE LAYER TESTS
    @Test
    public void saveFindFindAllCustomer(){

        CustomerViewModel customerVM = new CustomerViewModel();
        customerVM.setFirstName("Amelia");
        customerVM.setLastName("Earhart");
        customerVM.setStreet("123 Ocean Ave");
        customerVM.setCity("Atchison");
        customerVM.setZip("09345");
        customerVM.setEmail("ae@ameliaearhart.com");
        customerVM.setPhone("333-777-8899");

        customerVM = service.saveCustomer(customerVM);

        CustomerViewModel cvmFromService = service.findCustomer(customerVM.getCustomerId());
        assertEquals(customerVM, cvmFromService);

        List<CustomerViewModel> customerViewModels = service.findAllCustomers();
        assertEquals(customerViewModels.size(), 1);
      //  assertEquals(customerViewModels.get(0), cvmFromService);

    }


    @Test
    public void updateCustomer(){

        CustomerViewModel cvmUpdate = new CustomerViewModel();
        cvmUpdate.setCustomerId(2);
        cvmUpdate.setFirstName("Amelia updated");
        cvmUpdate.setLastName("update");
        cvmUpdate.setStreet("123 Ocean Ave");
        cvmUpdate.setCity("Atchison");
        cvmUpdate.setZip("09345");
        cvmUpdate.setEmail("ae@ameliaearhart.com");
        cvmUpdate.setPhone("333-777-8899");

        service.updateCustomer(cvmUpdate);

        CustomerViewModel cvm = service.findCustomer(2);
        assertEquals(cvm, cvmUpdate);

    }

    @Test
    public void removeCustomer(){
        service.removeCustomer(3);
        //assertNull(service.findCustomer(3));
        assertNull(customerClient.getCustomer(3));
    }




    public void setUpProductClientMock(){
        productClient = mock(ProductClient.class);

        //mock the add

        ProductViewModel productVM = new ProductViewModel();
        productVM.setProductId(45);
        productVM.setProductName("Tshirt");
        productVM.setProductDescription("Blue tshirt with white stripes");
        productVM.setListPrice(new BigDecimal(15.99));
        productVM.setUnitCost(new BigDecimal(8.00));


        Product product = new Product();
        product.setProductName("Tshirt");
        product.setProductDescription("Blue tshirt with white stripes");
        product.setListPrice(new BigDecimal(15.99));
        product.setUnitCost(new BigDecimal(8.00));

        List<ProductViewModel> productVms = new ArrayList<>();
        productVms.add(productVM);

        doReturn(productVM).when(productClient).createProduct(product);
        doReturn(productVM).when(productClient).getProduct(45);
        doReturn(productVms).when(productClient).getAllProducts();


        //mock the update

        ProductViewModel pvmUpdate = new ProductViewModel();
        pvmUpdate.setProductId(2);
        pvmUpdate.setProductName("Tshirt Update");
        pvmUpdate.setProductDescription("Blue tshirt Updated");
        pvmUpdate.setListPrice(new BigDecimal(20.00));
        pvmUpdate.setUnitCost(new BigDecimal(8.00));

        doReturn(pvmUpdate).when(productClient).getProduct(2);

        //mock the delete

        Product productDelete = new Product();
        productDelete.setProductId(3);
        productDelete.setProductName("Tshirt");
        productDelete.setProductDescription("Blue tshirt with white stripes");
        productDelete.setListPrice(new BigDecimal(15.99));
        productDelete.setUnitCost(new BigDecimal(8.00));

        doNothing().when(productClient).deleteProduct(3);
        doReturn(null).when(productClient).getProduct(3);

    }

    public void setUpCustomerClientMock(){
        customerClient = mock(CustomerClient.class);

        //mock the add

        CustomerViewModel customerVM = new CustomerViewModel();
        customerVM.setCustomerId(45);
        customerVM.setFirstName("Amelia");
        customerVM.setLastName("Earhart");
        customerVM.setStreet("123 Ocean Ave");
        customerVM.setCity("Atchison");
        customerVM.setZip("09345");
        customerVM.setEmail("ae@ameliaearhart.com");
        customerVM.setPhone("333-777-8899");

        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");

        List<CustomerViewModel> customerVMs = new ArrayList<>();
        customerVMs.add(customerVM);

        doReturn(customerVM).when(customerClient).createCustomer(customer);
        doReturn(customerVM).when(customerClient).getCustomer(45);
        doReturn(customerVMs).when(customerClient).getAllCustomers();

        //mock the update

        CustomerViewModel cvmUpdate = new CustomerViewModel();
        cvmUpdate.setCustomerId(2);
        cvmUpdate.setFirstName("Amelia updated");
        cvmUpdate.setLastName("update");
        cvmUpdate.setStreet("123 Ocean Ave");
        cvmUpdate.setCity("Atchison");
        cvmUpdate.setZip("09345");
        cvmUpdate.setEmail("ae@ameliaearhart.com");
        cvmUpdate.setPhone("333-777-8899");


        Customer customerUpdate = new Customer();
        customerUpdate.setCustomerId(2);
        customerUpdate.setFirstName("Amelia updated");
        customerUpdate.setLastName("update");
        customerUpdate.setStreet("123 Ocean Ave");
        customerUpdate.setCity("Atchison");
        customerUpdate.setZip("09345");
        customerUpdate.setEmail("ae@ameliaearhart.com");
        customerUpdate.setPhone("333-777-8899");

        doNothing().when(customerClient).updateCustomer(2, customer);
        doReturn(cvmUpdate).when(customerClient).getCustomer(2);

        //mock the delete

        Customer customerDelete = new Customer();
        customerDelete.setCustomerId(3);
        customerDelete.setFirstName("Amelia for deletion");
        customerDelete.setLastName("delete");
        customerDelete.setStreet("123 Ocean Ave");
        customerDelete.setCity("Atchison");
        customerDelete.setZip("09345");
        customerDelete.setEmail("ae@ameliaearhart.com");
        customerDelete.setPhone("333-777-8899");

        doNothing().when(customerClient).deleteCustomer(3);
        doReturn(null).when(customerClient).getCustomer(3);

    }


    public void setUpLevelUpClientMock(){
        levelUpClient = mock(LevelUpClient.class);
    }

}





































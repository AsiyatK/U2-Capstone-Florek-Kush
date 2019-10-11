package com.trilogyed.adminapiservice.service;

import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.util.feign.CustomerClient;
import com.trilogyed.adminapiservice.util.feign.LevelUpClient;
import com.trilogyed.adminapiservice.util.feign.ProductClient;
import com.trilogyed.adminapiservice.viewModel.CustomerViewModel;
import com.trilogyed.adminapiservice.viewModel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceLayer {
    private ProductClient productClient;
    private CustomerClient customerClient;
    private LevelUpClient levelUpClient;


    @Autowired
    public ServiceLayer(ProductClient productClient, CustomerClient customerClient, LevelUpClient levelUpClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.levelUpClient = levelUpClient;
    }

    //PRODUCT API

    @Transactional
    public ProductViewModel saveProduct(ProductViewModel productVM){

        Product product = new Product();
        product.setProductName(productVM.getProductName());
        product.setProductDescription(productVM.getProductDescription());
        product.setListPrice(productVM.getListPrice());
        product.setUnitCost(productVM.getUnitCost());

        productVM = productClient.createProduct(product);

        //productVM.setProductId(product.getProductId());

        return productVM;
    }

    public ProductViewModel findProduct(int productId){

        ProductViewModel productVm = productClient.getProduct(productId);

        if(productVm == null){
            throw new IllegalArgumentException("Product with the id " + productId + " could not be found");
        }

        return productVm;
    }

    public List<ProductViewModel> findAllProducts(){
        return productClient.getAllProducts();
    }

    @Transactional
    public void updateProduct(ProductViewModel productVm){

        Product product = new Product();
        product.setProductId(productVm.getProductId());
        product.setProductName(productVm.getProductName());
        product.setProductDescription(productVm.getProductDescription());
        product.setUnitCost(productVm.getUnitCost());
        product.setListPrice(productVm.getUnitCost());

        productClient.updateProduct(product.getProductId(), product);
    }

    @Transactional
    public void removeProduct(int productId){
        productClient.deleteProduct(productId);
    }

    //CUSTORMER API


        /*
        private int customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;
         */


    @Transactional
    public CustomerViewModel saveCustomer(CustomerViewModel customerVM){

        Customer customer = new Customer();
        customer.setFirstName(customerVM.getFirstName());
        customer.setLastName(customerVM.getLastName());
        customer.setStreet(customerVM.getStreet());
        customer.setCity(customerVM.getCity());
        customer.setZip(customerVM.getZip());
        customer.setEmail(customerVM.getEmail());
        customer.setPhone(customerVM.getPhone());

        customerVM = customerClient.createCustomer(customer);

        return customerVM;
    }

    public CustomerViewModel findCustomer(int customerId){
        CustomerViewModel customerVM = customerClient.getCustomer(customerId);

        if(customerVM == null){
            throw new IllegalArgumentException("Customer with the id " + customerId + " not found");
        }

        return customerVM;
    }

    public List<CustomerViewModel> findAllCustomers(){
        return customerClient.getAllCustomers();
    }

    @Transactional
    public void updateCustomer(CustomerViewModel customerVM){

        Customer customer = new Customer();
        customer.setFirstName(customerVM.getFirstName());
        customer.setLastName(customerVM.getLastName());
        customer.setStreet(customerVM.getStreet());
        customer.setCity(customerVM.getCity());
        customer.setZip(customerVM.getZip());
        customer.setEmail(customerVM.getEmail());
        customer.setPhone(customerVM.getPhone());

        customerClient.updateCustomer(customer.getCustomerId(), customer);

    }

    @Transactional
    public void removeCustomer(int customerId){
        customerClient.deleteCustomer(customerId);
    }

}













































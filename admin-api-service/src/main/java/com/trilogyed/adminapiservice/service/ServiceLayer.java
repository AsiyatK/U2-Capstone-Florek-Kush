package com.trilogyed.adminapiservice.service;

import com.trilogyed.adminapiservice.exception.NotFoundException;
import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.util.feign.CustomerClient;
import com.trilogyed.adminapiservice.util.feign.LevelUpClient;
import com.trilogyed.adminapiservice.util.feign.ProductClient;
import com.trilogyed.adminapiservice.viewModel.CustomerViewModel;
import com.trilogyed.adminapiservice.viewModel.LevelUpViewModel;
import com.trilogyed.adminapiservice.viewModel.ProductViewModel;
import feign.FeignException;
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
        try {
            return customerClient.getCustomer(customerId);

        } catch(FeignException.NotFound e){
            throw new NotFoundException("Customer with the id " + customerId + " not found" + e.getClass());

        } catch(FeignException e){
            throw new NotFoundException("!!!! " + e.getClass());
        }
    }

    public List<CustomerViewModel> findAllCustomers(){
        return customerClient.getAllCustomers();
    }

    @Transactional
    public void updateCustomer(CustomerViewModel customerVM){

        Customer customer = new Customer();
        customer.setCustomerId(customerVM.getCustomerId());
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


    //LEVEL-UP API

    public LevelUpViewModel saveAccount(LevelUpViewModel lvm){

       lvm = levelUpClient.createAccount(lvm);

        System.out.println("!!!!");

       return lvm;
    }

    public LevelUpViewModel findAcount(int levelUpId){
        LevelUpViewModel lvm = levelUpClient.getAccount(levelUpId);
        if(lvm == null){
            throw new NotFoundException("The account with id " + levelUpId + "could not be found");

        }
        return lvm;
    }

    public LevelUpViewModel findAccountByCustomer(int customerId){
        LevelUpViewModel lvm = levelUpClient.getAccountByCustomer(customerId);
        if(lvm == null){
            throw new NotFoundException("The account with the customer id  " + customerId + " not found");
        }
        return lvm;
    }

    public List<LevelUpViewModel> findAllAccounts(){
        return levelUpClient.getAllAccounts();
    }

    public void updateAccount(int levelUpId, LevelUpViewModel levelUpViewModel){
        levelUpClient.updateAccount(levelUpId, levelUpViewModel);
    }

    public void deleteAccount(int levelUpId){
        levelUpClient.deleteAccount(levelUpId);
    }

    public void deleteByCustomer(int customerId){
        levelUpClient.getAccountByCustomer(customerId);
    }

}













































package com.trilogyed.adminapiservice.service;

import com.trilogyed.adminapiservice.exception.NotFoundException;
import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.util.feign.*;
import com.trilogyed.adminapiservice.viewModel.*;
import feign.FeignException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServicelayerTest {


    ServiceLayer service;
    ProductClient productClient;
    CustomerClient customerClient;
    LevelUpClient levelUpClient;
    InventoryClient inventoryClient;
    InvoiceClient invoiceClient;


    @Before
    public void setUp() throws Exception {

        setUpProductClientMock();
        setUpCustomerClientMock();
        setUpLevelUpClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();

        service = new ServiceLayer(productClient, customerClient, levelUpClient, inventoryClient, invoiceClient);

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

    //@Test(expected = IllegalArgumentException.class)
    @Test /*(expected = NotFoundException.class)*/
    public void removeProduct(){
        service.removeProduct(3);
        assertNull(service.findProduct(3));

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
        assertEquals(customerViewModels.get(0), cvmFromService);

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

    @Test/*(expected = IllegalArgumentException.class)*/

    //asserts that this will throw an illegal argument exception while trying to get an customer that does not exist.
    public void removeCustomer(){
        service.removeCustomer(3);
        assertNull(service.findCustomer(3));


    }


    //LEVEL UP SERVICE LAYER TESTS

    @Test
    public void saveFindFindAccountByCustomer(){
        LevelUpViewModel accountToAdd = new LevelUpViewModel();
        accountToAdd.setCustomerId(732);
        accountToAdd.setPoints(5000);
        accountToAdd.setMemberDate(LocalDate.of(2010, 8, 2));

        accountToAdd = service.saveAccount(accountToAdd);

        LevelUpViewModel accountCreated = service.findAcount(accountToAdd.getLevelUpId());

        assertEquals(accountToAdd, accountCreated);

        LevelUpViewModel accountByCustomer = service.findAccountByCustomer(accountToAdd.getCustomerId());

        assertEquals(accountToAdd, accountByCustomer);
    }

    @Test
    public void findAllAccounts(){
        List<LevelUpViewModel> allAccounts = new ArrayList<>();

        LevelUpViewModel account1 = new LevelUpViewModel();
        account1.setLevelUpId(732);
        account1.setCustomerId(732);
        account1.setPoints(5000);
        account1.setMemberDate(LocalDate.of(2010, 8, 2));

        LevelUpViewModel account2 = new LevelUpViewModel();
        account2.setLevelUpId(908);
        account2.setCustomerId(908);
        account2.setPoints(2000);
        account2.setMemberDate(LocalDate.of(2012, 7, 19));

        LevelUpViewModel account3 = new LevelUpViewModel();
        account3.setLevelUpId(973);
        account3.setCustomerId(973);
        account3.setPoints(3000);
        account3.setMemberDate(LocalDate.of(2019, 6, 25));

        allAccounts.add(account1);
        allAccounts.add(account2);
        allAccounts.add(account3);

        List<LevelUpViewModel> accountsFromService = service.findAllAccounts();

        assertEquals(accountsFromService.size(), allAccounts.size());

        assertEquals(allAccounts, accountsFromService);
    }

    @Test
    public void updateAccount(){
        LevelUpViewModel accountUpdate = new LevelUpViewModel();
        accountUpdate.setLevelUpId(908);
        accountUpdate.setCustomerId(908);
        accountUpdate.setPoints(1000);
        accountUpdate.setMemberDate(LocalDate.of(2012, 7, 19));

        accountUpdate.setPoints(2000);

        service.updateAccount(accountUpdate.getLevelUpId(), accountUpdate);

        LevelUpViewModel accountFromService = service.findAcount(accountUpdate.getLevelUpId());

        assertEquals(accountUpdate, accountFromService);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAccount(){
        LevelUpViewModel accountDelete = new LevelUpViewModel();
        accountDelete.setLevelUpId(973);

        service.deleteAccount(accountDelete.getLevelUpId());

        accountDelete = service.findAcount(accountDelete.getLevelUpId());
        assertNull(accountDelete);

        fail("Should throw an error because account does not exist");
    }

    @Test(expected = NotFoundException.class)
    public void deleteAccountByCustomer(){
        LevelUpViewModel accountDelete = new LevelUpViewModel();
        accountDelete.setCustomerId(973);

        service.deleteByCustomer(accountDelete.getCustomerId());

        accountDelete = service.findAccountByCustomer(accountDelete.getCustomerId());

        fail("Should throw an error because account does not exist");
    }

    // INVENTORY SERVICE LAYER TESTS


    @Test
    public void saveFindFindInventoriesByProduct(){

        InventoryViewModel inventoryToAdd = new InventoryViewModel();
        inventoryToAdd.setProductId(732);
        inventoryToAdd.setQuantity(82);

        inventoryToAdd = service.saveInventory(inventoryToAdd);

        InventoryViewModel inventoryCreated = service.findInventory(inventoryToAdd.getInventoryId());

        assertEquals(inventoryToAdd, inventoryCreated);

        List<InventoryViewModel> inventoryList = new ArrayList<>();
        inventoryList.add(inventoryToAdd);

        List<InventoryViewModel> inventoryByProduct = service.findInventoriesByProduct(inventoryToAdd.getProductId());

        assertEquals(inventoryList, inventoryByProduct);
        assertEquals(1, inventoryByProduct.size());
    }


    @Test
    public void findAllInventories(){

        List<InventoryViewModel> allInventories = new ArrayList<>();

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(732);
        inventory.setProductId(732);
        inventory.setQuantity(82);

        allInventories.add(inventory);

        inventory = new InventoryViewModel();
        inventory.setInventoryId(908);
        inventory.setProductId(908);
        inventory.setQuantity(87);

        allInventories.add(inventory);

        inventory = new InventoryViewModel();
        inventory.setInventoryId(973);
        inventory.setProductId(973);
        inventory.setQuantity(37);

        allInventories.add(inventory);

        List<InventoryViewModel> inventoriesFromService = service.findAllInventories();

        assertEquals(allInventories.size(), inventoriesFromService.size());

        assertEquals(allInventories, inventoriesFromService);

    }

    @Test
    public void updateInventory(){
        InventoryViewModel inventoryUpdate = new InventoryViewModel();
        inventoryUpdate.setInventoryId(908);
        inventoryUpdate.setProductId(908);
        inventoryUpdate.setQuantity(1);

        inventoryUpdate.setQuantity(87);

        service.updateInventory(inventoryUpdate.getInventoryId(), inventoryUpdate);

        InventoryViewModel inventoryFromService = service.findInventory(inventoryUpdate.getInventoryId());

        assertEquals(inventoryUpdate, inventoryFromService);

    }


    @Test /*(expected = NotFoundException.class)*/
    public void deleteInventory(){
        InventoryViewModel inventoryDelete = new InventoryViewModel();
        inventoryDelete.setInventoryId(973);

        service.removeInventory(inventoryDelete.getInventoryId());

        inventoryDelete = service.findInventory(inventoryDelete.getInventoryId());

        //fail("Should throw an error because inventory does not exist");
    }



    @Test
    public void deleteInventoryByProduct(){
        InventoryViewModel inventoryDelete = new InventoryViewModel();
        inventoryDelete.setProductId(973);

        service.removeInventoryByProductId(inventoryDelete.getInventoryId());


        List<InventoryViewModel> inventoriesByProduct = service.findInventoriesByProduct(inventoryDelete.getProductId());

        assertEquals(0, inventoriesByProduct.size());
    }


    @Test
    public void addGetInvoiceGetByCustomerAddGetItemGetByInvoice(){
        //Assemble
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));
        //Act
        invoice = service.saveInvoice(invoice);
        //Assemble
        InvoiceItemViewModel item = new InvoiceItemViewModel();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(82);
        item.setUnitPrice(new BigDecimal("19.99"));
        //Act
        item = service.saveInvoiceItem(item);
        //Assemble
        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Act
        InvoiceViewModel invoiceFromService = service.findInvoice(invoice.getInvoiceId());
        //Assert
        assertEquals(invoice, invoiceFromService);

        //Assemble
        List<InvoiceViewModel> invoices = new ArrayList<>();
        invoices.add(invoice);
        //Act
        List<InvoiceViewModel> invoicesByCustomer = service.findInvoicesByCustomer(invoice.getCustomerId());
        //Assert
        assertEquals(invoices, invoicesByCustomer);
        assertEquals(1, invoicesByCustomer.size());

    }

    @Test
    public void getAllInvoices(){
        //Assemble first Invoice with associated item
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(732);
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        //Assemble
        InvoiceItemViewModel item = new InvoiceItemViewModel();
        item.setInvoiceItemId(732);
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(82);
        item.setUnitPrice(new BigDecimal("19.99"));

        //Assemble
        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Assemble
        List<InvoiceViewModel> invoices = new ArrayList<>();
        invoices.add(invoice);

        //Assemble second Invoice with associated item
        invoice = new InvoiceViewModel();
        invoice.setInvoiceId(908);
        invoice.setCustomerId(908);
        invoice.setPurchaseDate(LocalDate.of(2019, 7, 19));

        //Assemble
        item = new InvoiceItemViewModel();
        item.setInvoiceItemId(908);
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(908);
        item.setQuantity(87);
        item.setUnitPrice(new BigDecimal("14.99"));

        //Assemble
        itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Assemble
        invoices.add(invoice);

        //Act
        List<InvoiceViewModel> invoicesFromService = service.findAllInvoices();

        //Assert
       // assertEquals(invoices, invoicesFromService);
        assertEquals(2, invoicesFromService.size());
    }


    @Test
    public void updateInvoice(){
        InvoiceViewModel invoiceUpdated = new InvoiceViewModel();
        invoiceUpdated.setInvoiceId(908);
        invoiceUpdated.setCustomerId(98);
        invoiceUpdated.setPurchaseDate(LocalDate.of(2019, 7, 19));

        InvoiceItemViewModel itemUpdated = new InvoiceItemViewModel();
        itemUpdated.setInvoiceItemId(908);
        itemUpdated.setInvoiceId(908);
        itemUpdated.setInventoryId(908);
        itemUpdated.setQuantity(87);
        itemUpdated.setUnitPrice(new BigDecimal("14.99"));

        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(itemUpdated);
        invoiceUpdated.setInvoiceItems(itemList);

        invoiceUpdated.setCustomerId(908);

        service.updateInvoice(908, invoiceUpdated);

        InvoiceViewModel invoiceFromService = service.findInvoice(908);

        assertEquals(invoiceUpdated, invoiceFromService);

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

        LevelUpViewModel accountAdded = new LevelUpViewModel();
        accountAdded.setLevelUpId(732);
        accountAdded.setCustomerId(732);
        accountAdded.setPoints(5000);
        accountAdded.setMemberDate(LocalDate.of(2010, 8, 2));

        LevelUpViewModel accountNew = new LevelUpViewModel();
        accountNew.setCustomerId(732);
        accountNew.setPoints(5000);
        accountNew.setMemberDate(LocalDate.of(2010, 8, 2));

        doReturn(accountAdded).when(levelUpClient).createAccount(accountNew);
        doReturn(accountAdded).when(levelUpClient).getAccount(732);
        doReturn(accountAdded).when(levelUpClient).getAccountByCustomer(732);

        LevelUpViewModel accountUpdated = new LevelUpViewModel();
        accountUpdated.setLevelUpId(908);
        accountUpdated.setCustomerId(908);
        accountUpdated.setPoints(2000);
        accountUpdated.setMemberDate(LocalDate.of(2012, 7, 19));

        LevelUpViewModel accountDeleted = new LevelUpViewModel();
        accountDeleted.setLevelUpId(973);
        accountDeleted.setCustomerId(973);
        accountDeleted.setPoints(3000);
        accountDeleted.setMemberDate(LocalDate.of(2019, 6, 25));

        //mock setUp for get all accounts
        List<LevelUpViewModel> allAccounts = new ArrayList<>();
        allAccounts.add(accountAdded);
        allAccounts.add(accountUpdated);
        allAccounts.add(accountDeleted);

        //mock response for getting all accounts
        doReturn(allAccounts).when(levelUpClient).getAllAccounts();

        //mock response for updating account
        doNothing().when(levelUpClient).updateAccount(908, accountUpdated);
        doReturn(accountUpdated).when(levelUpClient).getAccount(908);

        //mock response for deleting account
        doNothing().when(levelUpClient).deleteAccount(973);
        doReturn(null).when(levelUpClient).getAccount(973);
        doReturn(null).when(levelUpClient).getAccountByCustomer(973);
    }

    public void setUpInventoryClientMock(){
        inventoryClient = mock(InventoryClient.class);

        InventoryViewModel inventoryAdded = new InventoryViewModel();
        inventoryAdded.setInventoryId(732);
        inventoryAdded.setProductId(732);
        inventoryAdded.setQuantity(82);

        InventoryViewModel inventoryNew = new InventoryViewModel();
        inventoryNew.setProductId(732);
        inventoryNew.setQuantity(82);

        doReturn(inventoryAdded).when(inventoryClient).createInventory(inventoryNew);
        doReturn(inventoryAdded).when(inventoryClient).getInventory(732);


        List<InventoryViewModel> inventoriesByProduct = new ArrayList<>();
        inventoriesByProduct.add(inventoryAdded);

        doReturn(inventoriesByProduct).when(inventoryClient).getInventoriesByProduct(732);

        InventoryViewModel inventoryUpdated = new InventoryViewModel();
        inventoryUpdated.setInventoryId(908);
        inventoryUpdated.setProductId(908);
        inventoryUpdated.setQuantity(87);

        InventoryViewModel inventoryDeleted = new InventoryViewModel();
        inventoryDeleted.setInventoryId(973);
        inventoryDeleted.setProductId(973);
        inventoryDeleted.setQuantity(37);

        List<InventoryViewModel> allInventories = new ArrayList<>();
        allInventories.add(inventoryAdded);
        allInventories.add(inventoryUpdated);
        allInventories.add(inventoryDeleted);


        //mock response for getting all inventories
        doReturn(allInventories).when(inventoryClient).getAllInventories();

        //mock response for updating inventory
        doNothing().when(inventoryClient).updateInventory(908, inventoryUpdated);
        doReturn(inventoryUpdated).when(inventoryClient).getInventory(908);

        //mock response for deleting inventory
        doNothing().when(inventoryClient).deleteInventory(973);
        doReturn(null).when(inventoryClient).getInventory(973);

        List<InventoryViewModel> emptyList = new ArrayList<>();
        doReturn(emptyList).when(inventoryClient).getInventoriesByProduct(973);

    }

    public void setUpInvoiceClientMock(){
        invoiceClient = mock(InvoiceClient.class);


        InvoiceViewModel invoiceAdded = new InvoiceViewModel();
        invoiceAdded.setInvoiceId(732);
        invoiceAdded.setCustomerId(732);
        invoiceAdded.setPurchaseDate(LocalDate.of(2019, 8, 2));

        InvoiceViewModel invoiceNew = new InvoiceViewModel();
        invoiceNew.setCustomerId(732);
        invoiceNew.setPurchaseDate(LocalDate.of(2019, 8, 2));

        doReturn(invoiceAdded).when(invoiceClient).createInvoice(invoiceNew);
        doReturn(invoiceAdded).when(invoiceClient).getInvoice(732);

        List<InvoiceViewModel> invoicesByCustomer = new ArrayList<>();
        invoicesByCustomer.add(invoiceAdded);

        doReturn(invoicesByCustomer).when(invoiceClient).getInvoicesByCustomer(732);

        InvoiceViewModel invoiceUpdated = new InvoiceViewModel();
        invoiceUpdated.setInvoiceId(908);
        invoiceUpdated.setCustomerId(908);
        invoiceUpdated.setPurchaseDate(LocalDate.of(2019, 7, 19));


        InvoiceItemViewModel itemUpdated = new InvoiceItemViewModel();
        itemUpdated.setInvoiceItemId(908);
        itemUpdated.setInvoiceId(908);
        itemUpdated.setInventoryId(908);
        itemUpdated.setQuantity(87);
        itemUpdated.setUnitPrice(new BigDecimal("14.99"));

        doNothing().when(invoiceClient).updateInvoiceItem(908, itemUpdated);
        doReturn(itemUpdated).when(invoiceClient).getInvoiceItem(908);

        List<InvoiceItemViewModel> itemsByInvoice1 = new ArrayList<>();
        itemsByInvoice1.add(itemUpdated);


        invoiceUpdated.setInvoiceItems(itemsByInvoice1);

        doNothing().when(invoiceClient).updateInvoice(908, invoiceUpdated);
        doReturn(invoiceUpdated).when(invoiceClient).getInvoice(908);

        InvoiceViewModel invoiceDeleted = new InvoiceViewModel();
        invoiceDeleted.setInvoiceId(973);
        invoiceDeleted.setCustomerId(973);
        invoiceDeleted.setPurchaseDate(LocalDate.of(2019, 6, 25));

        doNothing().when(invoiceClient).deleteInvoice(973);
        doReturn(null).when(invoiceClient).getInvoice(973);
        List<InvoiceViewModel> emptyList = new ArrayList<>();
        doReturn(emptyList).when(invoiceClient).getInvoicesByCustomer(973);

        List<InvoiceViewModel> allInvoices = new ArrayList<>();
        allInvoices.add(invoiceAdded);
        allInvoices.add(invoiceUpdated);
        doReturn(allInvoices).when(invoiceClient).getAllInvoices();


        //Invoice Items
        InvoiceItemViewModel itemAdded = new InvoiceItemViewModel();
        itemAdded.setInvoiceItemId(732);
        itemAdded.setInvoiceId(732);
        itemAdded.setInventoryId(732);
        itemAdded.setQuantity(82);
        itemAdded.setUnitPrice(new BigDecimal("19.99"));

        InvoiceItemViewModel itemNew = new InvoiceItemViewModel();
        itemNew.setInvoiceId(732);
        itemNew.setInventoryId(732);
        itemNew.setQuantity(82);
        itemNew.setUnitPrice(new BigDecimal("19.99"));

        doReturn(itemAdded).when(invoiceClient).createInvoiceItem(itemNew);
        doReturn(itemAdded).when(invoiceClient).getInvoiceItem(732);

//        List<InvoiceItemViewModel> itemsByInvoice = new ArrayList<>();
//        itemsByInvoice.add(itemAdded);
//        doReturn(itemsByInvoice).when(invoiceClient).getInvoiceItemsByInvoice(732);


        //doReturn(itemsByInvoice1).when(invoiceClient).getInvoiceItemsByInvoice(908);


        doNothing().when(invoiceClient).deleteInvoiceItem(973);
        doReturn(null).when(invoiceClient).getInvoiceItem(973);
//        List<InvoiceItemViewModel> emptyItemList = new ArrayList<>();
//        doReturn(emptyItemList).when(invoiceClient).getInvoiceItemsByInvoice(973);

//        List<InvoiceItemViewModel> allItems = new ArrayList<>();
//        allItems.add(itemAdded);
//        allItems.add(itemUpdated);
//        doReturn(allItems).when(invoiceClient).getAllInvoiceItems();
    }

}





































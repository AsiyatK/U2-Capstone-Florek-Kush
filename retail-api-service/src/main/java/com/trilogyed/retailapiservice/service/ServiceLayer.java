package com.trilogyed.retailapiservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.util.message.LevelUpEntry;
import com.trilogyed.retailapiservice.viewmodels.*;
import com.trilogyed.retailapiservice.viewmodels.backing.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    LevelUpServiceClient levelUpClient;
    ProductServiceClient productClient;
    InventoryServiceClient inventoryClient;
    CustomerServiceClient customerClient;
    InvoiceServiceClient invoiceClient;

    private RabbitTemplate rabbit;

    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.create.#";

    @Autowired
    public ServiceLayer(LevelUpServiceClient levelUpClient,
                        ProductServiceClient productClient,
                        InventoryServiceClient inventoryClient,
                        CustomerServiceClient customerClient,
                        InvoiceServiceClient invoiceClient,
                        RabbitTemplate rabbit)
    {
        this.levelUpClient=levelUpClient;
        this.productClient=productClient;
        this.inventoryClient=inventoryClient;
        this.customerClient=customerClient;
        this.invoiceClient=invoiceClient;
        this.rabbit=rabbit;
    }

    public ProductViewModel getProduct(int productId){
        ProductViewModel product = productClient.getProduct(productId);

        List<InventoryViewModel> productInventories = inventoryClient.getInventoriesByProduct(productId);
        //sum of all inventory quantities for the product
        int totalInStock = productInventories.stream().mapToInt(InventoryViewModel::getQuantity).sum();
        //set sum equal to quantityInStock property
        product.setQuantityInStock(totalInStock);
        return product;

    }

    public List<ProductViewModel> getAllProducts(){
        List<ProductViewModel> allProducts = productClient.getAllProducts();

        List<InventoryViewModel> allInventories = inventoryClient.getAllInventories();

        for (int i = 0; i < allProducts.size(); i++) {

            int currentProductId = allProducts.get(i).getProductId();

            int totalInStock = allInventories.stream()
                    .filter(inventory -> inventory.getProductId() == currentProductId)
                    .mapToInt(InventoryViewModel::getQuantity).sum();

            allProducts.get(i).setQuantityInStock(totalInStock);
        }

        return allProducts;
    }

    //where the magic happens..hopefully
    @Transactional
    public PurchaseViewModel generateInvoice(OrderViewModel order){
        //verify customer exists
        CustomerViewModel customer = new CustomerViewModel();
        try{
            customer = customerClient.getCustomer(order.getCustomerId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Please confirm that the customerId provided is correct and try the request again.");
        }
        //set customer value
        PurchaseViewModel purchase = new PurchaseViewModel();
        purchase.setCustomer(customer);
        //generate invoice
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setCustomerId(order.getCustomerId());
        invoice = invoiceClient.createInvoice(invoice);
        //set invoiceId/date values
        purchase.setInvoiceId(invoice.getInvoiceId());
        purchase.setPurchaseDate(invoice.getPurchaseDate());
        //update inventories and generate purchaseItems(products + invoiceItems)
        purchase.setPurchaseDetails(verifyInStock(order.getOrderList(), invoice.getInvoiceId()));
        //calculate and update level-up points earned
        LevelUpEntry accountUpdate = calculateLevelUp(purchase.getPurchaseDetails());
        accountUpdate.setCustomerId(order.getCustomerId());

        System.out.println("Sending message...");
        rabbit.convertAndSend(EXCHANGE, ROUTING_KEY, accountUpdate);
        System.out.println("Message sent");

        return purchase;
    }

    public PurchaseViewModel getInvoice(int invoiceId){

        return buildPurchaseViewModel(int invoiceId);
    }

    @HystrixCommand(fallbackMethod = "defaultRewards")
    public LevelUpViewModel getRewardsData(int customerId){

        LevelUpViewModel account = levelUpClient.getAccountByCustomer(customerId);

        return account;

    }

    //circuit breaker fallback method
    private LevelUpViewModel defaultRewards(int customerId){
        LevelUpViewModel lvm = new LevelUpViewModel();
        return lvm;
    }

    private PurchaseViewModel buildPurchaseViewModel(int invoiceId){

        InvoiceViewModel invoice = invoiceClient.getInvoice(invoiceId);

        PurchaseViewModel purchase = new PurchaseViewModel();
        purchase.setInvoiceId(invoiceId);
        purchase.setPurchaseDate(invoice.getPurchaseDate());
        purchase.setCustomer(customerClient.getCustomer(invoice.getCustomerId()));

        List<InvoiceItemViewModel> invoiceItems = invoice.getInvoiceItems();

        for (int i = 0; i < invoiceItems.size(); i++) {


        }


        return purchase;
    }

    private List<PurchaseItem> verifyInStock(List<OrderItem> productsToVerify, int invoiceId){

        List<PurchaseItem> productsPurchased = new ArrayList<>();
        //loop through order items
        for (int i = 0; i < productsToVerify.size(); i++) {
            OrderItem currentItem = productsToVerify.get(i);
            //get all inventories for product
            List<InventoryViewModel> productInventories =
                    inventoryClient.getInventoriesByProduct(currentItem.getProductId());
            //sum inventories per product
            int totalInStock = productInventories.stream()
                        .mapToInt(InventoryViewModel::getQuantity).sum();
            //verify if quantity available can fulfill order request
            if(totalInStock >= currentItem.getQuantity()){
                int orderQuantityRemaining = currentItem.getQuantity();
                //then sort inventories and adjust inventory/create line items per orderItem
                List<InventoryViewModel> sortedInventories = new ArrayList<>();

                productInventories.stream()
                                .forEach(inventory -> {
                            if (sortedInventories.size() == 0){
                                sortedInventories.add(0, inventory);
                            } else if(inventory.getQuantity() != 0
                                    && inventory.getQuantity() > sortedInventories.get(0).getQuantity()){
                                sortedInventories.add(0, inventory);
                            } else if(inventory.getQuantity() != 0
                                    && inventory.getQuantity() < sortedInventories.get(0).getQuantity()) {
                                sortedInventories.add(inventory);
                            }
                        });
                //fulfill order using largest inventories first...each inventory requires a unique line item
                for (int j = 0; j < sortedInventories.size(); j++) {
                    InventoryViewModel currentInventory = sortedInventories.get(j);

                    //generate invoiceItem, set purchase props, and update inventory
                    PurchaseItem purchaseItem = new PurchaseItem();

                    ProductViewModel product = productClient.getProduct(currentItem.getProductId());
                    //setting product details for PurchaseItem
                    purchaseItem.setProductName(product.getProductName());
                    purchaseItem.setProductDescription(product.getProductDescription());

                    InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();

                    //break loop for currentProduct if orderQuantity has been met
                    if(orderQuantityRemaining == 0){
                        //breaking loop for currentProduct
                        j = sortedInventories.size();
                    //determining if order quantity  can NOT be fulfilled with current Inventory
                    //if true, fulfill partial order and adjust orderQuantityRemaining
                    } else if(currentInventory.getQuantity() < orderQuantityRemaining){
                        //setting line item props
                        invoiceItem.setInvoiceId(invoiceId);
                        invoiceItem.setInventoryId(currentInventory.getInventoryId());
                        invoiceItem.setQuantity(currentInventory.getQuantity());

                        orderQuantityRemaining -= currentInventory.getQuantity();

                        currentInventory.setQuantity(0);

                        invoiceItem.setUnitPrice(product.getListPrice());
                        //creating invoiceItem
                        invoiceItem = invoiceClient.createInvoiceItem(invoiceItem);
                        //setting invoiceItem prop for purchaseItem
                        purchaseItem.setInvoiceItem(invoiceItem);
                        purchaseItem.setLineTotal(
                                (invoiceItem.getUnitPrice()).multiply(new BigDecimal(invoiceItem.getQuantity()))
                        );
                        //updating inventory
                        inventoryClient.updateInventory(currentInventory.getInventoryId(), currentInventory);
                        //adding line item to list of purchased products
                        productsPurchased.add(purchaseItem);
                    } else {//if false, fulfill order and break loop
                        //setting line item props
                        invoiceItem.setInvoiceId(invoiceId);
                        invoiceItem.setInventoryId(currentInventory.getInventoryId());
                        invoiceItem.setQuantity(orderQuantityRemaining);

                        currentInventory.setQuantity(currentInventory.getQuantity() - orderQuantityRemaining);

                        invoiceItem.setUnitPrice(product.getListPrice());
                        //creating invoiceItem
                        invoiceItem = invoiceClient.createInvoiceItem(invoiceItem);
                        //setting invoiceItem prop for purchaseItem
                        purchaseItem.setInvoiceItem(invoiceItem);
                        purchaseItem.setLineTotal(
                                (invoiceItem.getUnitPrice()).multiply(new BigDecimal(invoiceItem.getQuantity()))
                        );
                        //updating inventory
                        inventoryClient.updateInventory(currentInventory.getInventoryId(), currentInventory);
                        //adding line item to list of purchased products
                        productsPurchased.add(purchaseItem);

                        //breaking loop for currentProduct
                        j = sortedInventories.size();
                    }

                }//end of loop to fulfill orders

            //if quantity available is not sufficient, throw exception
            } else {
                throw new IllegalArgumentException("quantity available is less than quantity ordered.");
            }
        }//end of loop through order items

         return productsPurchased;
    }

    private LevelUpEntry calculateLevelUp(List<PurchaseItem> purchasesToCalculate){
        BigDecimal totalSpent = new BigDecimal("0.00");
        for (int i = 0; i < purchasesToCalculate.size(); i++) {
            totalSpent = totalSpent.add(purchasesToCalculate.get(i).getLineTotal());
        }
        int points = ((totalSpent.divide(new BigDecimal("50.00")))
                        .multiply(new BigDecimal("10.00")))
                        .intValue();
        //add messaging to levelUp queue consumer service
        LevelUpEntry accountUpdate = new LevelUpEntry();

        accountUpdate.setPoints(points);

        return accountUpdate;
    }

}

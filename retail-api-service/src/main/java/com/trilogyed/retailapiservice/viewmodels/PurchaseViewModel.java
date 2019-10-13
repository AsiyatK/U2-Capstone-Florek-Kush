package com.trilogyed.retailapiservice.viewmodels;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseViewModel {

    private int invoiceId;
    private CustomerViewModel customer;
    private List<ItemViewModel> purchaseDetails;
    private BigDecimal total;
    private int pointsTotal;

}

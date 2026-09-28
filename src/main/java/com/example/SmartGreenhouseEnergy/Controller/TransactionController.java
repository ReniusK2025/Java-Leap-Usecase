package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.*;
import com.example.SmartGreenhouseEnergy.Service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService service;
    public TransactionController(TransactionService service) { this.service=service; }

    @PostMapping("/purchase-orders") public PurchaseOrder purchase(@RequestBody PurchaseOrder x) { return service.createPurchaseOrder(x); }
    @GetMapping("/purchase-orders") public List<PurchaseOrder> purchases() { return service.purchaseOrders(); }

    @PostMapping("/vendor-bills") public VendorBill bill(@RequestBody VendorBill x) { return service.createVendorBill(x); }
    @GetMapping("/vendor-bills") public List<VendorBill> bills() { return service.vendorBills(); }

    @PostMapping("/sales-orders") public SalesOrder sales(@RequestBody SalesOrder x) { return service.createSalesOrder(x); }
    @GetMapping("/sales-orders") public List<SalesOrder> salesOrders() { return service.salesOrders(); }

    @PostMapping("/customer-invoices") public CustomerInvoice invoice(@RequestBody CustomerInvoice x) { return service.createInvoice(x); }
    @GetMapping("/customer-invoices") public List<CustomerInvoice> invoices() { return service.invoices(); }

    @PostMapping("/payments") public Payment payment(@RequestBody Payment x) { return service.receivePayment(x); }
    @GetMapping("/payments") public List<Payment> payments() { return service.payments(); }

    @GetMapping("/journals") public List<JournalEntry> journals() { return service.journals(); }
}

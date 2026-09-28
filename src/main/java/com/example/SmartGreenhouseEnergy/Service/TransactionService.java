package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Model.*;
import com.example.SmartGreenhouseEnergy.Repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final PurchaseOrderRepository purchaseOrders;
    private final VendorBillRepository vendorBills;
    private final SalesOrderRepository salesOrders;
    private final CustomerInvoiceRepository invoices;
    private final PaymentRepository payments;
    private final JournalEntryRepository journals;
    private final ContactRepository contacts;
    private final ProductRepository products;

    public TransactionService(
            PurchaseOrderRepository purchaseOrders,
            VendorBillRepository vendorBills,
            SalesOrderRepository salesOrders,
            CustomerInvoiceRepository invoices,
            PaymentRepository payments,
            JournalEntryRepository journals,
            ContactRepository contacts,
            ProductRepository products) {
        this.purchaseOrders = purchaseOrders;
        this.vendorBills = vendorBills;
        this.salesOrders = salesOrders;
        this.invoices = invoices;
        this.payments = payments;
        this.journals = journals;
        this.contacts = contacts;
        this.products = products;
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        validateVendorAndProduct(po.getVendorId(), po.getProductId());
        calculatePurchaseTotal(po);
        po.setStatus("ORDERED");
        po.setOrderDate(LocalDate.now());
        return purchaseOrders.save(po);
    }

    public PurchaseOrder findPurchaseOrder(Long id) {
        return purchaseOrders.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found: " + id));
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder value) {
        PurchaseOrder po = findPurchaseOrder(id);
        validateVendorAndProduct(value.getVendorId(), value.getProductId());
        po.setVendorId(value.getVendorId());
        po.setProductId(value.getProductId());
        po.setQuantity(value.getQuantity());
        po.setUnitPrice(value.getUnitPrice());
        calculatePurchaseTotal(po);
        if (value.getStatus() != null && !value.getStatus().isBlank()) {
            po.setStatus(value.getStatus());
        }
        return purchaseOrders.save(po);
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrders.delete(findPurchaseOrder(id));
    }

    public VendorBill createVendorBill(VendorBill bill) {
        bill.setBillDate(LocalDate.now());
        bill.setStatus("OPEN");

        VendorBill saved = vendorBills.save(bill);

        journal(
                "PURCHASE", "Maintenance Creditors", "CREDIT",
                bill.getAmount(), "VENDOR_BILL", saved.getId(),
                "Pump/collector maintenance bill"
        );
        journal(
                "PURCHASE", "Solar Collector Servicing Costs", "DEBIT",
                bill.getAmount(), "VENDOR_BILL", saved.getId(),
                "Maintenance expense"
        );

        return saved;
    }

    public SalesOrder createSalesOrder(SalesOrder so) {
        validateGrowerAndProduct(so.getGrowerId(), so.getProductId());

        so.setTotalAmount(round(so.getEnergyKwh() * so.getUnitPrice()));
        so.setStatus("CONFIRMED");
        so.setOrderDate(LocalDate.now());

        return salesOrders.save(so);
    }

    public SalesOrder findSalesOrder(Long id) {
        return salesOrders.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found: " + id));
    }

    public SalesOrder updateSalesOrder(Long id, SalesOrder value) {
        SalesOrder so = findSalesOrder(id);
        validateGrowerAndProduct(value.getGrowerId(), value.getProductId());

        so.setGrowerId(value.getGrowerId());
        so.setProductId(value.getProductId());
        so.setEnergyKwh(value.getEnergyKwh());
        so.setUnitPrice(value.getUnitPrice());
        so.setZone(value.getZone());
        so.setTotalAmount(round(value.getEnergyKwh() * value.getUnitPrice()));

        if (value.getStatus() != null && !value.getStatus().isBlank()) {
            so.setStatus(value.getStatus());
        }

        return salesOrders.save(so);
    }

    public void deleteSalesOrder(Long id) {
        salesOrders.delete(findSalesOrder(id));
    }

    public CustomerInvoice createInvoice(CustomerInvoice invoice) {
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setStatus("OPEN");

        CustomerInvoice saved = invoices.save(invoice);

        journal(
                "SALES", "Greenhouse Heating Revenue", "CREDIT",
                invoice.getAmount(), "CUSTOMER_INVOICE", saved.getId(),
                "Thermal heating service revenue"
        );
        journal(
                "SALES", "Customer Receivables", "DEBIT",
                invoice.getAmount(), "CUSTOMER_INVOICE", saved.getId(),
                "Grower receivable"
        );

        return saved;
    }

    public Payment receivePayment(Payment payment) {
        payment.setPaymentDate(LocalDate.now());

        Payment saved = payments.save(payment);

        if ("CUSTOMER".equalsIgnoreCase(payment.getPaymentType())) {
            invoices.findById(payment.getReferenceId()).ifPresent(i -> {
                i.setStatus("PAID");
                invoices.save(i);
            });

            journal(
                    "BANK", "Cash/Bank", "DEBIT",
                    payment.getAmount(), "PAYMENT", saved.getId(),
                    "Customer bank transfer received"
            );
            journal(
                    "BANK", "Customer Receivables", "CREDIT",
                    payment.getAmount(), "PAYMENT", saved.getId(),
                    "Customer invoice settlement"
            );
        } else {
            vendorBills.findById(payment.getReferenceId()).ifPresent(b -> {
                b.setStatus("PAID");
                vendorBills.save(b);
            });

            journal(
                    "BANK", "Maintenance Creditors", "DEBIT",
                    payment.getAmount(), "PAYMENT", saved.getId(),
                    "Vendor payment"
            );
            journal(
                    "BANK", "Cash/Bank", "CREDIT",
                    payment.getAmount(), "PAYMENT", saved.getId(),
                    "Vendor bank payment"
            );
        }

        return saved;
    }

    public List<PurchaseOrder> purchaseOrders() {
        return purchaseOrders.findAll();
    }

    public List<VendorBill> vendorBills() {
        return vendorBills.findAll();
    }

    public List<SalesOrder> salesOrders() {
        return salesOrders.findAll();
    }

    public List<CustomerInvoice> invoices() {
        return invoices.findAll();
    }

    public List<Payment> payments() {
        return payments.findAll();
    }

    public List<JournalEntry> journals() {
        return journals.findAll();
    }

    private void validateVendorAndProduct(Long vendorId, Long productId) {
        if (!contacts.existsById(vendorId)) {
            throw new RuntimeException("Vendor contact not found: " + vendorId);
        }
        if (!products.existsById(productId)) {
            throw new RuntimeException("Product not found: " + productId);
        }
    }

    private void validateGrowerAndProduct(Long growerId, Long productId) {
        if (!contacts.existsById(growerId)) {
            throw new RuntimeException("Grower contact not found: " + growerId);
        }
        if (!products.existsById(productId)) {
            throw new RuntimeException("Product not found: " + productId);
        }
    }

    private void calculatePurchaseTotal(PurchaseOrder po) {
        po.setTotalAmount(round(po.getQuantity() * po.getUnitPrice()));
    }

    private void journal(
            String type, String account, String dc, Double amount,
            String refType, Long refId, String description) {
        journals.save(new JournalEntry(
                null, LocalDate.now(), type, account, dc,
                amount, refType, refId, description
        ));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

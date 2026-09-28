package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.*;
import com.example.SmartGreenhouseEnergy.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    private final ThermalStorageService thermal;
    private final ContactService contacts;
    private final ProductService products;
    private final TransactionService transactions;
    private final ReportService reports;
    private final BudgetService budgets;

    public WebController(
            ThermalStorageService thermal,
            ContactService contacts,
            ProductService products,
            TransactionService transactions,
            ReportService reports,
            BudgetService budgets) {
        this.thermal = thermal;
        this.contacts = contacts;
        this.products = products;
        this.transactions = transactions;
        this.reports = reports;
        this.budgets = budgets;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("dashboard", reports.dashboard());
        model.addAttribute("pnl", reports.profitLoss());
        model.addAttribute("balance", reports.balanceSheet());
        return "dashboard";
    }

    // ---------------- Tanks ----------------

    @GetMapping("/tanks")
    public String tanks(Model model) {
        model.addAttribute("tanks", thermal.allTanks());
        return "tanks";
    }

    @GetMapping("/tanks/new")
    public String newTank(Model model) {
        model.addAttribute("tank", new ThermalStorageTank());
        model.addAttribute("pageTitle", "Add Thermal Storage Tank");
        return "tank-form";
    }

    @PostMapping("/tanks/save")
    public String saveTank(@ModelAttribute ThermalStorageTank tank) {
        thermal.saveTank(tank);
        return "redirect:/tanks";
    }

    @GetMapping("/tanks/edit/{id}")
    public String editTank(@PathVariable Long id, Model model) {
        model.addAttribute("tank", thermal.getTank(id));
        model.addAttribute("pageTitle", "Edit Thermal Storage Tank");
        return "tank-form";
    }

    @PostMapping("/tanks/update/{id}")
    public String updateTank(@PathVariable Long id, @ModelAttribute ThermalStorageTank tank) {
        thermal.updateTank(id, tank);
        return "redirect:/tanks";
    }

    @PostMapping("/tanks/delete/{id}")
    public String deleteTank(@PathVariable Long id) {
        thermal.deleteTank(id);
        return "redirect:/tanks";
    }

    // ---------------- Telemetry ----------------

    @GetMapping("/telemetry")
    public String telemetry(Model model) {
        model.addAttribute("telemetry", thermal.allTelemetry());
        model.addAttribute("tanks", thermal.allTanks());
        return "telemetry";
    }

    @GetMapping("/telemetry/new")
    public String newTelemetry(Model model) {
        model.addAttribute("telemetry", new Telemetry());
        model.addAttribute("tanks", thermal.allTanks());
        return "telemetry-form";
    }

    @PostMapping("/telemetry/save")
    public String saveTelemetry(@ModelAttribute Telemetry telemetry) {
        thermal.processTelemetry(telemetry);
        return "redirect:/telemetry";
    }

    @PostMapping("/telemetry/delete/{id}")
    public String deleteTelemetry(@PathVariable Long id) {
        thermal.deleteTelemetry(id);
        return "redirect:/telemetry";
    }

    // ---------------- Contacts ----------------

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("contacts", contacts.findAll());
        return "contacts";
    }

    @GetMapping("/contacts/new")
    public String newContact(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("pageTitle", "Add Contact");
        return "contact-form";
    }

    @PostMapping("/contacts/save")
    public String saveContact(@ModelAttribute Contact contact) {
        contacts.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/edit/{id}")
    public String editContact(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contacts.findById(id));
        model.addAttribute("pageTitle", "Edit Contact");
        return "contact-form";
    }

    @PostMapping("/contacts/update/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute Contact contact) {
        contacts.update(id, contact);
        return "redirect:/contacts";
    }

    @PostMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contacts.delete(id);
        return "redirect:/contacts";
    }

    // ---------------- Products ----------------

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", products.findAll());
        return "products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add Product");
        return "product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        products.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", products.findById(id));
        model.addAttribute("pageTitle", "Edit Product");
        return "product-form";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        products.update(id, product);
        return "redirect:/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        products.delete(id);
        return "redirect:/products";
    }

    // ---------------- Budgets ----------------

    @GetMapping("/budgets")
    public String budgets(Model model) {
        model.addAttribute("budgets", budgets.all());
        return "budgets";
    }

    @GetMapping("/budgets/new")
    public String newBudget(Model model) {
        model.addAttribute("budget", new Budget());
        model.addAttribute("pageTitle", "Add Zone Budget");
        return "budget-form";
    }

    @PostMapping("/budgets/save")
    public String saveBudget(@ModelAttribute Budget budget) {
        budgets.save(budget);
        return "redirect:/budgets";
    }

    @GetMapping("/budgets/edit/{id}")
    public String editBudget(@PathVariable Long id, Model model) {
        model.addAttribute("budget", budgets.findById(id));
        model.addAttribute("pageTitle", "Edit Zone Budget");
        return "budget-form";
    }

    @PostMapping("/budgets/update/{id}")
    public String updateBudget(@PathVariable Long id, @ModelAttribute Budget budget) {
        budgets.update(id, budget);
        return "redirect:/budgets";
    }

    @PostMapping("/budgets/delete/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgets.delete(id);
        return "redirect:/budgets";
    }

    // ---------------- Purchase Orders ----------------

    @GetMapping("/purchase-orders")
    public String purchaseOrders(Model model) {
        model.addAttribute("purchaseOrders", transactions.purchaseOrders());
        model.addAttribute("vendors", contacts.findAll());
        model.addAttribute("products", products.findAll());
        return "purchase-orders";
    }

    @GetMapping("/purchase-orders/new")
    public String newPurchaseOrder(Model model) {
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        model.addAttribute("vendors", contacts.findAll());
        model.addAttribute("products", products.findAll());
        model.addAttribute("pageTitle", "Create Purchase Order");
        return "purchase-order-form";
    }

    @PostMapping("/purchase-orders/save")
    public String savePurchaseOrder(@ModelAttribute PurchaseOrder po) {
        transactions.createPurchaseOrder(po);
        return "redirect:/purchase-orders";
    }

    @GetMapping("/purchase-orders/edit/{id}")
    public String editPurchaseOrder(@PathVariable Long id, Model model) {
        model.addAttribute("purchaseOrder", transactions.findPurchaseOrder(id));
        model.addAttribute("vendors", contacts.findAll());
        model.addAttribute("products", products.findAll());
        model.addAttribute("pageTitle", "Edit Purchase Order");
        return "purchase-order-form";
    }

    @PostMapping("/purchase-orders/update/{id}")
    public String updatePurchaseOrder(
            @PathVariable Long id,
            @ModelAttribute PurchaseOrder po) {
        transactions.updatePurchaseOrder(id, po);
        return "redirect:/purchase-orders";
    }

    @PostMapping("/purchase-orders/delete/{id}")
    public String deletePurchaseOrder(@PathVariable Long id) {
        transactions.deletePurchaseOrder(id);
        return "redirect:/purchase-orders";
    }

    // ---------------- Sales Orders ----------------

    @GetMapping("/sales-orders")
    public String salesOrders(Model model) {
        model.addAttribute("salesOrders", transactions.salesOrders());
        model.addAttribute("growers", contacts.findAll());
        model.addAttribute("products", products.findAll());
        return "sales-orders";
    }

    @GetMapping("/sales-orders/new")
    public String newSalesOrder(Model model) {
        model.addAttribute("salesOrder", new SalesOrder());
        model.addAttribute("growers", contacts.findAll());
        model.addAttribute("products", products.findAll());
        model.addAttribute("pageTitle", "Create Thermal Heating Sales Order");
        return "sales-order-form";
    }

    @PostMapping("/sales-orders/save")
    public String saveSalesOrder(@ModelAttribute SalesOrder so) {
        transactions.createSalesOrder(so);
        return "redirect:/sales-orders";
    }

    @GetMapping("/sales-orders/edit/{id}")
    public String editSalesOrder(@PathVariable Long id, Model model) {
        model.addAttribute("salesOrder", transactions.findSalesOrder(id));
        model.addAttribute("growers", contacts.findAll());
        model.addAttribute("products", products.findAll());
        model.addAttribute("pageTitle", "Edit Thermal Heating Sales Order");
        return "sales-order-form";
    }

    @PostMapping("/sales-orders/update/{id}")
    public String updateSalesOrder(
            @PathVariable Long id,
            @ModelAttribute SalesOrder so) {
        transactions.updateSalesOrder(id, so);
        return "redirect:/sales-orders";
    }

    @PostMapping("/sales-orders/delete/{id}")
    public String deleteSalesOrder(@PathVariable Long id) {
        transactions.deleteSalesOrder(id);
        return "redirect:/sales-orders";
    }

    // ---------------- Vendor Bills ----------------

    @GetMapping("/vendor-bills")
    public String vendorBills(Model model) {
        model.addAttribute("vendorBills", transactions.vendorBills());
        model.addAttribute("purchaseOrders", transactions.purchaseOrders());
        model.addAttribute("vendors", contacts.findAll());
        return "vendor-bills";
    }

    @GetMapping("/vendor-bills/new")
    public String newVendorBill(Model model) {
        model.addAttribute("vendorBill", new VendorBill());
        model.addAttribute("purchaseOrders", transactions.purchaseOrders());
        model.addAttribute("vendors", contacts.findAll());
        return "vendor-bill-form";
    }

    @PostMapping("/vendor-bills/save")
    public String saveVendorBill(@ModelAttribute VendorBill bill) {
        transactions.createVendorBill(bill);
        return "redirect:/vendor-bills";
    }

    // ---------------- Customer Invoices ----------------

    @GetMapping("/customer-invoices")
    public String customerInvoices(Model model) {
        model.addAttribute("invoices", transactions.invoices());
        model.addAttribute("salesOrders", transactions.salesOrders());
        model.addAttribute("growers", contacts.findAll());
        return "customer-invoices";
    }

    @GetMapping("/customer-invoices/new")
    public String newCustomerInvoice(Model model) {
        model.addAttribute("invoice", new CustomerInvoice());
        model.addAttribute("salesOrders", transactions.salesOrders());
        model.addAttribute("growers", contacts.findAll());
        return "customer-invoice-form";
    }

    @PostMapping("/customer-invoices/save")
    public String saveCustomerInvoice(@ModelAttribute CustomerInvoice invoice) {
        transactions.createInvoice(invoice);
        return "redirect:/customer-invoices";
    }

    // ---------------- Payments ----------------

    @GetMapping("/payments")
    public String payments(Model model) {
        model.addAttribute("payments", transactions.payments());
        model.addAttribute("invoices", transactions.invoices());
        model.addAttribute("vendorBills", transactions.vendorBills());
        return "payments";
    }

    @GetMapping("/payments/new")
    public String newPayment(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("invoices", transactions.invoices());
        model.addAttribute("vendorBills", transactions.vendorBills());
        return "payment-form";
    }

    @PostMapping("/payments/save")
    public String savePayment(@ModelAttribute Payment payment) {
        transactions.receivePayment(payment);
        return "redirect:/payments";
    }


    // Compatibility routes from the original version
    @GetMapping("/masters")
    public String mastersRedirect() {
        return "redirect:/contacts";
    }

    @GetMapping("/transactions")
    public String transactionsRedirect() {
        return "redirect:/purchase-orders";
    }

    // ---------------- Reports ----------------

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("pnl", reports.profitLoss());
        model.addAttribute("balance", reports.balanceSheet());
        model.addAttribute("budgets", budgets.all());
        return "reports";
    }
}

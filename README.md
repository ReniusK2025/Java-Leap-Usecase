# Smart Agricultural Greenhouse Hydro-Thermal Energy Storage System

This project follows the structure and technology style of the supplied `SpringJPA 2` reference project.

## Technology stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL 8+
- Thymeleaf
- Lombok
- Maven
- VS Code

## Package structure

`com.example.SmartGreenhouseEnergy`

- `Model` - JPA entities
- `Repository` - Spring Data repositories
- `Service` - business logic and thermal/accounting calculations
- `Controller` - REST APIs and Thymeleaf web pages
- `resources/templates` - web UI
- `resources/static/css` - UI styling

## Main APIs

### Master data

- `POST /api/contacts`
- `GET /api/contacts`
- `PUT /api/contacts/{id}`
- `DELETE /api/contacts/{id}`
- `POST /api/products`
- `GET /api/products`

### Thermal storage

- `POST /api/thermal-storage/tanks`
- `GET /api/thermal-storage/tanks`
- `POST /api/thermal-storage/telemetry`
- `GET /api/thermal-storage/telemetry`

Telemetry kWh calculation:

`gross kWh = litres × 4.186 × |outlet°C - inlet°C| / 3600`

`net kWh = gross kWh × tank efficiency`

### Transactions

- `POST /api/purchase-orders`
- `POST /api/vendor-bills`
- `POST /api/sales-orders`
- `POST /api/customer-invoices`
- `POST /api/payments`
- `GET /api/journals`
- `POST /api/accounting/coa`
- `GET /api/accounting/coa`
- `POST /api/accounting/pump-power-expense`

### Budgets

- `POST /api/budgets`
- `GET /api/budgets`
- `GET /api/budgets/report/{zone}`

### Reports

- `GET /api/reports/dashboard`
- `GET /api/reports/profit-loss`
- `GET /api/reports/balance-sheet`

## Run on Mac + VS Code

1. Install Java 21.
2. Install MySQL 8+ and start MySQL.
3. Create the database or let the configured connection create it:
   `greenhouse_energy_db`
4. Open `src/main/resources/application.properties`.
5. Replace:
   `spring.datasource.password=YOUR_MYSQL_PASSWORD`
   with your MySQL password.
6. Open this folder in VS Code.
7. Run:
   `./mvnw spring-boot:run`
8. Open:
   `http://localhost:8080`

If `mvnw` is not executable:
`chmod +x mvnw`

## Suggested demo sequence

1. Create a grower contact.
2. Create a pump vendor contact.
3. Create the three products from the use case.
4. Create Thermal Storage Zone 3 tank.
5. POST telemetry.
6. Create a pump purchase order.
7. Convert it to a vendor bill.
8. Create a thermal sales order using calculated kWh.
9. Create a customer invoice.
10. Record customer/vendor bank payments.
11. Create a seasonal budget for Zone 3.
12. Open `/reports`.

## Example JSON

### Grower

```json
{
  "name": "Green Valley Growers",
  "type": "GROWER",
  "email": "accounts@greenvalley.example",
  "phone": "9876543210",
  "address": "Thermal Storage Zone 3"
}
```

### Thermal tank

```json
{
  "tankCode": "TANK-Z3-001",
  "zone": "Thermal Storage Zone 3",
  "capacityLiters": 10000,
  "currentTemperatureC": 45,
  "targetTemperatureC": 60,
  "efficiencyPercent": 90,
  "active": true
}
```

### Telemetry

```json
{
  "tankId": 1,
  "inletTemperatureC": 45,
  "outletTemperatureC": 60,
  "waterVolumeLiters": 10000
}
```

For the example above, gross energy is approximately 174.42 kWh and net delivered energy at 90% efficiency is approximately 156.98 kWh.

### Product: thermal heating

```json
{
  "name": "Greenhouse Thermal Heating kWh",
  "type": "SERVICE",
  "unit": "kWh",
  "unitPrice": 8.5
}
```

### Budget

```json
{
  "zone": "Thermal Storage Zone 3",
  "season": "2026-2027",
  "heatingRevenueBudget": 250000,
  "pumpPowerExpenseBudget": 50000,
  "servicingExpenseBudget": 30000
}
```

## Notes

The reference project had a food CRUD example. This implementation preserves its simple Controller -> Service -> Repository style while replacing the food domain with the greenhouse energy domain.

For a production ERP, add authentication/authorization, DTO validation, database migrations (Flyway/Liquibase), transaction boundaries, audit logging, role-based access control and a proper double-entry accounting model.


## Browser CRUD and workflow screens

The web UI now supports:

### Full CRUD
- Thermal Storage Tanks: Create / Read / Update / Delete
- Contacts: Create / Read / Update / Delete
- Products: Create / Read / Update / Delete
- Zone Budgets: Create / Read / Update / Delete
- Purchase Orders: Create / Read / Update / Delete
- Thermal Heating Sales Orders: Create / Read / Update / Delete

### Workflow forms
- Telemetry: Record / View / Delete
- Vendor Bills: Create / View
- Customer Invoices: Create / View
- Payments: Record / View

Open these pages after starting the application:

- `/tanks`
- `/telemetry`
- `/contacts`
- `/products`
- `/purchase-orders`
- `/sales-orders`
- `/vendor-bills`
- `/customer-invoices`
- `/payments`
- `/budgets`
- `/reports`

The dashboard also contains quick-action cards for the main modules.

### Recommended demo

1. Open Contacts and add a GROWER and a VENDOR.
2. Open Products and add the thermal heating service and pump assembly.
3. Open Tanks and add a second thermal tank.
4. Open Telemetry and record a reading. The application calculates gross and net kWh.
5. Open Sales Orders and create a thermal heating order.
6. Open Customer Invoices and create the invoice.
7. Open Payments and record the customer payment.
8. Open Purchase Orders and create a pump order.
9. Open Vendor Bills and create the supplier bill.
10. Open Payments and record the vendor payment.
11. Open Budgets and create a Zone 3 seasonal budget.
12. Open Reports to view the financial snapshot.

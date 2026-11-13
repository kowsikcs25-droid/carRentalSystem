# Car Rental System

A desktop-based **Car Rental System** developed using **Java Swing**. The application provides a graphical interface to manage customers, search and book cars, maintain the vehicle fleet, process payments, and view reservation reports.

## Features

- **Dashboard** – Navigate between the main modules.
- **Customer Management** – Add customers and view customer details.
- **Car Search & Booking** – Search cars by brand, model, category, or availability status; book an available car.
- **Fleet Management** – Add cars, update vehicle status, and delete cars.
- **Payment Processing** – View pending bookings and mark payments as completed.
- **Reservation & Reports** – View and cancel bookings, and generate a summary report.
- **Sample Data** – Includes sample cars and customers to help test the application.

## Technologies Used

- Java
- Java Swing (GUI)
- AWT
- `ArrayList` for in-memory data storage
- `JTable` and `DefaultTableModel` for displaying records

## Project Structure

```text
Car-Rental-System/
├── README.md
└── CarRentalSystem.java
```

## Requirements

- JDK 8 or later
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans, or VS Code), or a terminal

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/YOUR-USERNAME/Car-Rental-System.git
cd Car-Rental-System
```

Replace `YOUR-USERNAME` with your GitHub username.

### 2. Compile the program

```bash
javac CarRentalSystem.java
```

### 3. Run the application

```bash
java CarRentalSystem
```

The Car Rental System dashboard will open in a desktop window.

## How to Use

1. Open **Customer Management** to add customer details.
2. Open **Car Search & Booking** to search for an available car.
3. Select a car and click **Book Selected Car**.
4. Enter the customer name and rental duration.
5. Open **Payment Processing** and select the pending booking.
6. Choose a payment method and click **Process Payment**.
7. Use **Reservation & Reports** to view bookings, cancel a booking, or generate a report.
8. Use **Fleet Management** to add vehicles or update their status.

## Notes and Limitations

- This is a demonstration/academic project. Data is stored in memory using `ArrayList`.
- Customer, car, booking, and payment data will be reset when the application is closed.
- Payment processing is simulated. The application does not connect to a real payment gateway.
- Booking dates, authentication, database connectivity, and invoice generation are not implemented.

## Future Improvements

- Connect the application to MySQL or SQLite for permanent data storage.
- Add login and role-based access.
- Add pickup and return dates with date validation.
- Validate mobile numbers and driving licence details.
- Integrate a real payment gateway.
- Generate downloadable booking invoices and reports.

## Author

**Your Name**

## License

This project is available for educational and learning purposes. Add a license file if you plan to distribute it under a specific open-source license.

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CarRentalSystem extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final List<Car> cars = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();

    private final Color NAVY = new Color(19, 35, 58);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT = new Color(243, 247, 252);
    private final Color GREEN = new Color(22, 163, 105);
    private final Color RED = new Color(220, 65, 75);

    private JTable carTable;
    private JTable fleetTable;
    private JTable bookingTable;
    private JTable customerTable;

    public CarRentalSystem() {
        setTitle("Car Rental System");
        setSize(1100, 700);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadSampleData();
        buildUI();
        showScreen("HOME");
    }

    private void loadSampleData() {
        cars.add(new Car("C101", "Toyota", "Innova", "SUV", 2500, "Available"));
        cars.add(new Car("C102", "Hyundai", "Creta", "SUV", 2200, "Available"));
        cars.add(new Car("C103", "Honda", "City", "Sedan", 1800, "Available"));
        cars.add(new Car("C104", "Maruti", "Swift", "Hatchback", 1400, "Available"));
        cars.add(new Car("C105", "Mahindra", "Thar", "SUV", 3000, "Available"));

        customers.add(new Customer("U001", "Arun", "9876543210", "DL123456"));
        customers.add(new Customer("U002", "Rahul", "9123456780", "DL789012"));
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel title = new JLabel("CAR RENTAL SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton homeBtn = createButton("Dashboard", BLUE);
        homeBtn.addActionListener(e -> showScreen("HOME"));

        header.add(title, BorderLayout.WEST);
        header.add(homeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createCustomerPanel(), "CUSTOMER");
        mainPanel.add(createSearchBookingPanel(), "BOOKING");
        mainPanel.add(createFleetPanel(), "FLEET");
        mainPanel.add(createPaymentPanel(), "PAYMENT");
        mainPanel.add(createReservationPanel(), "RESERVATION");

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel welcome = new JLabel(
                "<html><div style='text-align:center'>" +
                "<h1>Welcome to Car Rental System</h1>" +
                "<p>Search, book and manage rental cars easily.</p>" +
                "</div></html>",
                SwingConstants.CENTER
        );
        welcome.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBackground(LIGHT);

        grid.add(moduleCard("Customer Management",
                "Register customers, manage profiles and view customer details.",
                "CUSTOMER", BLUE));
        grid.add(moduleCard("Car Search & Booking",
                "Search available cars, select dates and confirm bookings.",
                "BOOKING", GREEN));
        grid.add(moduleCard("Fleet Management",
                "Add, update and track vehicle availability and maintenance.",
                "FLEET", new Color(217, 119, 6)));
        grid.add(moduleCard("Payment Processing",
                "Calculate rental charges and record payment information.",
                "PAYMENT", new Color(124, 58, 237)));
        grid.add(moduleCard("Reservation & Reports",
                "View reservations, booking status and rental statistics.",
                "RESERVATION", RED));
        grid.add(moduleCard("Exit System",
                "Close the Car Rental System application.",
                "EXIT", NAVY));

        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel moduleCard(String title, String description, String screen, Color color) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 228)),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 18));
        t.setForeground(color);

        JLabel d = new JLabel(
                "<html><div style='text-align:center'>" + description + "</div></html>",
                SwingConstants.CENTER
        );
        d.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton open = createButton(screen.equals("EXIT") ? "Exit" : "Open", color);
        open.addActionListener(e -> {
            if (screen.equals("EXIT")) {
                System.exit(0);
            } else {
                showScreen(screen);
            }
        });

        card.add(t, BorderLayout.NORTH);
        card.add(d, BorderLayout.CENTER);
        card.add(open, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = basePanel("Customer Management");
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField license = new JTextField();

        form.add(new JLabel("Customer Name:"));
        form.add(name);
        form.add(new JLabel("Mobile Number:"));
        form.add(phone);
        form.add(new JLabel("Driving License:"));
        form.add(license);

        JButton add = createButton("Add Customer", GREEN);
        JButton clear = createButton("Clear", NAVY);
        form.add(add);
        form.add(clear);

        panel.add(form, BorderLayout.NORTH);
        customerTable = new JTable();
        refreshCustomerTable();
        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        add.addActionListener(e -> {
            if (name.getText().trim().isEmpty()
                    || phone.getText().trim().isEmpty()
                    || license.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all customer details.");
                return;
            }

            String id = String.format("U%03d", customers.size() + 1);
            customers.add(new Customer(id, name.getText().trim(),
                    phone.getText().trim(), license.getText().trim()));

            refreshCustomerTable();
            name.setText("");
            phone.setText("");
            license.setText("");
            JOptionPane.showMessageDialog(this, "Customer added successfully!");
        });

        clear.addActionListener(e -> {
            name.setText("");
            phone.setText("");
            license.setText("");
        });

        return panel;
    }

    private JPanel createSearchBookingPanel() {
        JPanel panel = basePanel("Car Search & Booking");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        top.setBackground(Color.WHITE);

        JTextField search = new JTextField(15);
        JButton searchBtn = createButton("Search", BLUE);
        JButton resetBtn = createButton("Show All", NAVY);
        JButton bookBtn = createButton("Book Selected Car", GREEN);

        top.add(new JLabel("Search by brand/model/category:"));
        top.add(search);
        top.add(searchBtn);
        top.add(resetBtn);
        top.add(bookBtn);

        panel.add(top, BorderLayout.NORTH);
        carTable = new JTable();
        refreshCarTable(cars);
        panel.add(new JScrollPane(carTable), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String q = search.getText().trim().toLowerCase();
            List<Car> result = new ArrayList<>();

            for (Car c : cars) {
                if (c.brand.toLowerCase().contains(q)
                        || c.model.toLowerCase().contains(q)
                        || c.category.toLowerCase().contains(q)
                        || c.status.toLowerCase().contains(q)) {
                    result.add(c);
                }
            }
            refreshCarTable(result);
        });

        resetBtn.addActionListener(e -> {
            search.setText("");
            refreshCarTable(cars);
        });

        bookBtn.addActionListener(e -> bookSelectedCar());
        return panel;
    }

    private void bookSelectedCar() {
        int row = carTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a car first.");
            return;
        }

        String carId = carTable.getValueAt(row, 0).toString();
        Car selected = findCar(carId);

        if (selected == null || !selected.status.equals("Available")) {
            JOptionPane.showMessageDialog(this, "Selected car is not available.");
            return;
        }

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField customerName = new JTextField();
        JTextField days = new JTextField("1");
        JTextField pickup = new JTextField("Chennai");
        JTextField returnLocation = new JTextField("Chennai");

        form.add(new JLabel("Customer Name:"));
        form.add(customerName);
        form.add(new JLabel("Rental Days:"));
        form.add(days);
        form.add(new JLabel("Pickup Location:"));
        form.add(pickup);
        form.add(new JLabel("Return Location:"));
        form.add(returnLocation);

        int option = JOptionPane.showConfirmDialog(
                this, form, "Book " + selected.brand + " " + selected.model,
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int rentalDays = Integer.parseInt(days.getText().trim());
            if (rentalDays <= 0 || customerName.getText().trim().isEmpty()) {
                throw new NumberFormatException();
            }

            double amount = rentalDays * selected.pricePerDay;
            String bookingId = String.format("B%03d", bookings.size() + 1);

            bookings.add(new Booking(
                    bookingId, customerName.getText().trim(),
                    selected.id, selected.brand + " " + selected.model,
                    rentalDays, amount, "Pending Payment"
            ));

            selected.status = "Booked";
            refreshCarTable(cars);
            refreshReservationTable();

            JOptionPane.showMessageDialog(this,
                    "Booking created successfully!\n"
                            + "Booking ID: " + bookingId + "\n"
                            + "Amount: ₹" + String.format("%.2f", amount)
                            + "\nPlease complete payment.");

            showScreen("PAYMENT");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid customer name and rental days.");
        }
    }

    private JPanel createFleetPanel() {
        JPanel panel = basePanel("Fleet Management");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        top.setBackground(Color.WHITE);

        JButton add = createButton("Add Car", GREEN);
        JButton update = createButton("Update Status", BLUE);
        JButton delete = createButton("Delete Car", RED);

        top.add(add);
        top.add(update);
        top.add(delete);
        panel.add(top, BorderLayout.NORTH);

        fleetTable = new JTable();
        refreshFleetTable();
        panel.add(new JScrollPane(fleetTable), BorderLayout.CENTER);

        add.addActionListener(e -> addCarDialog());
        update.addActionListener(e -> updateCarStatus());
        delete.addActionListener(e -> deleteCar());
        return panel;
    }

    private void addCarDialog() {
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField brand = new JTextField();
        JTextField model = new JTextField();
        JTextField category = new JTextField();
        JTextField price = new JTextField();

        form.add(new JLabel("Brand:"));
        form.add(brand);
        form.add(new JLabel("Model:"));
        form.add(model);
        form.add(new JLabel("Category:"));
        form.add(category);
        form.add(new JLabel("Price / Day:"));
        form.add(price);
        form.add(new JLabel("Status:"));
        form.add(new JLabel("Available"));

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add New Car", JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            double p = Double.parseDouble(price.getText().trim());
            if (brand.getText().trim().isEmpty()
                    || model.getText().trim().isEmpty()
                    || category.getText().trim().isEmpty() || p <= 0) {
                throw new NumberFormatException();
            }

            String id = String.format("C%03d", cars.size() + 101);
            cars.add(new Car(id, brand.getText().trim(), model.getText().trim(),
                    category.getText().trim(), p, "Available"));

            refreshFleetTable();
            refreshCarTable(cars);
            JOptionPane.showMessageDialog(this, "Car added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid car details.");
        }
    }

    private void updateCarStatus() {
        int row = fleetTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a car first.");
            return;
        }

        String id = fleetTable.getValueAt(row, 0).toString();
        Car car = findCar(id);
        if (car == null) {
            return;
        }

        String[] options = {"Available", "Booked", "Maintenance"};
        String status = (String) JOptionPane.showInputDialog(
                this, "Select new status:", "Update Car Status",
                JOptionPane.PLAIN_MESSAGE, null, options, car.status
        );

        if (status != null) {
            car.status = status;
            refreshFleetTable();
            refreshCarTable(cars);
        }
    }

    private void deleteCar() {
        int row = fleetTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a car first.");
            return;
        }

        String id = fleetTable.getValueAt(row, 0).toString();
        Car car = findCar(id);

        if (car != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    this, "Delete " + car.brand + " " + car.model + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                cars.remove(car);
                refreshFleetTable();
                refreshCarTable(cars);
            }
        }
    }

    private JPanel createPaymentPanel() {
        JPanel panel = basePanel("Payment Processing");
        JPanel form = new JPanel(new GridLayout(6, 2, 12, 12));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(30, 100, 30, 100));

        JComboBox<String> bookingBox = new JComboBox<>();
        JTextField amount = new JTextField();
        amount.setEditable(false);

        JComboBox<String> method = new JComboBox<>(
                new String[]{"UPI", "Debit Card", "Credit Card", "Cash"}
        );
        JTextField transaction = new JTextField();

        JButton pay = createButton("Process Payment", GREEN);
        JButton refresh = createButton("Refresh Bookings", BLUE);

        populateBookingBox(bookingBox);

        form.add(new JLabel("Booking ID:"));
        form.add(bookingBox);
        form.add(new JLabel("Amount:"));
        form.add(amount);
        form.add(new JLabel("Payment Method:"));
        form.add(method);
        form.add(new JLabel("Transaction ID:"));
        form.add(transaction);
        form.add(refresh);
        form.add(pay);

        JLabel info = new JLabel(
                "<html><b>Payment Processing:</b> Select a pending booking and complete the payment.</html>"
        );
        info.setBorder(new EmptyBorder(0, 100, 10, 100));

        panel.add(info, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);

        bookingBox.addActionListener(e -> {
            Booking b = getBookingById((String) bookingBox.getSelectedItem());
            amount.setText(b == null ? "" : String.format("%.2f", b.amount));
        });

        refresh.addActionListener(e -> {
            populateBookingBox(bookingBox);
            Booking b = getBookingById((String) bookingBox.getSelectedItem());
            amount.setText(b == null ? "" : String.format("%.2f", b.amount));
        });

        pay.addActionListener(e -> {
            Booking b = getBookingById((String) bookingBox.getSelectedItem());

            if (b == null) {
                JOptionPane.showMessageDialog(this, "No booking selected.");
                return;
            }

            if (!b.status.equals("Pending Payment")) {
                JOptionPane.showMessageDialog(this, "Payment is already completed.");
                return;
            }

            if (transaction.getText().trim().isEmpty()) {
                transaction.setText("TXN" + System.currentTimeMillis());
            }

            b.status = "Paid";
            JOptionPane.showMessageDialog(this,
                    "Payment successful!\n"
                            + "Booking ID: " + b.id
                            + "\nTransaction ID: " + transaction.getText()
                            + "\nPayment Method: " + method.getSelectedItem()
                            + "\nAmount: ₹" + String.format("%.2f", b.amount));

            refreshReservationTable();
            populateBookingBox(bookingBox);
            transaction.setText("");
        });

        return panel;
    }

    private JPanel createReservationPanel() {
        JPanel panel = basePanel("Reservation & Reports");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        top.setBackground(Color.WHITE);

        JButton refresh = createButton("Refresh", BLUE);
        JButton report = createButton("Generate Report", GREEN);
        JButton cancel = createButton("Cancel Selected Booking", RED);

        top.add(refresh);
        top.add(report);
        top.add(cancel);
        panel.add(top, BorderLayout.NORTH);

        bookingTable = new JTable();
        refreshReservationTable();
        panel.add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        refresh.addActionListener(e -> refreshReservationTable());

        cancel.addActionListener(e -> {
            int row = bookingTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a booking first.");
                return;
            }

            String id = bookingTable.getValueAt(row, 0).toString();
            Booking booking = getBookingById(id);

            if (booking != null && !booking.status.equals("Cancelled")) {
                booking.status = "Cancelled";
                Car car = findCar(booking.carId);
                if (car != null) {
                    car.status = "Available";
                }

                refreshReservationTable();
                refreshCarTable(cars);
                refreshFleetTable();
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
            }
        });

        report.addActionListener(e -> showReport());
        return panel;
    }

    private void showReport() {
        int total = bookings.size();
        int paid = 0;
        int cancelled = 0;
        double revenue = 0;

        for (Booking b : bookings) {
            if (b.status.equals("Paid")) {
                paid++;
                revenue += b.amount;
            }
            if (b.status.equals("Cancelled")) {
                cancelled++;
            }
        }

        int available = 0;
        int booked = 0;
        int maintenance = 0;

        for (Car c : cars) {
            if (c.status.equals("Available")) {
                available++;
            } else if (c.status.equals("Booked")) {
                booked++;
            } else if (c.status.equals("Maintenance")) {
                maintenance++;
            }
        }

        String report = "<html>"
                + "<h2>Car Rental Report</h2>"
                + "<b>Total Cars:</b> " + cars.size() + "<br>"
                + "<b>Available Cars:</b> " + available + "<br>"
                + "<b>Booked Cars:</b> " + booked + "<br>"
                + "<b>Maintenance Cars:</b> " + maintenance + "<br><br>"
                + "<b>Total Reservations:</b> " + total + "<br>"
                + "<b>Paid Bookings:</b> " + paid + "<br>"
                + "<b>Cancelled Bookings:</b> " + cancelled + "<br>"
                + "<b>Total Revenue:</b> ₹" + String.format("%.2f", revenue)
                + "</html>";

        JOptionPane.showMessageDialog(this, report, "Rental Report",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel basePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT);
        panel.setBorder(new EmptyBorder(20, 25, 25, 25));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(NAVY);
        heading.setBorder(new EmptyBorder(0, 5, 12, 0));

        panel.add(heading, BorderLayout.NORTH);
        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isPressed() ? color.darker()
                        : getModel().isRollover() ? color.brighter() : color;

                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setPreferredSize(new Dimension(
                Math.max(125, button.getFontMetrics(button.getFont()).stringWidth(text) + 42),
                43
        ));
        return button;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setBackground(Color.WHITE);
        table.setForeground(NAVY);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(NAVY);
        table.setGridColor(new Color(230, 235, 242));
        table.setShowVerticalLines(false);
        table.getTableHeader().setBackground(NAVY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 39));
        table.getTableHeader().setOpaque(true);
        table.setFillsViewportHeight(true);
    }

    private void refreshCarTable(List<Car> list) {
        if (carTable == null) return;

        String[] columns = {"Car ID", "Brand", "Model", "Category", "Price/Day", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Car c : list) {
            model.addRow(new Object[]{
                    c.id, c.brand, c.model, c.category,
                    "₹" + String.format("%.0f", c.pricePerDay), c.status
            });
        }

        carTable.setModel(model);
        styleTable(carTable);
    }

    private void refreshFleetTable() {
        if (fleetTable == null) return;

        String[] columns = {"Car ID", "Brand", "Model", "Category", "Price/Day", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Car c : cars) {
            model.addRow(new Object[]{
                    c.id, c.brand, c.model, c.category,
                    "₹" + String.format("%.0f", c.pricePerDay), c.status
            });
        }

        fleetTable.setModel(model);
        styleTable(fleetTable);
    }

    private void refreshReservationTable() {
        if (bookingTable == null) return;

        String[] columns = {
                "Booking ID", "Customer", "Car ID", "Car", "Days", "Amount", "Status"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Booking b : bookings) {
            model.addRow(new Object[]{
                    b.id, b.customerName, b.carId, b.carName,
                    b.days, "₹" + String.format("%.2f", b.amount), b.status
            });
        }

        bookingTable.setModel(model);
        styleTable(bookingTable);
    }

    private void refreshCustomerTable() {
        if (customerTable == null) return;

        String[] columns = {"Customer ID", "Name", "Mobile", "Driving License"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Customer c : customers) {
            model.addRow(new Object[]{c.id, c.name, c.phone, c.license});
        }

        customerTable.setModel(model);
        styleTable(customerTable);
    }

    private void populateBookingBox(JComboBox<String> box) {
        box.removeAllItems();
        for (Booking b : bookings) {
            if (b.status.equals("Pending Payment")) {
                box.addItem(b.id);
            }
        }
    }

    private Car findCar(String id) {
        for (Car c : cars) {
            if (c.id.equals(id)) return c;
        }
        return null;
    }

    private Booking getBookingById(String id) {
        if (id == null) return null;

        for (Booking b : bookings) {
            if (b.id.equals(id)) return b;
        }
        return null;
    }

    private void showScreen(String screen) {
        cardLayout.show(mainPanel, screen);
        refreshAllTables();
    }

    private void refreshAllTables() {
        refreshCarTable(cars);
        refreshFleetTable();
        refreshReservationTable();
        refreshCustomerTable();
    }

    static class Car {
        String id, brand, model, category, status;
        double pricePerDay;

        Car(String id, String brand, String model, String category,
            double pricePerDay, String status) {
            this.id = id;
            this.brand = brand;
            this.model = model;
            this.category = category;
            this.pricePerDay = pricePerDay;
            this.status = status;
        }
    }

    static class Customer {
        String id, name, phone, license;

        Customer(String id, String name, String phone, String license) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.license = license;
        }
    }

    static class Booking {
        String id, customerName, carId, carName, status;
        int days;
        double amount;

        Booking(String id, String customerName, String carId, String carName,
                int days, double amount, String status) {
            this.id = id;
            this.customerName = customerName;
            this.carId = carId;
            this.carName = carName;
            this.days = days;
            this.amount = amount;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default Swing look and feel.
            }

            new CarRentalSystem().setVisible(true);
        });
    }
}

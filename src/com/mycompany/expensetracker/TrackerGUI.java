package com.mycompany.expensetracker;

import com.mycompany.expensetracker.model.Category;
import com.mycompany.expensetracker.model.Transaction;
import com.mycompany.expensetracker.service.ExpenseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TrackerGUI extends JFrame {

    private ExpenseService expenseService;
    private JTextArea displayArea;
    private JTextField dateField, descriptionField, amountField;
    private JComboBox<String> typeComboBox;
    private JComboBox<Category> categoryComboBox;

    public TrackerGUI() {
        this.expenseService = new ExpenseService();
        setTitle("Personal Finance Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createUI();
        refreshDisplay();
    }

    private void createUI() {
        // Main panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Transaction"));
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().toString());
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Type (Expense/Income):"));
        typeComboBox = new JComboBox<>(new String[] { "Expense", "Income" });
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(Category.values());
        inputPanel.add(categoryComboBox);

        JButton addButton = new JButton("Add Transaction");
        inputPanel.add(new JLabel()); // Spacer
        inputPanel.add(addButton);

        // Action listener for the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });

        // Main display area for transactions
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("All Transactions"));

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addTransaction() {
        try {
            LocalDate date;
            try {
                date = LocalDate.parse(dateField.getText().trim());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Date must be in YYYY-MM-DD format.", "Invalid Date",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String description = descriptionField.getText().trim();
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Invalid Description",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0.", "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String type = (String) typeComboBox.getSelectedItem();
            Category category = (Category) categoryComboBox.getSelectedItem();

            if (type == null || category == null) {
                JOptionPane.showMessageDialog(this, "Please select both type and category.", "Invalid Selection",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            expenseService.addTransaction(date, description, amount, type, category.name());

            // Clear fields after adding
            dateField.setText(LocalDate.now().toString());
            descriptionField.setText("");
            amountField.setText("");
            typeComboBox.setSelectedIndex(0);
            categoryComboBox.setSelectedIndex(0);

            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Transaction added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDisplay() {
        displayArea.setText(""); // Clear previous text
        List<Transaction> transactions = expenseService.getAllTransactions();
        if (transactions.isEmpty()) {
            displayArea.setText("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                displayArea.append(t.toString() + "\n");
            }
        }
    }
}
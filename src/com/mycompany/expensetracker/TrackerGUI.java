package com.mycompany.expensetracker;

import com.mycompany.expensetracker.model.Transaction;
import com.mycompany.expensetracker.service.ExpenseService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class TrackerGUI extends JFrame {

    private ExpenseService expenseService;
    private JTextArea displayArea;
    private JTextField dateField, descriptionField, amountField, typeField, categoryField;

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
        typeField = new JTextField();
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        
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
            LocalDate date = LocalDate.parse(dateField.getText());
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String type = typeField.getText();
            String category = categoryField.getText();

            expenseService.addTransaction(date, description, amount, type, category);
            
            // Clear fields after adding
            dateField.setText(LocalDate.now().toString());
            descriptionField.setText("");
            amountField.setText("");
            typeField.setText("");
            categoryField.setText("");
            
            refreshDisplay();
            JOptionPane.showMessageDialog(this, "Transaction added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
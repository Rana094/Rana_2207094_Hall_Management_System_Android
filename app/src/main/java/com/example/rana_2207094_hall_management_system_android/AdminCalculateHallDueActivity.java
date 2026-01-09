package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AdminCalculateHallDueActivity extends AppCompatActivity {

    private Spinner monthSpinner;
    private EditText amountField;
    private Button generateBtn, viewBillsBtn, goBackBtn;

    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_calculate_hall_due);

        firebaseManager = new FirebaseManager();

        monthSpinner = findViewById(R.id.monthSpinner);
        amountField = findViewById(R.id.amountField);
        generateBtn = findViewById(R.id.generateBtn);
        viewBillsBtn = findViewById(R.id.viewBillsBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupMonthSpinner();
        }

        setupActions();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupMonthSpinner() {
        List<String> months = new ArrayList<>();
        YearMonth current = YearMonth.now();

        for (int i = 0; i < 12; i++) {
            months.add(current.plusMonths(i).toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }

    private void setupActions() {

        goBackBtn.setOnClickListener(v -> finish());

        viewBillsBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Navigating to View Bills...", Toast.LENGTH_SHORT).show();

             Intent intent = new Intent(AdminCalculateHallDueActivity.this, AdminViewHallBillsActivity.class);
             startActivity(intent);
        });

        generateBtn.setOnClickListener(v -> {
            String selectedMonth = monthSpinner.getSelectedItem().toString();
            String amountText = amountField.getText().toString();

            if (selectedMonth.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(this, "Please select month and enter amount", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int amount = Integer.parseInt(amountText);

                firebaseManager.generateMonthlyBill(selectedMonth, amount, new DatabaseCallback() {
                    @Override
                    public void checkResult(boolean isSuccess) {
                        if (isSuccess) {
                            Toast.makeText(AdminCalculateHallDueActivity.this, "Hall bills generated successfully!", Toast.LENGTH_LONG).show();
                            amountField.setText(""); // Clear field
                        } else {
                            Toast.makeText(AdminCalculateHallDueActivity.this, "Failed to generate bills", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(AdminCalculateHallDueActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Amount must be a valid number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
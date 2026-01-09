package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentHallDuesActivity extends AppCompatActivity implements HallBillAdapter.OnBillListener {

    private RecyclerView recyclerView;
    private TextView totalDueLabel;
    private Button payBtn, goBackBtn;

    private HallBillAdapter adapter;

    private FirebaseManager firebaseManager;
    private int studentRoll;
    private HallBill selectedBill = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_hall_dues);

        studentRoll = getIntent().getIntExtra("LOGGED_IN_ROLL", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalDueLabel = findViewById(R.id.totalDueLabel);
        payBtn = findViewById(R.id.payBtn);
        goBackBtn = findViewById(R.id.goBackBtn);

        firebaseManager = new FirebaseManager();

        if (studentRoll != -1) {
            loadUnpaidBills();
        } else {
            Toast.makeText(this, "Error: Invalid Roll", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupActions();
    }

    private void loadUnpaidBills() {
        firebaseManager.getUnpaidBillsForStudent(studentRoll, new DatabaseCallback() {
            @Override
            public void onHallBillsReceived(List<HallBill> bills) {
                if (bills != null) {

                    adapter = new HallBillAdapter(bills, StudentHallDuesActivity.this);
                    recyclerView.setAdapter(adapter);
                    calculateTotal(bills);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentHallDuesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateTotal(List<HallBill> bills) {
        int total = 0;
        for (HallBill bill : bills) {
            total += bill.getAmount();
        }
        totalDueLabel.setText(total + " Tk");
    }

    private void setupActions() {
        goBackBtn.setOnClickListener(v -> finish());

        payBtn.setOnClickListener(v -> {
            if (selectedBill == null) {
                Toast.makeText(this, "Select a bill to pay", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseManager.markBillAsPaid(studentRoll, selectedBill.getMonth(), new DatabaseCallback() {
                @Override
                public void checkResult(boolean isSuccess) {
                    if (isSuccess) {
                        Toast.makeText(StudentHallDuesActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                        selectedBill = null;
                        loadUnpaidBills();
                    } else {
                        Toast.makeText(StudentHallDuesActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(StudentHallDuesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onBillClick(int position) {
        selectedBill = adapter.getSelectedBill();
    }
}
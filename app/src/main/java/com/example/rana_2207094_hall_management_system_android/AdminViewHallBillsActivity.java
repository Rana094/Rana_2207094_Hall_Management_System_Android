package com.example.rana_2207094_hall_management_system_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminViewHallBillsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HallBillAdapter adapter;
    private FirebaseManager firebaseManager;
    private Button goBackBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_hall_bills);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goBackBtn = findViewById(R.id.goBackBtn);

        firebaseManager = new FirebaseManager();

        loadHallBills();

        goBackBtn.setOnClickListener(v -> finish());
    }

    private void loadHallBills() {
        firebaseManager.getAllHallBills(new DatabaseCallback() {
            @Override
            public void onHallBillsReceived(List<HallBill> bills) {
                if (bills != null && !bills.isEmpty()) {
                    adapter = new HallBillAdapter(bills);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminViewHallBillsActivity.this, "No bills generated yet.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(AdminViewHallBillsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
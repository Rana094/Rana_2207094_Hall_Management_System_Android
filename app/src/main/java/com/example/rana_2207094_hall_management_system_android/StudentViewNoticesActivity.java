package com.example.rana_2207094_hall_management_system_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentViewNoticesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private FirebaseManager firebaseManager;
    private Button goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_notices);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goBackBtn = findViewById(R.id.goBackBtn);

        firebaseManager = new FirebaseManager();

        loadNotices();

        goBackBtn.setOnClickListener(v -> finish());
    }

    private void loadNotices() {
        firebaseManager.getAllNotices(new DatabaseCallback() {
            @Override
            public void onNoticeListReceived(List<Notice> notices) {
                if (notices != null && !notices.isEmpty()) {
                    adapter = new NoticeAdapter(notices);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(StudentViewNoticesActivity.this, "No notices found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentViewNoticesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
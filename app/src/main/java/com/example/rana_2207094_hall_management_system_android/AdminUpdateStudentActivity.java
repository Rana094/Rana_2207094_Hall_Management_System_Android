package com.example.rana_2207094_hall_management_system_android;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminUpdateStudentActivity extends AppCompatActivity {

    private EditText nameTxt, passwordTxt, confirmpasswordTxt, emailTxt, addressTxt, departmentTxt, cgpaTxt, birthDateTxt;
    private ImageView imageView;
    private Button chooseImageBtn, updateBtn, goBack;

    private FirebaseManager firebaseManager;
    private int studentRoll;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_student);

        studentRoll = getIntent().getIntExtra("ROLL_TO_UPDATE", -1);

        firebaseManager = new FirebaseManager();

        initViews();

        if (studentRoll != -1) {
            loadStudentData();
        } else {
            Toast.makeText(this, "Error: No Roll Number", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupActions();
    }

    private void initViews() {
        nameTxt = findViewById(R.id.nameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmpasswordTxt = findViewById(R.id.confirmpasswordTxt);
        emailTxt = findViewById(R.id.emailTxt);
        addressTxt = findViewById(R.id.addressTxt);
        departmentTxt = findViewById(R.id.departmentTxt);
        cgpaTxt = findViewById(R.id.cgpaTxt);
        birthDateTxt = findViewById(R.id.birthDateTxt);
        imageView = findViewById(R.id.imageView);

        chooseImageBtn = findViewById(R.id.chooseImageBtn);
        updateBtn = findViewById(R.id.updateBtn);
        goBack = findViewById(R.id.goBack);
    }

    private void loadStudentData() {
        firebaseManager.getStudentByRoll(studentRoll, new DatabaseCallback() {
            @Override
            public void onStudentReceived(Student student) {
                if (student != null) {
                    currentStudent = student;

                    nameTxt.setText(student.getName());
                    emailTxt.setText(student.getEmail());
                    addressTxt.setText(student.getAddress());
                    departmentTxt.setText(student.getDept());
                    cgpaTxt.setText(student.getCgpa());
                    birthDateTxt.setText(student.getBirthdate());

                }
            }
        });
    }

    private void setupActions() {

        birthDateTxt.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {

                        String date = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        birthDateTxt.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        goBack.setOnClickListener(v -> finish());

        chooseImageBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Image picking not implemented in this snippet", Toast.LENGTH_SHORT).show();

        });

        updateBtn.setOnClickListener(v -> performUpdate());
    }

    private void performUpdate() {
        if (currentStudent == null) return;

        String name = nameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String address = addressTxt.getText().toString();
        String dept = departmentTxt.getText().toString();
        String cgpa = cgpaTxt.getText().toString();
        String birthDate = birthDateTxt.getText().toString();

        String newPass = passwordTxt.getText().toString();
        String confirmPass = confirmpasswordTxt.getText().toString();
        String passwordToSave;

        if (newPass.isEmpty() && confirmPass.isEmpty()) {
            passwordToSave = currentStudent.getPassword();
        } else {
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            passwordToSave = newPass;
        }

        if (birthDate.isEmpty()) {
            Toast.makeText(this, "Please select birthdate", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("email", email);
        updates.put("address", address);
        updates.put("dept", dept);
        updates.put("cgpa", cgpa);
        updates.put("birthdate", birthDate);
        updates.put("password", passwordToSave);

        firebaseManager.updateStudentData(studentRoll, updates, new DatabaseCallback() {
            @Override
            public void checkResult(boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(AdminUpdateStudentActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminUpdateStudentActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
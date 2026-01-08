package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameTxtStudent;
    private EditText passwordTxtStudent;
    private CheckBox checkBox;
    private Button loginBtnStudent;
    private Button gotoAdminPageBtn;
    private Button gotoRegisterPageBtn;

    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseManager = new FirebaseManager();

        usernameTxtStudent = findViewById(R.id.usernameTxtStudent);
        passwordTxtStudent = findViewById(R.id.passwordTxtStudent);
        checkBox = findViewById(R.id.checkBox);
        loginBtnStudent = findViewById(R.id.loginBtnStudent);
        gotoAdminPageBtn = findViewById(R.id.gotoAdminPageBtn);
        gotoRegisterPageBtn = findViewById(R.id.gotoRegisterPageBtn);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    passwordTxtStudent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {

                    passwordTxtStudent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                passwordTxtStudent.setSelection(passwordTxtStudent.getText().length());
            }
        });

        loginBtnStudent.setOnClickListener(v -> performLogin());

        gotoAdminPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });

        gotoRegisterPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String rollText = usernameTxtStudent.getText().toString().trim();
        String password = passwordTxtStudent.getText().toString();

        if (rollText.isEmpty() || password.isEmpty()) {
            showAlert("Warning", "Username and password are required");
            return;
        }

        int roll;
        try {
            roll = Integer.parseInt(rollText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Roll must be a number");
            return;
        }

        loginBtnStudent.setEnabled(false);
        loginBtnStudent.setText("Checking...");

        firebaseManager.getStudentByRoll(roll, new DatabaseCallback() {
            @Override
            public void onStudentReceived(Student student) {
                // Reset button
                loginBtnStudent.setEnabled(true);
                loginBtnStudent.setText("Login");

                if (student == null) {

                    showAlert("Error", "User doesn't exist");
                } else {

                    if (!student.getPassword().equals(password)) {
                        showAlert("Error", "Password doesn't match with username");
                    }

                    else if ("false".equals(student.getStatus())) {
                        showAlert("Error", "Admin has not Approved Yet, kindly contact the Hall Office");
                    }

                    else {
                        gotoStudentProfile(student.getRoll());
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                loginBtnStudent.setEnabled(true);
                loginBtnStudent.setText("Login");
                showAlert("Connection Error", e.getMessage());
            }
        });
    }

    private void gotoStudentProfile(int roll) {
        Intent intent = new Intent(MainActivity.this, StudentHomeActivity.class);
        intent.putExtra("LOGGED_IN_ROLL", roll);
        startActivity(intent);
        finish();
    }

    private void showAlert(String title, String msg) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}
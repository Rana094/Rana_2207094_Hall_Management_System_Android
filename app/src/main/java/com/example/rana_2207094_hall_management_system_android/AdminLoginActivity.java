package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etUsernameAdmin;
    private EditText etPasswordAdmin;
    private CheckBox cbShowPassword;
    private Button btnLoginAdmin;
    private Button btnHome;

    private final String PASS = "admin";
    private final String USERNAME = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etUsernameAdmin = findViewById(R.id.etUsernameAdmin);
        etPasswordAdmin = findViewById(R.id.etPasswordAdmin);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        btnHome = findViewById(R.id.btnHome);

        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    etPasswordAdmin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {

                    etPasswordAdmin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etPasswordAdmin.setSelection(etPasswordAdmin.getText().length());
            }
        });

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHome();
            }
        });
    }

    private void performLogin() {
        String inputUsername = etUsernameAdmin.getText().toString();
        String inputPassword = etPasswordAdmin.getText().toString();

        if (!inputPassword.equals(PASS) || !inputUsername.equals(USERNAME)) {
            showAlert("Error", "Passwords or Username do not match");
            return;
        }

        Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
        startActivity(intent);

    }

    private void gotoHome() {
        Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showAlert(String title, String msg) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}
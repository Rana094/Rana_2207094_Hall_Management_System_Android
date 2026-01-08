package com.example.rana_2207094_hall_management_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etRollNumber, etPassword;
    private CheckBox cbShowPassword;
    private Button btnLogin, btnRegister, btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etRollNumber = findViewById(R.id.etRollNumber);
        etPassword = findViewById(R.id.etPassword);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegister = findViewById(R.id.btnGoToRegister);

        btnAdminLogin = findViewById(R.id.btnGoToAdmin);

        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {

                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btnLogin.setOnClickListener(v -> {
            String roll = etRollNumber.getText().toString();
            String pass = etPassword.getText().toString();

            if(roll.isEmpty() || pass.isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(MainActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, StudentHomeActivity.class);
            intent.putExtra("USER_ROLL", roll); // Passing the roll number to the next screen
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Going to Register Page", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnAdminLogin.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Going to Admin Login", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}
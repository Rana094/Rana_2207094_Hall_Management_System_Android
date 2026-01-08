package com.example.rana_2207094_hall_management_system_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameTxt, passwordTxt, confirmpasswordTxt, rollTxt, emailTxt, addressTxt, departmentTxt, cgpaTxt, birthDateTxt;
    private ImageView imageView;
    private Button chooseImageBtn, signupBtn, gotoHomeBtn;

    private String base64ImageString = null;

    private FirebaseManager firebaseManager;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    try {
                        imageView.setImageURI(uri);


                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        float ratio = (float) width / height;
                        if (width > 800) {
                            width = 800;
                            height = (int) (width / ratio);
                            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                        byte[] byteArr = stream.toByteArray();
                        base64ImageString = Base64.encodeToString(byteArr, Base64.DEFAULT);

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseManager = new FirebaseManager();

        nameTxt = findViewById(R.id.nameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmpasswordTxt = findViewById(R.id.confirmpasswordTxt);
        rollTxt = findViewById(R.id.rollTxt);
        emailTxt = findViewById(R.id.emailTxt);
        addressTxt = findViewById(R.id.addressTxt);
        departmentTxt = findViewById(R.id.departmentTxt);
        cgpaTxt = findViewById(R.id.cgpaTxt);
        birthDateTxt = findViewById(R.id.birthDateTxt);

        imageView = findViewById(R.id.imageView);
        chooseImageBtn = findViewById(R.id.chooseImageBtn);
        signupBtn = findViewById(R.id.signupBtn);
        gotoHomeBtn = findViewById(R.id.gotoHomeBtn);

        chooseImageBtn.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        birthDateTxt.setOnClickListener(v -> showDatePicker());

        signupBtn.setOnClickListener(v -> submitClick());

        gotoHomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, day) -> birthDateTxt.setText(year + "-" + (month + 1) + "-" + day),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void submitClick() {
        if (isEmpty(rollTxt) || isEmpty(nameTxt) || isEmpty(passwordTxt)) {
            showAlert("Warning", "Please fill all fields");
            return;
        }



        if (!passwordTxt.getText().toString().equals(confirmpasswordTxt.getText().toString())) {
            showAlert("Error", "Passwords do not match");
            return;
        }

        int roll;
        try {
            roll = Integer.parseInt(rollTxt.getText().toString());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Roll Number");
            return;
        }

        signupBtn.setEnabled(false);
        signupBtn.setText("Checking...");

        firebaseManager.checkRollExists(roll, new DatabaseCallback() {
            @Override
            public void checkResult(boolean exists) {
                if (exists) {
                    signupBtn.setEnabled(true);
                    signupBtn.setText("Sign Up");
                    showAlert("Error", "Roll number already registered!");
                } else {

                    saveDataToFirestore(roll);
                }
            }
            @Override
            public void onError(Exception e) {
                signupBtn.setEnabled(true);
                showAlert("Connection Error", e.getMessage());
            }
        });
    }

    private void saveDataToFirestore(int roll) {
        signupBtn.setText("Saving...");

        Student student = new Student(
                roll,
                nameTxt.getText().toString(),
                emailTxt.getText().toString(),
                addressTxt.getText().toString(),
                departmentTxt.getText().toString(),
                cgpaTxt.getText().toString(),
                birthDateTxt.getText().toString(),
                base64ImageString,
                passwordTxt.getText().toString()
        );

        firebaseManager.saveStudent(student, new DatabaseCallback() {
            @Override
            public void checkResult(boolean success) {
                if (success) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    signupBtn.setEnabled(true);
                    signupBtn.setText("Sign Up");
                    showAlert("Error", "Save failed.");
                }
            }

            @Override
            public void onError(Exception e) {
                signupBtn.setEnabled(true);
                signupBtn.setText("Sign Up");

                if(e.getMessage().contains("maximum allowed size")) {
                    showAlert("Error", "Image is too big. Please choose a smaller one.");
                } else {
                    showAlert("Error", e.getMessage());
                }
            }
        });
    }

    private boolean isEmpty(EditText et) {
        return et.getText().toString().trim().isEmpty();
    }

    private void showAlert(String title, String msg) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setPositiveButton("OK", null).show();
    }
}
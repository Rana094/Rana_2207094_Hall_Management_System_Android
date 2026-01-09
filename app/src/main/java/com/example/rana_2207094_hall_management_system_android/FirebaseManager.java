package com.example.rana_2207094_hall_management_system_android;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    private final DatabaseReference dbRef;

    private static final String NODE_STUDENTS = "students";
    private static final String NODE_NOTICES = "notices";

    public FirebaseManager() {

        dbRef = FirebaseDatabase.getInstance().getReference();
    }


    public void saveStudent(Student student, DatabaseCallback callback) {

        student.setStatus("false");
        student.setRemoveStatus("false");

        dbRef.child(NODE_STUDENTS).child(String.valueOf(student.getRoll()))
                .setValue(student)
                .addOnSuccessListener(aVoid -> callback.checkResult(true))
                .addOnFailureListener(callback::onError);
    }

    public void deleteStudent(int roll, DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .removeValue()
                .addOnSuccessListener(aVoid -> callback.checkResult(true))
                .addOnFailureListener(callback::onError);
    }

    public void getStudentByRoll(int roll, DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Student s = snapshot.getValue(Student.class);
                            callback.onStudentReceived(s);
                        } else {
                            callback.onStudentReceived(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error.toException());
                    }
                });
    }

    public void updateStudentStatus(int roll, String status, String removeStatus) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", status);
        updates.put("removeStatus", removeStatus);

        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .updateChildren(updates);
    }

    public void getActiveStudents(DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).orderByChild("status").equalTo("true")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Student> list = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            list.add(child.getValue(Student.class));
                        }
                        callback.onStudentListReceived(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error.toException());
                    }
                });
    }

    public void insertNotice(String title, String message, DatabaseCallback callback) {
        String key = dbRef.child(NODE_NOTICES).push().getKey();

        Map<String, Object> notice = new HashMap<>();
        notice.put("id", key);
        notice.put("title", title);
        notice.put("message", message);
        notice.put("created_at", System.currentTimeMillis());

        if (key != null) {
            dbRef.child(NODE_NOTICES).child(key).setValue(notice)
                    .addOnSuccessListener(aVoid -> callback.checkResult(true))
                    .addOnFailureListener(callback::onError);
        }
    }


    public void login(int roll, String password, DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Check password
                            Student s = snapshot.getValue(Student.class);
                            if (s != null && s.getPassword().equals(password)) {
                                callback.checkResult(true);
                            } else {
                                callback.checkResult(false);
                            }
                        } else {
                            callback.checkResult(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.checkResult(false);
                    }
                });
    }

    public void checkRollExists(int roll, DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        callback.checkResult(snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error.toException());
                    }
                });
    }

    public void updateStudentData(int roll, Map<String, Object> updates, DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .updateChildren(updates)
                .addOnSuccessListener(aVoid -> callback.checkResult(true))
                .addOnFailureListener(e -> callback.onError(e));
    }

    public void getPendingStudents(final DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Student> pendingList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Student student = data.getValue(Student.class);
                    if (student != null) {
                        // Check if status is "false"
                        String status = String.valueOf(student.getStatus());
                        if (status.equalsIgnoreCase("false")) {
                            pendingList.add(student);
                        }
                    }
                }
                callback.onStudentListReceived(pendingList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    public void approveStudent(int roll, final DatabaseCallback callback) {
        dbRef.child(NODE_STUDENTS).child(String.valueOf(roll))
                .child("status").setValue("true")
                .addOnSuccessListener(aVoid -> callback.checkResult(true))
                .addOnFailureListener(e -> callback.onError(e));
    }
}
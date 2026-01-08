package com.example.rana_2207094_hall_management_system_android;

import java.util.List;

public interface FirestoreCallback {
    default void onStudentListReceived(List<Student> students) {}
//    default void onNoticeListReceived(List<Notice> notices) {}
//    default void onBillListReceived(List<HallBillMonth> bills) {}
    default void onStudentReceived(Student student) {}
    default void checkResult(boolean exists) {}
    default void onError(Exception e) {}
}
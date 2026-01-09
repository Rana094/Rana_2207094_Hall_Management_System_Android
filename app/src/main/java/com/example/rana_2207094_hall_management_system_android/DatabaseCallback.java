package com.example.rana_2207094_hall_management_system_android;

import java.util.List;

public interface DatabaseCallback {
    default void onStudentListReceived(List<Student> students) {}
    default void onHallBillsReceived(List<HallBill> bills) {}
    default void onStudentReceived(Student student) {}
    default void checkResult(boolean exists) {}
    default void onError(Exception e) {}

    default void onTotalDueReceived(int amount) {}

    default void onNoticeListReceived(List<Notice> notices) {}
}
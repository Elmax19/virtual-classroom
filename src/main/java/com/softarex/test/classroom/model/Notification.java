package com.softarex.test.classroom.model;

public class Notification {
    private String student;
    private String message;

    public Notification(String student, String message) {
        this.student = student;
        this.message = message;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

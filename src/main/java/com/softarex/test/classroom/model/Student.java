package com.softarex.test.classroom.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    private String login;
    private boolean hand;

    public Student(String login, boolean hand) {
        this.login = login;
        this.hand = hand;
    }

    public Student() {

    }

    public String getLogin() {
        return login;
    }

    public boolean isHand() {
        return hand;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHandUp(boolean hand) {
        this.hand = hand;
    }
}

package com.softarex.test.classroom.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Student entity model
 * @author Elmax19
 * @version 1.0
 */
@Entity
public class Student {
    /**
     * database columns
     */
    @Id
    private String login;
    private boolean hand;

    /**
     * constructor to initialize {@link Student#login} and {@link Student#hand}
     * @param login student login value
     * @param hand student hand status(is rose or not)
     */
    public Student(String login, boolean hand) {
        this.login = login;
        this.hand = hand;
    }

    /**
     * default constructor
     */
    public Student() {

    }

    /**
     * {@link Student#login} getter
     * @return current student login
     */
    public String getLogin() {
        return login;
    }

    /**
     * {@link Student#hand} getter
     * @return is student hand rose
     */
    public boolean isHand() {
        return hand;
    }

    /**
     * @link Student#login} setter
     * @param login new student login value
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @link Student#hand} setter
     * @param hand new student hand value
     */
    public void setHandUp(boolean hand) {
        this.hand = hand;
    }
}

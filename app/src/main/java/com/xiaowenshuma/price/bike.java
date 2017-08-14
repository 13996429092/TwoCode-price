package com.xiaowenshuma.price;

/**
 * Created by Mac on 17/8/10.
 */

public class bike {
    private String number;
    private String password;

    public bike() {
    }

    public bike(String number, String password) {
        this.number = number;
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

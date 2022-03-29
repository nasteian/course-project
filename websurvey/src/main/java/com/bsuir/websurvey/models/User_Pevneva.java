package com.bsuir.websurvey.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    private String password;
    private String login;
    private Boolean role;
    private String code_phrase;

    public User_Pevneva() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public String getCode_phrase() {
        return code_phrase;
    }

    public void setCode_phrase(String code_phrase) {
        this.code_phrase = code_phrase;
    }
}

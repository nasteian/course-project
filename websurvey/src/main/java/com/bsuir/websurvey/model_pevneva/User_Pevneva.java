package com.websurvey.websurvey_pevneva.model_pevneva;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String passwordHash;
    private Boolean role;
    private String codePhraseHash;
    @Nullable
    private String sessionIdHash;

    public String getSessionIdHash() {
        return sessionIdHash;
    }

    public void setSessionIdHash(String sessionIdHash) {
        this.sessionIdHash = sessionIdHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getCodePhraseHash() {
        return codePhraseHash;
    }

    public void setCodePhraseHash(String codePhraseHash) {
        this.codePhraseHash = codePhraseHash;
    }
}

package ir.sharif.model;

import ir.sharif.model.game.DeckInfo;

import java.io.Serializable;
import java.nio.file.Path;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username, password, nickname, email, id;
    private SecurityQuestion securityQuestion;
    private DeckInfo deckInfo;

    public User(String username, String password, String nickname, String email, SecurityQuestion securityQuestion) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.deckInfo = null;
    }

    public User(String username){
        this.username = username;
    }

    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DeckInfo getDeckInfo() {
        return deckInfo;
    }

    public void setDeckInfo(DeckInfo deckInfo) {
        this.deckInfo = deckInfo;
    }
}

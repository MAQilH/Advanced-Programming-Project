package ir.sharif.model;

import java.nio.file.Path;

public class User implements Savable {
    private String username, password, nickname, email, id;
    private SecurityQuestion securityQuestion;

    public User(String username, String password, String nickname, String email, SecurityQuestion securityQuestion) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.securityQuestion = securityQuestion;
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

    @Override
    public void save(Path path) {}

    @Override
    public Savable load(Path path) {
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

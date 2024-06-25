package ir.sharif.model;

import java.io.Serializable;

public class SecurityQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String question, answer;
    SecurityQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}

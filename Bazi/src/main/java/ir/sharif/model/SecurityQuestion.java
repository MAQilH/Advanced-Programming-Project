package ir.sharif.model;

import java.io.Serializable;

public class SecurityQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

	public static final String[] questions = {
			"What is your favorite color?",
			"What is your favorite movie?",
			"What is your favorite book?",
			"What is your favorite food?",
			"What is your favorite animal?",
			"What is your favorite sport?",
			"What is your favorite song?",
			"What is your favorite band?",
			"What is your favorite game?",
			"What is your favorite TV show?"
	};

    private String question, answer;
    public SecurityQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
}


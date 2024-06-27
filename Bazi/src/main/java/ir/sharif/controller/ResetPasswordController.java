package ir.sharif.controller;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import ir.sharif.view.Regex;
import ir.sharif.view.terminal.Patterns;

import java.util.regex.Pattern;

public class ResetPasswordController {
	private static ResetPasswordController instance = null;
	private User user;

	private ResetPasswordController() {
	}
	public static ResetPasswordController getInstance() {
		if (instance == null)
			instance = new ResetPasswordController();
		return instance;
	}

	public String getQuestion() {
		return user.getSecurityQuestion().getQuestion();
	}

	public CommandResult setUser(String username) {
		User user = UserService.getInstance().getUserByUsername(username);
		if (user == null) {
			return new CommandResult(ResultCode.FAILED, "user not found");
		}

		this.user = user;
		return new CommandResult(ResultCode.ACCEPT, "user found");
	}

	public CommandResult resetPassword(String answer, String password, String passwordConfirm) {
		if (!answer.equals(user.getSecurityQuestion().getAnswer())) {
			return new CommandResult(ResultCode.FAILED, "answer is incorrect");
		}

		if (password == null || password.isEmpty()) {
			return new CommandResult(ResultCode.FAILED, "password is empty");
		}
		if (passwordConfirm == null || passwordConfirm.isEmpty()) {
			return new CommandResult(ResultCode.FAILED, "password confirm is empty");
		}
		if (!password.equals(passwordConfirm)) {
			return new CommandResult(ResultCode.FAILED, "passwords do not match");
		}

		if (!Regex.PASSWORD.getMatcher(password).matches()) {
			return new CommandResult(ResultCode.FAILED, "password is weak");
		}

		user.setPassword(password);
		return new CommandResult(ResultCode.ACCEPT, "password changed");
	}
}

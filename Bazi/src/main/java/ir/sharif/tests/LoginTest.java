package ir.sharif.tests;

import ir.sharif.controller.LoginController;
import ir.sharif.controller.RegisterController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {

	private LoginController loginController;
	private RegisterController registerController;
	private UserService userService;

	@Before
	public void setUp() {
		userService = UserService.getInstance();
		loginController = new LoginController();
		registerController = new RegisterController();

		if (userService.getUserByUsername("sohsoh") == null) {
			registerController.register("sohsoh", "Soheil@84", "Soheil@84",
				new SecurityQuestion("test", "test"), "sohsoh", "sohsoh84@gmail.com");
		}

		if (userService.getUserByUsername("aqil") == null) {
			registerController.register("aqil", "Soheil@84", "Soheil@84",
				new SecurityQuestion("test", "test"), "aqil", "aqil@gmail.com");
		}
	}

	@Test
	public void testLoginAccept() {
		CommandResult result = loginController.login("sohsoh", "Soheil@84", false);
		assertEquals(ResultCode.ACCEPT, result.statusCode());
	}

	@Test
	public void testLoginFailed() {
		CommandResult result = loginController.login("sohsoh", "Soheil@85", false);
		assertEquals(ResultCode.FAILED, result.statusCode());

		result = loginController.login("someRandomusername", "Soheil@84", false);
		assertEquals(ResultCode.NOT_FOUND, result.statusCode());
	}

	@Test
	public void testSecurityQuestion() {
		CommandResult result = loginController.login("sohsoh", "Soheil@85", false);
		assertEquals(ResultCode.FAILED, result.statusCode());

		result = loginController.login("sohsoh", "Soheil@85", false);
		assertEquals(ResultCode.FAILED, result.statusCode());
	}
}

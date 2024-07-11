package ir.sharif.tests;

import ir.sharif.controller.LoginController;
import ir.sharif.controller.ProfileController;
import ir.sharif.controller.RegisterController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterTest {

    private ProfileController profileController;
    private RegisterController registerController;
    private LoginController loginController;
    private UserService userService;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        CommandResult result = new CommandResult(ResultCode.ACCEPT, "test");
        userService = UserService.getInstance();
        registerController = new RegisterController();

        if (userService.getUserByUsername("sohsoh") == null) {
            result = registerController.register("sohsoh", "Soheil@84", "Soheil@84",
                    new SecurityQuestion("test", "test"), "sohsoh", "sohsoh84@gmail.com");
        }
        assertEquals(ResultCode.ACCEPT, result.statusCode());
    }

}

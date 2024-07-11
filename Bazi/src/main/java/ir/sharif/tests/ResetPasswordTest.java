package ir.sharif.tests;

import ir.sharif.controller.LoginController;
import ir.sharif.controller.ProfileController;
import ir.sharif.controller.RegisterController;
import ir.sharif.controller.ResetPasswordController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResetPasswordTest {

    private ProfileController profileController;
    private RegisterController registerController;
    private LoginController loginController;
    private ResetPasswordController resetPasswordController;
    private UserService userService;

    @Before
    public void setUp() {
        registerController = new RegisterController();
        userService = UserService.getInstance();
        if (userService.getUserByUsername("kookoooooo") == null) {
            registerController.register("kookoooooo", "Soheil@84", "Soheil@84",
                    new SecurityQuestion("test", "test"), "sohsoh", "kiansgzn@gmail.com");
        }
        resetPasswordController = ResetPasswordController.getInstance();
        resetPasswordController.setUser("kookoooooo");
    }

    @Test
    public void test() {
        CommandResult result = resetPasswordController.resetPassword("test", "Soheil@84", "Soheil@84");
        assertEquals(ResultCode.ACCEPT, result.statusCode());
    }

}

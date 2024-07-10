package ir.sharif.tests;

import ir.sharif.controller.LoginController;
import ir.sharif.controller.MainMenuController;
import ir.sharif.controller.ProfileController;
import ir.sharif.controller.RegisterController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileTest {

    private ProfileController profileController;
    private RegisterController registerController;
    private LoginController loginController;
    private UserService userService;

    @Before
    public void setUp() {
        userService = UserService.getInstance();
        profileController = new ProfileController();
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
    public void test() {
        User user = userService.getUserByUsername("sohsoh");
        userService.setCurrentUser(user);
        CommandResult result = profileController.showInfo();
        assertEquals(ResultCode.ACCEPT, result.statusCode());
    }

}

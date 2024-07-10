package ir.sharif.tests;

import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.User;
import ir.sharif.service.GameHistoryService;
import ir.sharif.service.TournamentService;
import ir.sharif.service.UserService;
import ir.sharif.view.controllers.Game;
import ir.sharif.view.terminal.Menu;
import ir.sharif.enums.Menus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {

    private UserService userService;
    @Before
    public void setUp() {
        userService = UserService.getInstance();
    }

    @Test
    public void test() {
        User user = userService.getUserByUsername("sohsoh");
        if(user == null) {
            user = new User("sohsoh", "Soheil@84",
                    "sohsoh", "sohsoh84@gmail.com", new SecurityQuestion("fuck", "fuck"));
            if(userService.getUserByUsername("sohsoh") == null) {
                userService.addUser(user);
            }
        }
        userService.setCurrentUser(user);
        assertEquals(user, userService.getCurrentUser());
    }

    @Test
    public void test2() {
        ArrayList<User> allusers = userService.getUsers();
        boolean sohsohExists = false;
        for(User user : allusers) {
            if (user.getUsername().equals("sohsoh")) {
                sohsohExists = true;
                break;
            }
        }
        assertTrue(sohsohExists);
    }
}
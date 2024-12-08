package uk.ac.nott.cs.comp2013.mentorapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.Optional;

/* Uses the login controller to determine whether role selected was correct */
/* Upon login before user can navigate to relevant role screen */
public class RoleSelectionTests {

    @BeforeEach
    public void setUp() {
        LoginController controller = new LoginController(null);
    }

    /* 1. User selects admin as their role */
    @Test
    public void testSetSelectedRoleAdmin() {
        /* The admin role is selected on the screen */
        LoginController.setSelectedRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, LoginController.getSelectedRole());
    }

    /* 2. User selects mentor as their role */
    @Test
    public void testSetSelectedRoleMentor() {
        /* The admin role is selected on the screen */
        LoginController.setSelectedRole(UserRole.MENTOR);
        assertEquals(UserRole.MENTOR, LoginController.getSelectedRole());
    }

    /* 3. User selects mentee as their role */
    @Test
    public void testSetSelectedRoleMentee() {
        /* The admin role is selected on the screen */
        LoginController.setSelectedRole(UserRole.MENTEE);
        assertEquals(UserRole.MENTEE, LoginController.getSelectedRole());
    }
}

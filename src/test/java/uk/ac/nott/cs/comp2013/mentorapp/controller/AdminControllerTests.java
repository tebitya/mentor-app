package uk.ac.nott.cs.comp2013.mentorapp.controller;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminControllerTests {

    @Mock
    private Repository<User, String> userRepo;
    private AdminController adminController;
    private List<Pair<String, String>> pairsResults;

    @BeforeEach
    public void setUp() {
        userRepo = mock(Repository.class);
        pairsResults = new ArrayList<>();
        adminController = new AdminController(userRepo, pairsResults);
    }

    /* Testing every function */
    /* 5 functions in AdminController */

    /* 1. Testing the function that lists mentees */
    @Test
    public void testListAllMentors(){
        /* Creating one mentee to add to the list */
        User mentee1 = mock(User.class);
        when(mentee1.getRole()).thenReturn(UserRole.MENTEE);
        /* Giving them name of mentee1 to be listed  */
        when(mentee1.getUsername()).thenReturn("mentee1");

        /* Only adding mentee1 to the list */
        List<User> mockUsers = List.of(mentee1);
        when(userRepo.selectAll()).thenReturn(mockUsers);

        /* Running the function */
        List<String> mentees = adminController.listAllMentees();

        assertEquals(1, mentees.size(), "Should only be 1 mentee");
        assertTrue(mentees.contains("mentee1"), "Mentee1 should be in list");
    }

    /* 2. Testing the function that lists mentors */
}

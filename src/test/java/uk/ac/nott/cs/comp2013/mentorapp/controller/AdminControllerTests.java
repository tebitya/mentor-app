package uk.ac.nott.cs.comp2013.mentorapp.controller;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.HasAvailability;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.time.LocalDateTime;
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
    public void testListAllMentees() {
        /* Creating one mentee to add to the list */
        User mentee1 = mock(User.class);
        when(mentee1.getRole()).thenReturn(UserRole.MENTEE);
        /* Giving them name of mentee1 to be listed */
        when(mentee1.getUsername()).thenReturn("mentee1");

        /* Only adding mentee1 to the list */
        List<User> mockUsers = List.of(mentee1);
        when(userRepo.selectAll()).thenReturn(mockUsers);

        /* Running the function */
        List<String> mentees = adminController.listAllMentees();

        assertEquals(1, mentees.size(), "Should only be 1 mentee");
        assertTrue(mentees.contains("mentee1"), "Mentee1 should be in list");
    }

    /* 2. Testing with multiple values */
    @Test
    public void testListAllMenteesMultiple() {
        User mentee1 = mock(User.class);
        when(mentee1.getRole()).thenReturn(UserRole.MENTEE);
        when(mentee1.getUsername()).thenReturn("mentee1");

        User mentee2 = mock(User.class);
        when(mentee2.getRole()).thenReturn(UserRole.MENTEE);
        /* Adding second item */
        when(mentee2.getUsername()).thenReturn("mentee2");

        when(userRepo.selectAll()).thenReturn(List.of(mentee1, mentee2));

        List<String> mentees = adminController.listAllMentees();

        assertEquals(2, mentees.size(), "Should return 2 mentees");
        assertTrue(mentees.contains("mentee1"), "Should contain mentee1");
        assertTrue(mentees.contains("mentee2"), "Should contain mentee2");
    }

    /* 3. Testing the function that lists mentors */
    @Test
    public void testListAllMentors() {
        /* As above, only adding one mentor to list */
        User mentor1 = mock(User.class);
        when(mentor1.getRole()).thenReturn(UserRole.MENTOR);
        when(mentor1.getUsername()).thenReturn("mentor1");

        List<User> mockUsers = List.of(mentor1);
        when(userRepo.selectAll()).thenReturn(mockUsers);
        /* Calling the function to test it */
        List<String> mentors = adminController.listAllMentors();

        assertEquals(1, mentors.size(), "Should only be 1 mentor");
        assertTrue(mentors.contains("mentor1"), "Mentor1 should be in list");
    }

    /* 4. Testing the function that lists mentors with empty list */
    @Test
    public void testListAllMentorsWithEmpty() {
        /* As above, only adding empty list */
        User mentor1 = mock(User.class);
        when(mentor1.getRole()).thenReturn(UserRole.MENTOR);
        when(mentor1.getUsername()).thenReturn("");

        List<User> mockUsers = List.of(mentor1);
        when(userRepo.selectAll()).thenReturn(mockUsers);
        /* Calling the function to test it */
        List<String> mentors = adminController.listAllMentors();

        assertTrue(mentors.isEmpty(), "Only empty string in result");
    }


    /* 5. Testing the function that lists mentors with null value */
    @Test
    public void testListAllMentorsWithNull() {
        /* As above, only adding empty list */
        User mentor1 = mock(User.class);
        when(mentor1.getRole()).thenReturn(UserRole.MENTOR);
        when(mentor1.getUsername()).thenReturn(null);

        List<User> mockUsers = List.of(mentor1);
        when(userRepo.selectAll()).thenReturn(mockUsers);
        /* Calling the function to test it */
        List<String> mentors = adminController.listAllMentors();

        assertTrue(mentors.isEmpty(), "Only empty string in result");
    }

    /* 6.  Testing to see if function returns mentors with >= 6 months availability */
    @Test
    public void testListAllMentorsInvalid() {
        Mentor mentor = mock(Mentor.class);
        when(mentor.getRole()).thenReturn(UserRole.MENTOR);
        
        /* Sets start as now, and end as in 5 months */
        when(mentor.getStartAvailability()).thenReturn(LocalDateTime.now());
        when(mentor.getEndAvailability()).thenReturn(LocalDateTime.now().plusMonths(5));

        /* Selected from list */
        when(userRepo.selectAll()).thenReturn(List.of(mentor));

        /* Function called here */
        List<String> mentors = adminController.listAllMentors();

        assertTrue(mentors.isEmpty(), "Should return no mentors");
    }


}

package uk.ac.nott.cs.comp2013.mentorapp.controller;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Mentor mentor1 = mock(Mentor.class);
        when(mentor1.getRole()).thenReturn(UserRole.MENTOR);

        LocalDateTime start = LocalDateTime.now();
        /* Testing with exact 6 months to ensure the mentor is included */
        LocalDateTime end = start.plusMonths(8);

        when(mentor1.getStartAvailability()).thenReturn(start);
        when(mentor1.getEndAvailability()).thenReturn(end);

        /* Selected from list */
        when(userRepo.selectAll()).thenReturn(List.of(mentor1));

        /* Function called here */
        List<String> mentors = adminController.listAllMentors();

        assertEquals(1, mentors.size(), "Should return the mentor with 8 month availability");
        assertTrue(mentors.contains(mentor1.getUsername()), "Mentor should be included in list");

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

    /* 7.Edge case - listing mentors with exactly 6 months */
    @Test
    public void testListAllMentorsExactSixMonths(){
        Mentor mentor = mock(Mentor.class);
        when(mentor.getRole()).thenReturn(UserRole.MENTOR);

        LocalDateTime start = LocalDateTime.now();
        /* Testing with exact 6 months to ensure the mentor is included */
        LocalDateTime end = start.plusMonths(6);

        when(mentor.getStartAvailability()).thenReturn(start);
        when(mentor.getEndAvailability()).thenReturn(end);

        /* Selected from list */
        when(userRepo.selectAll()).thenReturn(List.of(mentor));

        /* Function called here */
        List<String> mentors = adminController.listAllMentors();

        assertEquals(1, mentors.size(), "Should return the mentor with 6 month availability");
        assertTrue(mentors.contains(mentor.getUsername()), "Mentor should be included in list");

    }

    /* 8. Testing mentors with missing availability */
    @Test
    public void testListAllMentorsMissingAvailability(){
        Mentor mentor = mock(Mentor.class);
        when(mentor.getRole()).thenReturn(UserRole.MENTOR);

        /* These columns left empty for this mentor */
        when(mentor.getStartAvailability()).thenReturn(null);
        when(mentor.getEndAvailability()).thenReturn(null);

        /* Selected from list */
        when(userRepo.selectAll()).thenReturn(List.of(mentor));

        /* Function called here */
        List<String> mentors = adminController.listAllMentors();

        assertTrue(mentors.isEmpty(), "Mentors without availability should not be included in list");
    }

    /* 9. Testing even just missing start availability */
    @Test
    public void testListAllMentorsMissingStart() {
        Mentor mentor = mock(Mentor.class);
        when(mentor.getRole()).thenReturn(UserRole.MENTOR);
        when(mentor.getStartAvailability()).thenReturn(null);
        when(mentor.getEndAvailability()).thenReturn(LocalDateTime.now().plusMonths(7));

        List<User> mockUsers = List.of(mentor);
        when(userRepo.selectAll()).thenReturn(mockUsers);

        List<String> mentors = adminController.listAllMentors();
        assertTrue(mentors.isEmpty(), "Mentor with missing start availability should not be included");
    }



    /* 10. Testing the addPair() function */
    @Test
    public void testAddPair() {
        /* Goal is to make these two a pair */
        adminController.addPair("mentee1", "mentor1");

        assertEquals(1, pairsResults.size(), "Should contain one pair");
        assertEquals("mentee1", pairsResults.getFirst().getKey(), "First in pair (mentee) should be mentee1");
        assertEquals("mentor1", pairsResults.getFirst().getValue(), "First in pair (mentor) should be mentor1");
    }


    /* 11. Testing same function with multiple pairs */
    @Test
    public void testAddPairMultiple() {
        /* Adding 3 pairs */
        adminController.addPair("mentee1", "mentor1");
        adminController.addPair("mentee2", "mentor2");
        adminController.addPair("mentee3", "mentor3");

        assertEquals(3, pairsResults.size(), "Should contain three pair");

        assertEquals("mentee1", pairsResults.get(0).getKey(), "First in pair (mentee) should be mentee1");
        assertEquals("mentor1", pairsResults.get(0).getValue(), "First in pair (mentor) should be mentor1");

        assertEquals("mentee2", pairsResults.get(1).getKey(), "Second in pair (mentee) should be mentee2");
        assertEquals("mentor2", pairsResults.get(1).getValue(), "Second in pair (mentor) should be mentor2");

        assertEquals("mentee3", pairsResults.get(2).getKey(), "Third in pair (mentee) should be mentee3");
        assertEquals("mentor3", pairsResults.get(2).getValue(), "Third in pair (mentor) should be mentor3");
    }

    /* 12. With null pairs */
    @Test
    public void testAddPairNull() {
        adminController.addPair(null, null);
        assertEquals(1, pairsResults.size(), "Null pair should not be included");
    }

    /* 13. Testing the writeCSVfile function */
    @Test
    public void testWritePairsToCSV() throws IOException {
        adminController.addPair("mentee1", "mentor1");
        adminController.addPair("mentee2", "mentor2");
        /* Calling function */
        adminController.writePairsToCSV();

        assertEquals(2, pairsResults.size(), "Should have written 2 pairs");
    }

    /* 14. Testing the writeCSVFile function when no pairs */
    @Test
    public void testWritePairsToCSVNoPair() throws IOException {
        /* Calling function */
        adminController.writePairsToCSV();

        assertEquals(0, pairsResults.size(), "CSV should not contain any pairs if the pair list is empty");
    }

    /* 15. No mentees in repository */
    @Test
    public void testListAllMenteesEmpty() {
        when(userRepo.selectAll()).thenReturn(new ArrayList<>());
        List<String> mentees = adminController.listAllMentees();
        assertTrue(mentees.isEmpty(), "Mentee list should be empty");
    }

}

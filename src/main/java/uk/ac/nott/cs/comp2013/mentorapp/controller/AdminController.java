package uk.ac.nott.cs.comp2013.mentorapp.controller;

import javafx.util.Pair;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.HasAvailability;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class AdminController {

    private final Repository<User, String> userRepo;
    private final List<Pair<String, String>> selectedPairs = new ArrayList<>();

    /* Using same params as login */
    public AdminController(Repository<User, String> model) {
        this.userRepo = model;
    }

    /* To return the names needed for displaying in drop down menus*/
    /* For MENTEES */
    public List<String> listAllMentees(){
        List<String> menteeList = new ArrayList<>();
        /* Selecting all users where the role is set to mentee */
        for (User user : userRepo.selectAll()){
            if (user.getRole() == UserRole.MENTEE){
                /* Taking only their username for now to display in combo box */
                menteeList.add(user.getUsername());
            }
        }
        return menteeList;
    }

    /* For MENTORS  */
    public List<String> listAllMentors(){
        List<String> mentorList = new ArrayList<>();
        /* Selecting all users where the role is set to mentor */
        /* However, the data set is outdated and therefore main condition mentors
        will be listed for is availability of minimum 6 months */

        for (User user : userRepo.selectAll()){
            if (user.getRole() == UserRole.MENTOR){
                if (user instanceof HasAvailability availability){
                    LocalDateTime start = availability.getStartAvailability();
                    LocalDateTime end = availability.getEndAvailability();

                    /* Checking mentor has at least 6 months */
                    /* Also ensures that each mentor has dates filled in for both start and end */
                    if (start != null && end != null && ChronoUnit.MONTHS.between(start, end) >= 6){
                        mentorList.add(user.getUsername());
                    }
                }
            }
        }
        return mentorList;
    }

    /* Now move onto specifics in use case */
    /* Adding the pair created */


}
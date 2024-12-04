package uk.ac.nott.cs.comp2013.mentorapp.controller;

import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private final Repository<User, String> userRepo;

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
        for (User user : userRepo.selectAll()){
            if (user.getRole() == UserRole.MENTOR){
                /* Taking only their username for now to display in combo box */
                mentorList.add(user.getUsername());
            }
        }
        return mentorList;
    }

}
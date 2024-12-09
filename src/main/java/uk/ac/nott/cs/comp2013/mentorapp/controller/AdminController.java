package uk.ac.nott.cs.comp2013.mentorapp.controller;

import javafx.util.Pair;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.HasAvailability;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Controller for admin of the mentor app.
 * This class handles the logic for managing the mentors and mentees.
 * System lists them, admin adds pairs, and confirmed pairs are written to a CSV file.
 */
public class AdminController {

    private final Repository<User, String> userRepo;
    private final List<Pair<String, String>> pairsResults;

    /**
     * Constructs an AdminController with the repository and pair results.
     * @param model - The repository of users of the application.
     * @param pairsResults - The list of confirmed pairs.
     */
    /* Using same params as login */
    public AdminController(Repository<User, String> model, List<Pair<String, String>> pairsResults) {
        this.userRepo = model;
        this.pairsResults = pairsResults;
    }

    /**
     * Returns list of mentee usernames to be displayed on UI.
     * @return - list of mentee usernames.
     */
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

    /**
     * Returns list of mentor usernames to be displayed on UI.
     * @return - list of mentor usernames.
     */
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

    /**
     * Adds a new pair to the list of pairs.
     * @param mentee - username of mentee .
     * @param mentor - username of mentor .
     */
    /* Adding the pair created */
    public void addPair(String mentee, String mentor){
        Pair<String, String> pair = new Pair<>(mentee, mentor);
        pairsResults.add(pair);

    }

    /**
     * Writes the confirmed pairs to a CSV file.
     * The file is stored in the path "src/main/pairing".
     * @throws IOException - if I/O error occurs when writing to the file .
     */
    public void writePairsToCSV() throws IOException {
        /* Function to permanently record the pairs made into a csv file */
        /* Creating the new file in specified location */
        String directoryPath = "src/main/pairing";
        /* Choosing name of file */
        String filePath = directoryPath + "/confirmed-pairs.csv";

        /* Creating the new file */
        File file = new File(filePath);
        /* Testing first with simple message */

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Mentee, Mentor");
            writer.newLine();
            /* Looping through the pairs admin selected */
            for (Pair<String, String> pair : pairsResults) {
                writer.write(pair.getKey() + "," + pair.getValue());
                /* New line after each pair for readability */
                writer.newLine();
            }
            int totalPairs = pairsResults.size();
            writer.write("Total number of pairs: " + totalPairs);
            writer.newLine();

        }
    }

}
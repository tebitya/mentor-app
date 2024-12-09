package uk.ac.nott.cs.comp2013.mentorapp.controller;

import java.util.Optional;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

public class LoginController {

  private final Repository<User, String> repo;
  private static UserRole role;
  private static UserRole selectedRole;

  /**
   * Constructs new LoginController .
   * @param model - connection to repository to retrieve data .
   */
  public LoginController(Repository<User, String> model) {
    this.repo = model;
  }

  /* New getter to get the role of the user */

  /**
   * Gets role of user .
   * @return - returns userRole of the current user .
   */
  public static UserRole getRole() {
    return role;
  }

  /**
   * New function to store the selected role
   * @param role - setting the user's role .
   */
  public static void setSelectedRole(UserRole role) {
    selectedRole = role;
  }

  /**
   * Function to retrieve selected role
   * @return - getting the role user selected .
   */
  public static UserRole getSelectedRole() {
    return selectedRole;
  }

  /**
   * Handles the logic for the login.
   * Takes in both the username and password, and validates against the CSV
   * to either allow user to login or send an error message .
   * @param username - username entered by user
   * @param password - password entered by user
   * @return - either true or false based on progress / success of login .
   */
  public boolean onLoginClick(String username, String password) {
    Optional<User> user = repo.selectById(username);
    if (user.isEmpty()) {
      return false;
    }


    User u = user.get();
    if(u.getUsername().equals(username) && u.getPassword().equals(password)){
      /* Role recorded once current user logs in */
      role = u.getRole();
      return role == selectedRole;
    }
    return false;
  }
}

package uk.ac.nott.cs.comp2013.mentorapp.controller;

import java.util.Optional;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

public class LoginController {

  private final Repository<User, String> repo;
  private static UserRole role;

  public LoginController(Repository<User, String> model) {
    this.repo = model;
  }

  /* New getter to get the role of the user */
  /* Used for the actorsveiw file */
  public static UserRole getRole() {
    return role;
  }

  public boolean onLoginClick(String username, String password) {
    Optional<User> user = repo.selectById(username);
    if (user.isEmpty()) {
      return false;
    }


    User u = user.get();
    if(u.getUsername().equals(username) && u.getPassword().equals(password)){
      /* Role recorded once current user logs in */
      role = u.getRole();
      return true;
    }
    return false;
  }
}

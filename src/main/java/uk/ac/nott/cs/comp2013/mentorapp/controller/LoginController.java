package uk.ac.nott.cs.comp2013.mentorapp.controller;

import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

public class LoginController {

  private Repository<User, String> repo;

  public LoginController(Repository<User, String> model) {
    this.repo = model;
  }

}

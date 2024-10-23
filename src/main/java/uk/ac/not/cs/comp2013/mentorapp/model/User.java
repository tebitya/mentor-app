package uk.ac.not.cs.comp2013.mentorapp.model;

/**
 * Base interface for any user of the system.
 */
public interface User extends HasId<Integer> {

  String getUsername();

  void setUsername(String username);

  String getPassword();

  void setPassword(String password);

}

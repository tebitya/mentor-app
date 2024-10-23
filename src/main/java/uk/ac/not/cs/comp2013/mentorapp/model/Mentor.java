package uk.ac.not.cs.comp2013.mentorapp.model;

import java.time.LocalDateTime;

public class Mentor implements User, HasAvailability {

  private int id;
  private String username, password;
  private LocalDateTime startAvailability, endAvailability;

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public LocalDateTime getStartAvailability() {
    return startAvailability;
  }

  @Override
  public void setStartAvailability(LocalDateTime date) {
    this.startAvailability = date;
  }

  @Override
  public LocalDateTime setEndAvailability() {
    return endAvailability;
  }

  @Override
  public void setEndAvailability(LocalDateTime date) {
    this.endAvailability = date;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Mentor m) {
      return username.equals(m.username);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }
}

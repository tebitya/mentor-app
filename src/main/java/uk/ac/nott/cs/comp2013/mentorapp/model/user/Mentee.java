package uk.ac.nott.cs.comp2013.mentorapp.model.user;

public class Mentee implements User, HasCv {

  private UserRole role = UserRole.MENTEE;
  private String username, password, cvText;
  /* year group is int */
  private int yearGroup;

  public Mentee(String username, String password, int yearGroup) {
    this(username, password, "", yearGroup);
  }

  public Mentee(String username, String password, String cvText, int yearGroup) {
    this.username = username;
    this.password = password;
    this.cvText = cvText;
    /* Adding year group column to ensure that there is no in-year mentoring */
    /* All mentors must be older by at least a year than their mentee */
    this.yearGroup = yearGroup;
  }

  /* getter */
  @Override
  public int getYearGroup() {
    return yearGroup;
  }

  /* setter */
  @Override
  public void setYearGroup(int yearGroup) {
    this.yearGroup = yearGroup;
  }

  @Override
  public String getCvText() {
    return cvText;
  }

  @Override
  public void setCvText(String text) {
    cvText = text;
  }

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
  public UserRole getRole() {
    return role;
  }

  @Override
  public void setRole(UserRole role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Mentee m) {
      return username.equals(m.username);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }
}

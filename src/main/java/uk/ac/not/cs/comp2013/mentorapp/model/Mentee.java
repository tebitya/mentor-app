package uk.ac.not.cs.comp2013.mentorapp.model;

public class Mentee implements User, HasCv {

  private int id;
  private String username, password, cvText;

  @Override
  public String getCvText() {
    return cvText;
  }

  @Override
  public void setCvText(String text) {
    cvText = text;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
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
}

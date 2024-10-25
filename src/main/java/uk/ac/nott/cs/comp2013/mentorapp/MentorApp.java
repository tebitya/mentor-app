package uk.ac.nott.cs.comp2013.mentorapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.HashMapRepository;
import uk.ac.nott.cs.comp2013.mentorapp.view.LoginView;

public class MentorApp extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    LoginController controller = new LoginController(new HashMapRepository<>());
    Scene s = new Scene(new LoginView(controller));
    stage.setScene(s);
    stage.show();
  }
}

package uk.ac.nott.cs.comp2013.mentorapp;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.util.Pair;
import uk.ac.nott.cs.comp2013.mentorapp.controller.*;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.RepositoryFactory;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.view.*;

/**
 * Main class for the entire mentor app. Remember to run the application using Gradle's {@code run}
 * task rather than running the app through IntelliJ.
 */
public class MentorApp extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  private Repository<User, String> loadMockData() throws IOException {
    RepositoryFactory factory = new RepositoryFactory();
    return factory.userHashMapRepository("/MOCK_DATA.csv");
  }

  private LoginView createLoginView(Repository<User, String> repo) {
    LoginController controller = new LoginController(repo);
    return new LoginView(controller);
  }

  private ActorsView createActorsView() {
    ActorsController controller = new ActorsController();
    return new ActorsView(controller);
  }

  private MenteeView createMenteeView() {
    MenteeController controller = new MenteeController();
    return new MenteeView(controller);
  }

  private MentorView createMentorView() {
    MentorController controller = new MentorController();
    return new MentorView(controller);
  }

  private AdminView createAdminView(Repository<User, String> repo){
    List<Pair<String, String>> pairsResults = FXCollections.observableArrayList();
    AdminController controller = new AdminController(repo, pairsResults);
    return new AdminView(controller);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Repository<User, String> mockData = loadMockData();

    ViewManager vm = new ViewManager(stage);
    vm.addView(ViewManager.LOGIN, createLoginView(mockData));

    /* Testing with actors view window */
    vm.addView(ViewManager.ACTORS_VIEW, createActorsView());
    vm.addView(ViewManager.MENTEE_VIEW, createMenteeView());
    vm.addView(ViewManager.MENTOR_VIEW, createMentorView());
    vm.addView(ViewManager.ADMIN_VIEW, createAdminView(mockData));

    /* Set title to windows */
    stage.setTitle("UoN Mentor App");
    /* Changed to have the role selection screen first */
    vm.setStageView(ViewManager.MENTOR_VIEW);
    stage.show();
  }

}

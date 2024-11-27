package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;

/**
 * The {@code LoginView} provides a login screen for users to access the app. It also serves as a
 * simple example of how to implement {@code ManagedView}. Note that this class extends {@code VBox}
 * so that we can use JavaFX's built-in layout logic.
 */
public class LoginView extends VBox implements ManagedView {

  private final LoginController controller;
  protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
  private TextField txtUsername, txtPassword;

  public LoginView(LoginController controller) {
    this.controller = controller;
    this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);
    /* Making the window larger */
    setPrefSize(900,700);
    buildView();
  }

  private void buildView() {
    /* Moving login button to the left of the input fields*/
    setAlignment(Pos.BASELINE_LEFT);
    setPadding(new Insets(50));
    setSpacing(20);

    /* New title for the login page */
    Label loginTitle = new Label("Welcome to the Mentor App");
    /* To position title in centre */
    loginTitle.setAlignment(Pos.CENTER);
    loginTitle.setStyle("-fx-text-fill: #10263B; -fx-font-size: 20px; -fx-font-weight: bold;");


    /* Organising structure of words - complete THIS ! */

    /* Gathering input from user */
    txtUsername = new TextField();
    txtPassword = new TextField();

    /* Changing text in button to make it more similar to Moodle page */
    Button btnLogin = new Button("Log in");
    /* Changing colour of button and text inside to make it more similar to Moodle login page*/
    btnLogin.setStyle("-fx-background-color: #10263B;-fx-text-fill: white; -fx-font-size: 14px;");

    /* Creation of label to be displayed if login is invalid */
    Label invalidLogin = new Label();
    invalidLogin.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 14px;");
    /* Adding these two labels to the screen*/
    getChildren().addAll(invalidLogin, loginTitle);

    /* Once log in button is clicked */
    btnLogin.setOnAction(e -> {
      boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
      if (success) {
        var eh = onViewChange.get();
        if (eh != null) {
          /* Part to switch to new screen */
          eh.handle(new ViewChangeEvent(ViewManager.DUMMY));
        }
        /* Added 'else' condition for when login is not validated*/
      } else {
        invalidLogin.setText("  Invalid login, please try again  ");
      }
    });
    getChildren().addAll(txtUsername, txtPassword, btnLogin);
  }

  @Override
  public EventHandler<? super ViewChangeEvent> getOnViewChange() {
    return onViewChange.get();
  }

  @Override
  public void setOnViewChange(EventHandler<? super ViewChangeEvent> eventHandler) {
    onViewChange.set(eventHandler);
  }
}

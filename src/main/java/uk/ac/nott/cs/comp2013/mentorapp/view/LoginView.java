package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.Objects;

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
    setMinSize(500,300);
    setStyle("-fx-background-color: #FDFBF8;");
    setAlignment(Pos.CENTER);
    setPadding(new Insets(50));
    /* To add more spacing between the separate interface elements */
    setSpacing(30);
    buildView();
  }

  private void buildView() {
    /* VBox to hold all components */
    /* Allows border to be created around entire login form */

    VBox loginBox = new VBox(10);
    loginBox.setAlignment(Pos.CENTER);

    /* Adding border around the login form for similarity */
    loginBox.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 0.5px; -fx-border-radius: 5px; "
            + "-fx-background-color: #FDFBF9; -fx-padding: 20px;");


    /* UoN logo */
    Image uonLogo = null;
    /* try catch being used to ensure logo is being loaded correctly */
    try {
      /* Fetched from resource file where it has been saved */
      uonLogo = new Image(Objects.requireNonNull(getClass().getResource("/uon-logo.png")).toExternalForm());
      /* For when an exception may occur */
    } catch (NullPointerException e) {
      /* Printed during an error */
      System.out.println("Image not found");
    }
    /* If the logo has been successfully found */
    /* Using this try...catch method allows the page to be loaded without conflicts
    even if logo has not been located */

    if (uonLogo != null) {
      ImageView logoView = new ImageView(uonLogo);
      /* Setting the measurements of the image */
      logoView.setFitWidth(300);
      logoView.setFitHeight(300);
      /* Ensuring the image is not distorted */
      logoView.setPreserveRatio(true);
      loginBox.getChildren().add(logoView);
    }

    /* New title for the login page */
    Label loginTitle = new Label("Welcome to the Mentor App");
    /* To position title in centre */
    loginTitle.setAlignment(Pos.CENTER);
    loginTitle.setStyle("-fx-text-fill: #10263B; -fx-font-size: 20px; -fx-font-weight: bold;");
    loginTitle.setPadding(new Insets(10));

    /* Added limits for the widths of the input boxes */
    /* Also placeholder text to prompt users to enter password and user */
    txtUsername = new TextField();
    txtUsername.setPromptText("Username");
    txtUsername.setMaxWidth(400);
    txtUsername.setPadding(new Insets(10));
    txtUsername.setStyle("-fx-font-size: 16px;");

    /* For password field */
    /* PasswordField added for security */
    txtPassword = new PasswordField();
    txtPassword.setPromptText("Password");
    txtPassword.setMaxWidth(400);
    /* Used throughout to mimic padding of Moodle login page */
    txtPassword.setPadding(new Insets(10));
    txtPassword.setStyle("-fx-font-size: 16px;");

    /* Login button */
    /* Changing text in button to make it more similar to Moodle page */
    Button btnLogin = new Button("  Log in  ");
    /* Changing colour of button and text inside to make it more similar to Moodle login page*/
    btnLogin.setStyle("-fx-background-color: #10263B;-fx-text-fill: white; -fx-font-size: 16px;");
    btnLogin.setPadding(new Insets(10));


    /* Creation of label to be displayed if login is invalid */
    Label invalidLogin = new Label();
    invalidLogin.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 16px;");

    /* Link to create account */
    Label createAccount = new Label("Register account");
    createAccount.setStyle("-fx-text-fill: #10263B; -fx-font-size: 16px; -fx-cursor: hand; -fx-underline: true;");

    /* Link to reset password */
    Label resetPassword = new Label("Lost password?");
    resetPassword.setStyle("-fx-text-fill: #10263B; -fx-font-size: 16px; -fx-cursor: hand; -fx-underline: true;");

    /* Using a separator as is seen on Moodle page */
    Separator separator = new Separator();
    separator.setStyle("-fx-background-color: #CCCCCC;");
    separator.setMaxWidth(600);
    separator.setPrefHeight(0.5);

    /* Button to go back to role selection page */
    Button rolePage = new Button("Back to Role Selection");
    rolePage.setStyle("-fx-background-color: #CCCCCC; -fx-text-fill: black; -fx-font-size: 14px;");
    rolePage.setPadding(new Insets(10));

    /* Logic for going back to role page */
    rolePage.setOnAction(e -> {
      var eh = onViewChange.get();
      if (eh != null) {
        eh.handle(new ViewChangeEvent(ViewManager.ACTORS_VIEW));
      }
    });

    /* Adding these labels to the screen*/
    loginBox.getChildren().addAll(loginTitle, invalidLogin, txtUsername, txtPassword, btnLogin, resetPassword, createAccount, separator, rolePage);
    getChildren().addAll(loginBox);
    Platform.runLater(this::requestFocus);

    /* Once log in button is clicked */
    btnLogin.setOnAction(e -> {
      boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
      UserRole role = LoginController.getSelectedRole();
      var eh = onViewChange.get();
      if (success) {
        if (eh != null) {
          if (role == UserRole.ADMIN) {
            eh.handle(new ViewChangeEvent(ViewManager.ADMIN_VIEW));
          } else if (role == UserRole.MENTOR) {
            eh.handle(new ViewChangeEvent(ViewManager.MENTOR_VIEW));
          } else if (role == UserRole.MENTEE) {
            eh.handle(new ViewChangeEvent(ViewManager.MENTEE_VIEW));
          }
        }
        clearInputs();
          /* Added 'else' condition for when login is not validated*/
      } else {
          invalidLogin.setText("  Invalid login or incorrect role. Please try again.  ");
          invalidLogin.setMaxWidth(400);
          invalidLogin.setPadding(new Insets(10));
      }

    });

  }

  /* To clear inputs after entered */
  public void clearInputs() {
    txtUsername.clear();
    txtPassword.clear();
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

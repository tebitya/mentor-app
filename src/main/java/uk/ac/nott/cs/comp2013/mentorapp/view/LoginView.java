package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;

public class LoginView extends VBox {

  private final LoginController controller;
  private TextField txtUsername, txtPassword;
  private Button btnLogin;

  public LoginView(LoginController controller) {
    this.controller = controller;
    buildView();
  }

  private void buildView() {
    txtUsername = new TextField();
    txtPassword = new TextField();
    btnLogin = new Button("Login");
    btnLogin.setOnAction(e -> {
      boolean success = controller.onLoginClick(txtUsername.getText(), txtPassword.getText());
    });

    getChildren().addAll(txtUsername, txtPassword, btnLogin);
  }

}

package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.ac.nott.cs.comp2013.mentorapp.controller.ActorsController;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

import java.util.Objects;

public class ActorsView extends VBox implements ManagedView {

    // Property to handle view changes
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    public ActorsView(ActorsController controller) {
        // Initialize the view change handler
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);

        /* Making the window larger */
        setPrefSize(900,700);
        setStyle("-fx-background-color: #FDFBF8;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(50));
        /* To add more spacing between the separate interface elements */
        setSpacing(30);
        setStyle("-fx-background-color: #FDFBF9;");


        /* Creating border around all centered text like in login page */
        /* VBox to hold all components */
        /* Allows border to be created around entire login form */
        VBox roleBox = new VBox(10);
        roleBox.setAlignment(Pos.CENTER);

        /* Adding border around the buttons for similarity */
        roleBox.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 0.5px; -fx-border-radius: 5px; "
                + "-fx-background-color: #FDFBF9; -fx-padding: 20px;");

        /* Adding the UoN logo */
        Image uonLogo = null;
        try {
            uonLogo = new Image(Objects.requireNonNull(getClass().getResource("/uon-logo.png")).toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("Image not found");
        }

        /* Add image to the role selection page if found */
        if (uonLogo != null) {
            ImageView logoView = new ImageView(uonLogo);
            logoView.setFitWidth(300);
            logoView.setFitHeight(300);
            logoView.setPreserveRatio(true);
            roleBox.getChildren().add(logoView);
        }


        /* END of formulating role box */

        Label title = new Label("Select Your Role");
        title.setStyle("-fx-text-fill: #10263B; -fx-font-size: 24px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);


        /* Buttons created to display roles */
        Button mentorBtn = new Button("Mentor");
        Button menteeBtn = new Button("Mentee");
        Button adminBtn = new Button("Admin");

        String buttonStyle = "-fx-font-size: 20px; -fx-background-color: #10263B; -fx-text-fill: white;";
        mentorBtn.setStyle(buttonStyle);
        menteeBtn.setStyle(buttonStyle);
        adminBtn.setStyle(buttonStyle);

        /* Ensuring buttons cover width of page */
        mentorBtn.setMaxWidth(Double.MAX_VALUE);
        menteeBtn.setMaxWidth(Double.MAX_VALUE);
        adminBtn.setMaxWidth(Double.MAX_VALUE);

        /* Leaving more space/ padding between separate buttons for readability */
        VBox.setMargin(mentorBtn, new Insets(10));
        VBox.setMargin(menteeBtn, new Insets(10));
        VBox.setMargin(adminBtn, new Insets(10));

        /* Determining the button's actions once pressed */
        mentorBtn.setOnAction(e -> roleSelection(UserRole.MENTOR));
        menteeBtn.setOnAction(e -> roleSelection(UserRole.MENTEE));
        adminBtn.setOnAction(e -> roleSelection(UserRole.ADMIN));

        roleBox.getChildren().addAll(title,mentorBtn, menteeBtn, adminBtn);
        getChildren().addAll(roleBox);
    }

    /* Function to save the role the user selected */
    private void roleSelection(UserRole selectedRole) {

        var eh = onViewChange.get();

        if (eh != null) {
            LoginController.setSelectedRole(selectedRole);
            /* Navigates user to login page */
            eh.handle(new ViewChangeEvent(ViewManager.LOGIN));
        }
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

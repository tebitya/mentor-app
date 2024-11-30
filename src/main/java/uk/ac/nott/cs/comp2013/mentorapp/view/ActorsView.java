package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.ac.nott.cs.comp2013.mentorapp.controller.ActorsController;
import uk.ac.nott.cs.comp2013.mentorapp.controller.LoginController;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.UserRole;

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

        Label title = new Label("Select Your Role");
        title.setStyle("-fx-text-fill: #10263B; -fx-font-size: 24px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);
        getChildren().add(title);

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

        /* Determining the button's actions once pressed */
        mentorBtn.setOnAction(e -> roleSelection(UserRole.MENTOR));
        menteeBtn.setOnAction(e -> roleSelection(UserRole.MENTEE));
        adminBtn.setOnAction(e -> roleSelection(UserRole.ADMIN));

        getChildren().addAll(mentorBtn, menteeBtn, adminBtn);
    }

    /* Function to check which role a user has */
    private void roleSelection(UserRole selectedRole) {
        UserRole actualRole = LoginController.getRole();
        var eh = onViewChange.get();

        if (eh != null) {
            /* Checking button clicked against actual role */
            /* And based on this going to correct view */
            if (selectedRole == actualRole) {
                if (selectedRole == UserRole.ADMIN) {
                    eh.handle(new ViewChangeEvent(ViewManager.ADMIN_VIEW));
                } else if (selectedRole == UserRole.MENTOR) {
                    eh.handle(new ViewChangeEvent(ViewManager.MENTOR_VIEW));
                } else if (selectedRole == UserRole.MENTEE) {
                    eh.handle(new ViewChangeEvent(ViewManager.MENTEE_VIEW));
                }
            } else {
                /* New label for when incorrect role is selected */
                Label accessDenied = new Label("Access denied. Incorrect role selected.");
                /* Similar styling to Moodle error messages */
                accessDenied.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 14px;");
                getChildren().add(accessDenied);
            }
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

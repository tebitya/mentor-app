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

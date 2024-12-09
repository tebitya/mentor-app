package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MentorController;

import java.util.Date;
import java.util.Objects;

/**
 * MentorView is a view class that displays the mentor's interface.
 * Includes same dashboard as mentee and admin.
 * UI includes data pickers, buttons, and labels.
 */
public class MentorView extends VBox implements ManagedView {

    /** Property to handle view changes */
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
    private Label progressLbl;

    /**
     * Constructs MentorView object.
     * @param controller - Controller interacts with view .
     */

    public MentorView(MentorController controller) {

        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);

        /* Making the window larger */
        setPrefSize(900,700);

        buildView();
    }

    /**
     * Builds UI for mentor view.
     * Includes dashboard, and setting availability interface.
     */
    private void buildView() {
        /* Creating hbox for the navigation bar up top */
        /* Similar to moodle */
        HBox navBar = new HBox(10);
        navBar.setAlignment(Pos.CENTER);
        /* Customising the navigation bar */
        navBar.setStyle("-fx-background-color: #FDFBF9;");
        navBar.setSpacing(20);
        navBar.setPadding(new Insets(15));

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
            logoView.setFitWidth(200);
            logoView.setFitHeight(200);
            /* Ensuring the image is not distorted */
            logoView.setPreserveRatio(true);
            /* Adding image to the top navigation bar */
            navBar.getChildren().add(logoView);
        }

        /* Adding links to other pages in the centre */
        HBox linksBox = new HBox(20);
        linksBox.setAlignment(Pos.CENTER);

        /* The three main pages user can navigate to */
        Label accountLink = createLinkLabel("My Account");
        Label availableLink = createLinkLabel("Availability");
        Label logoutLink = createLinkLabel("Logout");

        linksBox.getChildren().addAll(accountLink, availableLink, logoutLink);

        /* Putting same as Moodle icons on the right */
        HBox iconsBox = new HBox(10);
        iconsBox.setAlignment(Pos.BASELINE_RIGHT);

        /* And add role label too to differentiate between different roles */
        Label roleLbl = new Label("MENTOR");
        roleLbl.setStyle("-fx-text-fill: #273B4D; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        /* Put in these two as string to avoid overcomplication */
        /* As used on Moodle */
        Label bellIcon = new Label("🔔");
        Label messageIcon = new Label("💬");
        bellIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");
        messageIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");

        iconsBox.getChildren().addAll(bellIcon, messageIcon, roleLbl);

        /* Simple welcome message  */
        Label welcome = new Label("Welcome ");
        welcome.setStyle("-fx-font-size: 24px; -fx-text-fill: #10263B; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        welcome.setPadding(new Insets(20));

        ////////////////////////////////////////////////////
        /* UI for date selection */
        Label description = new Label ("This page allows you to select your availability to ensure that the correct mentor is assigned to you.\nPlease include both the start and end of your availability.");
        description.setStyle("-fx-font-size: 20px; -fx-text-fill: #273B4D; -fx-font-family: 'Arial';");
        description.setPadding(new Insets(20));
        description.setWrapText(true);

        /* Date Pickers for user to get little calendar to select dates */
        DatePicker startAvail = new DatePicker();
        startAvail.setPromptText("Select Start Date");
        startAvail.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");

        /* Now same layout for end date */
        DatePicker endAvail = new DatePicker();
        endAvail.setPromptText("Select End Date");
        endAvail.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");

        /* Confirmation Button - same as in mentee */
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #10263B; -fx-text-fill: white;-fx-cursor: hand;-fx-font-family: 'Arial';");
        confirmBtn.setPadding(new Insets(10));
        confirmBtn.setOnAction(e -> handleConfirmBtnClick(startAvail, endAvail));

        /* HBox for setting availability component */
        HBox setAvailability = new HBox(10, startAvail, endAvail, confirmBtn);
        setAvailability.setAlignment(Pos.CENTER);
        setAvailability.setPadding(new Insets(10));

        /* Adding all individual hbox to main navigation one */
        navBar.getChildren().addAll(linksBox, iconsBox);

        /* Adding main hbox to page */
        getChildren().addAll(navBar, welcome, description, setAvailability);
    }

    /**
     * Handles when confirm button is clicked.
     * Tests & returns error if criteria is not met.
     * @param startAvail - date chosen as start date .
     * @param endAvail - date chosen as end date.
     */
    private void handleConfirmBtnClick(DatePicker startAvail, DatePicker endAvail) {
        /* When confirm button is clicked */
        /* EXACT same as menteeview */
        /* Firstly removing the error message if it is already present */

        if (progressLbl != null) {
            getChildren().remove(progressLbl);
            progressLbl = null;
        }

        if (startAvail.getValue() == null || endAvail.getValue() == null){
            progressLbl = new Label("Select start and end availability.");
            progressLbl.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 16px; -fx-font-family: 'Arial';");

            /* controller bit comes in here when it is written */
        }else{
            progressLbl = new Label ("Availability Submitted. Notification sent to administrator.");
            /* Similar styling to error messages */
            progressLbl.setStyle("-fx-background-color: #D4EDDA; -fx-text-fill: #155724; -fx-font-size: 16px; -fx-font-family: 'Arial';");
        }
        progressLbl.setAlignment(Pos.CENTER);
        progressLbl.setMaxWidth(Double.MAX_VALUE);
        progressLbl.setPadding(new Insets(10));

        getChildren().add(progressLbl);

    }

    /**
     * Creates a label that user clicks to logout.
     * @param page - The user navigates back to login page once clicked.
     * @return - The label.
     */
    private Label createLinkLabel(String page){
        /* Generic as of now so can be applied to all three */
        Label label = new Label(page);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #263A4D; -fx-font-size: 16px; -fx-cursor: hand; -fx-font-family: 'Arial';");
        label.setPadding(new Insets(15));

        /* When user hovers on the link */
        /* Background colour changes to show interaction */
        label.setOnMouseEntered(e -> label.setStyle("-fx-font-weight: bold; -fx-background-color: #F3F4F5;-fx-font-size: 16px; -fx-text-fill: #273B4D; -fx-cursor: hand; -fx-font-family: 'Arial';"));
        label.setOnMouseExited(e -> label.setStyle("-fx-font-weight: bold; -fx-background-color: #FDFBF9; -fx-font-size: 16px; -fx-text-fill: #405162; -fx-cursor: hand; -fx-font-family: 'Arial';"));

        /* LOGIC TO BE IMPLEMENTED LATER */
        /* FOR NOW BARE BONES */
        /* Logic only for logout page */
        /* Leads directly back to login page */
        label.setOnMouseClicked(e -> {
            if ("Logout".equals(page)) {
                var eh = onViewChange.get();
                if (eh != null) {
                    eh.handle(new ViewChangeEvent(ViewManager.LOGIN));
                }
            }
        });

        return label;
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

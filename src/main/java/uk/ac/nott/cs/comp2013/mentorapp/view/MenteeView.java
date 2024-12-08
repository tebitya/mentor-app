package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MenteeController;

import java.util.Objects;

public class MenteeView extends VBox implements ManagedView {

    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;
    private Label progressLbl;



    public MenteeView(MenteeController controller) {
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);

        /* Making the window larger */
        setPrefSize(900,700);

        buildView();
    }

    private void buildView(){
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

        /* The three main pages the mentee can navigate to */
        Label accountLink = createLinkLabel("My Account");
        Label supportReqLink = createLinkLabel("Request Support");
        Label logoutLink = createLinkLabel("Logout");

        linksBox.getChildren().addAll(accountLink, supportReqLink, logoutLink);

        /* Putting same as Moodle icons on the right */
        HBox iconsBox = new HBox(10);
        iconsBox.setAlignment(Pos.BASELINE_RIGHT);

        /* And add role label too to differentiate between different roles */
        Label roleLbl = new Label("MENTEE");
        roleLbl.setStyle("-fx-text-fill: #273B4D; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        /* Put in these two as string to avoid overcomplication */
        /* As used on Moodle */
        Label bellIcon = new Label("🔔");
        Label messageIcon = new Label("💬");
        bellIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");
        messageIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");

        iconsBox.getChildren().addAll(bellIcon, messageIcon, roleLbl);

        /* Simple welcome message */
        Label welcome = new Label("Welcome ");
        welcome.setStyle("-fx-font-size: 24px; -fx-text-fill: #10263B; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        welcome.setPadding(new Insets(20));

        /////////////////////////////////////////////////////
        /* Individual mentee support request layout */
        Label description = new Label ("This page allows you to request support for various needs.\nSelect a type of support below and we will pair you with an appropriate mentor.");
        description.setStyle("-fx-font-size: 20px; -fx-text-fill: #273B4D; -fx-font-family: 'Arial';");
        description.setPadding(new Insets(20));
        description.setWrapText(true);

        /* Drop-down box with support types */
        Label selectSupport = new Label ("Select type of support: ");
        selectSupport.setStyle("-fx-font-size: 20px; -fx-text-fill: #273B4D; -fx-font-family: 'Arial';");
        selectSupport.setPadding(new Insets(20));

        /* Most effective way to display support types */
        ComboBox<String> supportTypes = new ComboBox<>();
        supportTypes.getItems().addAll("Academic Support", "Technical Support", "Financial Support", "Mental Health Support");
        supportTypes.setPromptText("Choose support type");
        supportTypes.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");
        supportTypes.setPrefWidth(300);

        /* Button next to combo box for user to confirm their choice */
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #10263B; -fx-text-fill: white;-fx-cursor: hand;-fx-font-family: 'Arial';");
        confirmBtn.setPadding(new Insets(10));
        confirmBtn.setOnAction(e -> handleConfirmBtnClick(supportTypes));

        /* HBox for requesting support component */
        HBox requestSupport = new HBox(10, supportTypes, confirmBtn);
        requestSupport.setAlignment(Pos.CENTER);
        requestSupport.setPadding(new Insets(10));


        /* Adding all individual hbox to main navigation one */
        navBar.getChildren().addAll(linksBox, iconsBox);

        /* Adding main hbox to page */
        getChildren().addAll(navBar, welcome, description, selectSupport, requestSupport);

    }

    private void handleConfirmBtnClick(ComboBox<String> supportTypes) {
        /* For when user clicks the confirmation button */
        /* Firstly removing the error message if it is already present */
        if (progressLbl != null) {
            getChildren().remove(progressLbl);
            progressLbl = null;
        }

        if (supportTypes.getValue() == null){
            progressLbl = new Label("Select a support type.");
            progressLbl.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 16px; -fx-font-family: 'Arial';");

            /* controller bit comes in here when it is written */
        }else{
            progressLbl = new Label ("Support Request Submitted. Notification sent to administrator.");
            /* Similar styling to error messages */
            progressLbl.setStyle("-fx-background-color: #D4EDDA; -fx-text-fill: #155724; -fx-font-size: 16px; -fx-font-family: 'Arial';");
        }
        progressLbl.setAlignment(Pos.CENTER);
        progressLbl.setMaxWidth(Double.MAX_VALUE);
        progressLbl.setPadding(new Insets(10));

        getChildren().add(progressLbl);
    }


    /* Separate function for creating labels in navigation bar */
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

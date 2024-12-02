package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        roleLbl.setStyle("-fx-text-fill: #273B4D; -fx-font-size: 16px; -fx-font-weight: bold;");

        /* Put in these two as string to avoid overcomplication */
        /* As used on Moodle */
        Label bellIcon = new Label("🔔");
        Label messageIcon = new Label("💬");
        bellIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");
        messageIcon.setStyle("-fx-font-size: 18px; -fx-cursor: hand;");

        iconsBox.getChildren().addAll(bellIcon, messageIcon, roleLbl);

        /* Simple welcome message */
        Label welcome = new Label("Welcome ");
        welcome.setStyle("-fx-font-size: 24px; -fx-text-fill: #10263B; -fx-font-weight: bold;");
        welcome.setPadding(new Insets(20));


        /* Adding all individual hbox to main navigation one */
        navBar.getChildren().addAll(linksBox, iconsBox);

        /* Adding main hbox to page */
        getChildren().addAll(navBar, welcome);

    }

    /* Separate function for creating labels in navigation bar */
    private Label createLinkLabel(String page){
        /* Generic as of now so can be applied to all three */
        Label label = new Label(page);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #263A4D; -fx-font-size: 16px; -fx-cursor: hand;");
        label.setPadding(new Insets(15));

        /* When user hovers on the link */
        /* Background colour changes to show interaction */
        label.setOnMouseEntered(e -> label.setStyle("-fx-font-weight: bold; -fx-background-color: #F3F4F5;-fx-font-size: 16px; -fx-text-fill: #273B4D; -fx-cursor: hand;"));
        label.setOnMouseExited(e -> label.setStyle("-fx-font-weight: bold; -fx-background-color: #FDFBF9; -fx-font-size: 16px; -fx-text-fill: #405162; -fx-cursor: hand;"));

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

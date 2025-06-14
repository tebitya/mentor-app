package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;
import uk.ac.nott.cs.comp2013.mentorapp.controller.AdminController;

import java.io.IOException;
import java.util.Objects;

/**
 * AdminView is a view class that displays the administrator's user interface of the mentor app.
 * It includes the same dashboard at the top of the page as is present for both mentee and mentor.
 * Its UI components include combo-boxes, tables, buttons, and labels, to display lists of mentee and mentors, as well as
 * pairing and confirming pairs.
 */


public class AdminView extends VBox implements ManagedView {

    /** Property to handle view changes */
    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    /** Drop-downs to display mentee and mentor names */
    private final ComboBox<String> menteeList = new ComboBox<>();
    private final ComboBox<String> mentorList = new ComboBox<>();
    private final TableView<Pair<String, String>> pairsTable = new TableView<>();
    private final ObservableList<Pair<String, String>> pairsResults = FXCollections.observableArrayList();
    private final AdminController controller;
    private Label errorLbl;

    /**
     * Constructs AdminView object using the AdminController.
     * @param controller - This controller interacts with the view.
     */


    public AdminView(AdminController controller) {
        this.controller = controller;
        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);

        /* Making the window larger */
        setPrefSize(900,700);

        buildView();

        loadDropdownData();
    }


    /**
     * Loads the list of mentees and mentors into their separate combo-boxes.
     */
    private void loadDropdownData() {
        /* Getting the list from the method in admin controller */
        menteeList.setItems(FXCollections.observableArrayList(controller.listAllMentees()));
        mentorList.setItems(FXCollections.observableArrayList(controller.listAllMentors()));
    }

    /**
     * Builds the UI for the admin view.
     * Includes the navigation bar, pairing interface- complete with its buttons-
     * and the pairs table.
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
        Label pairingLink = createLinkLabel("Pairing");
        Label logoutLink = createLinkLabel("Logout");

        linksBox.getChildren().addAll(accountLink, pairingLink, logoutLink);

        /* Putting same as Moodle icons on the right */
        HBox iconsBox = new HBox(10);
        iconsBox.setAlignment(Pos.BASELINE_RIGHT);

        /* And add role label too to differentiate between different roles */
        Label roleLbl = new Label("ADMIN");
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

        ///////////////////////////////////////////////////////////////
        /* This part is for displaying the mentors and mentees */

        /* New hbox just for displaying dropdown boxes for usernames */
        /* As well as confirming 'pair' button */
        VBox pairingSection = new VBox(10);
        HBox pairingRow = new HBox(10);
        /* Put in centre as is admin's main interaction with the page */
        pairingRow.setAlignment(Pos.CENTER);
        pairingRow.setPadding(new Insets(10));

        /* To display mentee data */
        Label menteeLbl = new Label("Mentee");
        menteeLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        menteeList.setPromptText("Select a Mentee");
        menteeList.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial';");

        /* To display mentor data */
        Label mentorLbl = new Label("Mentor");
        mentorLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        mentorList.setPromptText("Select a Mentor");
        mentorList.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial';");

        /* Button for user to click to confirm the current pair */
        Button pairBtn = new Button("Pair");
        pairBtn.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #10263B; -fx-text-fill: white; -fx-cursor: hand;-fx-font-family: 'Arial';");
        pairBtn.setPrefWidth(100);
        pairBtn.setOnAction(e -> handlePairBtnClick());

        /* Adding individual components to main hbox */
        pairingRow.getChildren().addAll(menteeLbl, menteeList, mentorLbl, mentorList, pairBtn);

        /* Adding all individual hbox to main navigation one */
        navBar.getChildren().addAll(linksBox, iconsBox);


        ///////////////////////////////////////////////////////
        /* Adding the table on the page to display the pairs */
        /* To fix issue of small columns */
        pairsTable.setEditable(false);
        pairsTable.setPlaceholder(new Label("No pairs selected yet."));

        /* Adding the columns to the table */
        TableColumn<Pair< String, String>, String> menteeColumn = new TableColumn <> ("Mentee");
        /* To display the string value - the mentee names */
        menteeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        /* Doing the same for the mentor */
        TableColumn<Pair< String, String>, String> mentorColumn = new TableColumn <> ("Mentor");
        /* To display the string value - the mentee names */
        mentorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        /*To make each column the width of 50% of table */
        menteeColumn.prefWidthProperty().bind(pairsTable.widthProperty().multiply(0.5));
        mentorColumn.prefWidthProperty().bind(pairsTable.widthProperty().multiply(0.5));

        /* Adding these two columns to the table */
        pairsTable.getColumns().addAll(menteeColumn, mentorColumn);
        pairsTable.setItems(pairsResults);
        /* Increasing size for improved readability */
        pairsTable.setStyle("-fx-font-size: 16px;");
        /* Setting the height of the table */
        pairsTable.setPrefHeight(400);
        /* Adding scrollbar if admin wants to make many pairs */
        ScrollPane scrollBar = new ScrollPane(pairsTable);
        scrollBar.setFitToWidth(true);
        scrollBar.setPrefHeight(400);


        /* Button needed for admin to confirm their final selection */
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #10263B; -fx-text-fill: white;-fx-cursor: hand;-fx-font-family: 'Arial';");
        confirmBtn.setPrefWidth(100);
        /* CREATE NEW FUNCTION WHAT HAPPENS ONCE BUTTON CLICKED */
        confirmBtn.setOnAction(e -> {
            try {
                handleConfirmBtnClick();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        /* Adding all parts of pairing interface to the VBox */
        pairingSection.getChildren().addAll(pairingRow, scrollBar, confirmBtn);
        pairingSection.setAlignment(Pos.CENTER);
        pairingSection.setPadding(new Insets(20));
        pairingSection.setPrefWidth(600);

        /* Adding main hbox to page */
        getChildren().addAll(navBar, welcome,pairingSection);
    }

    /**
     * Handles when the confirm button is clicked.
     * Tells controller to write the confirmed pairs to the CSV.
     * @throws IOException - if I/O exception whilst writing pairs to the CSV.
     */
    private void handleConfirmBtnClick() throws IOException {
        Label confirmationLbl = new Label("Pairing confirmed. Notifications will be sent to selected mentees and mentors.");
        /* Similar styling to error messages */
        confirmationLbl.setStyle("-fx-background-color: #D4EDDA; -fx-text-fill: #155724; -fx-font-size: 16px; -fx-font-family: 'Arial';");
        confirmationLbl.setPadding(new Insets(10));
        confirmationLbl.setAlignment(Pos.CENTER);
        /* To cover entire width of window */
        confirmationLbl.setMaxWidth(Double.MAX_VALUE);
        /* Adding message to the page */
        getChildren().add(confirmationLbl);

        controller.writePairsToCSV();
    }

    /**
     * Handles when the pair button is clicked.
     * Alerts admin of any error if criteria is not followed.
     * Adds pairs to the table and removes them from combo-boxes.
     */
    private void handlePairBtnClick() {
        /* Function that occurs once admin has pressed the button labelled 'pair' */
        String selectedMentee = menteeList.getValue();
        String selectedMentor = mentorList.getValue();

        /* Firstly removing the error message if it is already present */
        if (errorLbl != null) {
            getChildren().remove(errorLbl);
            errorLbl = null;
        }

        if (selectedMentee == null || selectedMentor == null) {
            /* PUT THIS AS ERROR */
            errorLbl = new Label("Select both a mentee and a mentor.");
            errorLbl.setStyle("-fx-background-color: #EFCED0; -fx-text-fill: #682134; -fx-font-size: 16px; -fx-font-family: 'Arial';");
            errorLbl.setMaxWidth(Double.MAX_VALUE);

            errorLbl.setAlignment(Pos.CENTER);
            getChildren().add(errorLbl);
        }else{
            /* Adding the pair */
            controller.addPair(selectedMentee, selectedMentor);
            /* Adding it to the table now */
            pairsResults.add(new Pair<>(selectedMentee, selectedMentor));
            /* !! Removing selected mentors & mentees from drop-down once they have been selected */
            menteeList.getItems().remove(selectedMentee);
            mentorList.getItems().remove(selectedMentor);

            /* Resetting drop-down boxes for next selection */
            menteeList.setValue(null);
            mentorList.setValue(null);
        }

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
        /* FOR NOW BARE-BONES */
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

package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.ac.nott.cs.comp2013.mentorapp.controller.MentorController;

public class MentorView extends VBox implements ManagedView {

    protected ObjectProperty<EventHandler<? super ViewChangeEvent>> onViewChange;

    public MentorView(MentorController controller) {

        this.onViewChange = new SimpleObjectProperty<>("onViewChange", null);

        /* Making the window larger */
        setPrefSize(900,700);
        setStyle("-fx-background-color: #FDFBF8;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(50));
        /* To add more spacing between the separate interface elements */
        setSpacing(30);
        setStyle("-fx-background-color: #FDFBF9;");
        Label dummyLabel = new Label("I will show you mentor UI");
        getChildren().add(dummyLabel);
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

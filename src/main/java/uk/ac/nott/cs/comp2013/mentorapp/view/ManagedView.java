package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.EventHandler;

/**
 * Interface for any class which a {@link ViewManager} can manage.
 */
public interface ManagedView {

  EventHandler<? super ViewChangeEvent> getOnViewChange();

  void setOnViewChange(EventHandler<? super ViewChangeEvent> eventHandler);

}

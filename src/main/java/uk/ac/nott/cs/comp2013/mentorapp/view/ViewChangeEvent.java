package uk.ac.nott.cs.comp2013.mentorapp.view;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event raised whenever a {@link ViewManager} should change the displayed view.
 */
public class ViewChangeEvent extends Event {

  private static final EventType<ViewChangeEvent> TYPE = new EventType<>("ViewChange");
  private final String view;

  public ViewChangeEvent(String view) {
    super(TYPE);
    this.view = view;
  }

  /**
   * Get the key of the view to display.
   *
   * @return view key
   */
  public String getView() {
    return view;
  }
}

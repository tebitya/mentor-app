package uk.ac.not.cs.comp2013.mentorapp.model;

import java.time.LocalDateTime;

/**
 * Interface for any entity that has an availability.
 */
public interface HasAvailability {

  LocalDateTime getStartAvailability();

  void setStartAvailability(LocalDateTime date);

  LocalDateTime setEndAvailability();

  void setEndAvailability(LocalDateTime date);
}

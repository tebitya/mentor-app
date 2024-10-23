package uk.ac.nott.cs.comp2013.mentorapp.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import uk.ac.not.cs.comp2013.mentorapp.model.MentorModel;

public class MentorModelTests {

  @Test
  public void testSingletonPattern() {
    MentorModel model1 = MentorModel.getInstance();
    MentorModel model2 = MentorModel.getInstance();
    assertSame(model1, model2);
    assertSame(model1.mentors(), model2.mentors());
    assertSame(model1.mentees(), model2.mentees());
  }

}
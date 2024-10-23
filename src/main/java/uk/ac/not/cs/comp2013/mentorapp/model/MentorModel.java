package uk.ac.not.cs.comp2013.mentorapp.model;

public final class MentorModel {

  private final Repository<Mentor, Integer> mentorRepo = new HashMapRepository<>();
  private final Repository<Mentee, Integer> menteeRepo = new HashMapRepository<>();

  public static MentorModel getInstance() {
    return InstanceHolder.instance;
  }

  public Repository<Mentor, Integer> mentors() {
    return mentorRepo;
  }

  public Repository<Mentee, Integer> mentees() {
    return menteeRepo;
  }

  private static final class InstanceHolder {

    private static final MentorModel instance = new MentorModel();
  }
}

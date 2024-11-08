package uk.ac.nott.cs.comp2013.mentorapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

public class RepositoryFactoryTests {

  private RepositoryFactory factory;

  @BeforeEach
  public void setUp() {
    factory = new RepositoryFactory();
  }

  @Test
  public void testUserRepositoryCsvMissing() throws IOException {
    Repository<User, String> repo = factory.userRepositoryFromCsv("/MISSING_DATA.csv");
    assertNull(repo);
  }

  @Test
  public void testUserRepositoryCsvPresent() throws IOException {
    Repository<User, String> repo = factory.userRepositoryFromCsv("/TEST_DATA.csv");
    assertNotNull(repo);

    Optional<User> user = repo.selectById("labarough0");
    assertTrue(user.isPresent());
    assertEquals("labarough0", user.get().getUsername());
    assertEquals("gC9!dK+z!Fq$", user.get().getPassword());
    assertInstanceOf(Mentee.class, user.get());
  }

}

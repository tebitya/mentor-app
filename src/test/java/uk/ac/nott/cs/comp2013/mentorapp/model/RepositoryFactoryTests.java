package uk.ac.nott.cs.comp2013.mentorapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  public void testUserRepositoryCsvMissing() {
    assertThrows(IOException.class, () -> factory.userHashMapRepository("/MISSING_DATA.csv"),
        "Missing data should have caused an exception");
  }

  @Test
  public void testUserRepositoryCsvPresent() throws IOException {
    Repository<User, String> repo = factory.userHashMapRepository("/TEST_DATA.csv");
    assertNotNull(repo, "Repository should have been created");

    Optional<User> user = repo.selectById("labarough0");
    assertTrue(user.isPresent(), "User should have been found");
    assertEquals("labarough0", user.get().getUsername(), "Username should be labarough0");
    assertEquals("gC9!dK+z!Fq$", user.get().getPassword(), "Password should be gC9!dK+z!Fq$");
    assertInstanceOf(Mentee.class, user.get(), "User should be a Mentee");
  }

}

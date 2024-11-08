package uk.ac.nott.cs.comp2013.mentorapp.model;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Administrator;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentee;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.Mentor;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

public class RepositoryFactory {

  /**
   * Generate a repository using data provided in a CSV file.
   *
   * @param filename name of CSV file to read
   * @return the repository
   * @throws IOException if there was an error reading the file
   */
  public Repository<User, String> userRepositoryFromCsv(String filename) throws IOException {
    Repository<User, String> repo = new HashMapRepository<>();

    try (InputStream data = RepositoryFactory.class.getResourceAsStream(filename)) {
      if (data == null) {
        return null;
      }

      Scanner fileScanner = new Scanner(data);
      fileScanner.nextLine(); // skip header row

      while (fileScanner.hasNextLine()) {
        User user = scanUser(fileScanner.nextLine());
        if (user != null) {
          repo.insert(user);
        }
      }
    }

    return repo;
  }

  private User scanUser(String row) {
    String[] values = row.split(",");

    return switch (values[2]) {
      case "MENTEE" -> new Mentee(values[0], values[1], values[3]);
      case "MENTOR" -> {
        if (values[4].isBlank() || values[5].isBlank()) {
          yield new Mentor(values[0], values[1]);
        }

        Instant start = Instant.parse(values[4]);
        Instant end = Instant.parse(values[5]);
        yield new Mentor(values[0], values[1],
            LocalDateTime.ofInstant(start, ZoneId.systemDefault()),
            LocalDateTime.ofInstant(end, ZoneId.systemDefault()));
      }
      case "ADMIN" -> new Administrator(values[0], values[1]);
      default -> null;
    };
  }
}

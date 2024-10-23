package uk.ac.nott.cs.comp2013.mentorapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.ac.not.cs.comp2013.mentorapp.model.HasId;
import uk.ac.not.cs.comp2013.mentorapp.model.HashMapRepository;

public class HashMapRepositoryTests {

  private HashMapRepository<Blob, Integer> data;

  @BeforeEach
  public void setUp() {
    data = new HashMapRepository<>();
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3})
  public void testSelectAll(int n) {
    List<Blob> blobs = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      Blob b = new Blob(i, i);
      blobs.add(b);
      data.insert(b);
    }

    List<Blob> blobs1 = data.selectAll();
    assertEquals(n, blobs1.size(), "selectAll returned incorrect number of elements");
    for (Blob b : blobs) {
      assertTrue(blobs1.contains(b), String.format("Blob with id %d missing", b.id));
    }
  }

  @Test
  public void testSelectId_Missing() {
    assertTrue(data.selectById(2).isEmpty());
  }

  @Test
  public void testSelectId_Present() {
    Blob b = new Blob(1, 1);
    data.insert(b);

    Optional<Blob> b1 = data.selectById(1);
    assertTrue(b1.isPresent());
    assertEquals(b, b1.get());
  }


  private static class Blob implements HasId<Integer> {

    private int id, value;

    public Blob(int id, int value) {
      this.id = id;
      this.value = value;
    }

    @Override
    public Integer getId() {
      return id;
    }

    @Override
    public void setId(Integer id) {
      this.id = id;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Blob b) {
        return id == b.id;
      }

      return false;
    }

    @Override
    public int hashCode() {
      return id;
    }
  }
}

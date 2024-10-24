package uk.ac.nott.cs.comp2013.mentorapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class HashMapRepository<T extends HasId<Id>, Id> implements Repository<T, Id> {

  private final HashMap<Id, T> data = new HashMap<>();

  @Override
  public List<T> selectAll() {
    return new ArrayList<>(data.values());
  }

  @Override
  public Optional<T> selectById(Id id) {
    T value = data.get(id);
    return value == null ? Optional.empty() : Optional.of(value);
  }

  @Override
  public <S extends T> S insert(@NotNull S entity) {
    if (data.containsKey(entity.getId())) {
      return null;
    }

    data.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends T> S update(@NotNull S entity) {
    if (!data.containsKey(entity.getId())) {
      return null;
    }

    data.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public void delete(Id id) {
    data.remove(id);
  }
}

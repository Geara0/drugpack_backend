package com.geara.drugpack.entities.condition;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
  @Override
  void deleteAllById(@NotNull Iterable<? extends Long> ids);
}

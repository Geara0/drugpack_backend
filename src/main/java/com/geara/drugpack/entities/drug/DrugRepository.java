package com.geara.drugpack.entities.drug;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {
  @Override
  void deleteAllById(@NotNull Iterable<? extends Long> ids);
}

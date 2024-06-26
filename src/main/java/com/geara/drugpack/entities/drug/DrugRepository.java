package com.geara.drugpack.entities.drug;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {
  Optional<Drug> findBySourceAndForeignId(Source source, Long foreignId);
}

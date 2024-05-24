package com.geara.drugpack.entities.condition;

import com.geara.drugpack.entities.drug.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
  Optional<Condition> findBySourceAndForeignId(Source source, Long foreignId);
}

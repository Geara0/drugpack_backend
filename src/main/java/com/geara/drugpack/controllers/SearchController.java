package com.geara.drugpack.controllers;

import com.geara.drugpack.entities.condition.Condition;
import com.geara.drugpack.entities.condition.ConditionRepository;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.DrugRepository;
import com.geara.drugpack.utils.Searchable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("search")
@Tag(name = "Search", description = "Methods for searching data")
public class SearchController {
  @PersistenceContext private EntityManager entityManager;

  private final DrugRepository drugRepository;
  private final ConditionRepository conditionRepository;

  @PostMapping("drugs")
  ResponseEntity<List<Drug>> searchDrugs(@RequestBody final String query) {
    return search(Drug.class, drugRepository, query);
  }

  @PostMapping("conditions")
  ResponseEntity<List<Condition>> searchConditions(@RequestBody final String query) {
    return search(Condition.class, conditionRepository, query);
  }

  <T extends Searchable<Long>> ResponseEntity<List<T>> search(
      Class<T> classType, JpaRepository<T, Long> repository, String searchTerm) {
    final var select =
        "WITH subquery AS ( "
            + "    SELECT UNNEST(STRING_TO_ARRAY(:searchTerm, ' ')) AS substring_element "
            + ") "
            + "SELECT "
            + "       SUM(LENGTH(metaphone) - LENGTH(REPLACE(metaphone, ''||rumetaphone(substring_element)||'', ''))) AS importance, "
            + "       id "
            + "FROM "
            + classType.getName()
            + ", subquery "
            + "GROUP BY id "
            + "ORDER BY importance desc ";

    final Query query = entityManager.createQuery(select);
    query.setParameter("searchTerm", searchTerm);
    query.setMaxResults(50);

    final List<Object[]> queryResult = query.getResultList();
    final var foundIds = queryResult.stream().map(e -> (Long) e[1]).collect(Collectors.toSet());

    final var res = repository.findAllById(foundIds);

    return ResponseEntity.ok(res);
  }
}

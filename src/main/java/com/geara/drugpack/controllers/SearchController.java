package com.geara.drugpack.controllers;

import com.geara.drugpack.dto.drug.DrugDto;
import com.geara.drugpack.dto.drug.DrugDtoMapper;
import com.geara.drugpack.entities.condition.Condition;
import com.geara.drugpack.entities.condition.ConditionRepository;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.DrugRepository;
import com.geara.drugpack.utils.MetaphoneUtils;
import com.geara.drugpack.utils.Searchable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Comparator;
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

  @PersistenceContext private final EntityManager entityManager;

  private final DrugDtoMapper drugDtoMapper;
  private final DrugRepository drugRepository;
  private final ConditionRepository conditionRepository;

  @PostMapping("drugs")
  ResponseEntity<List<DrugDto>> searchDrugs(@RequestBody final String query) {
    return ResponseEntity.ok(
        search(Drug.class, drugRepository, query).stream()
            .map(drugDtoMapper::drugToDrugDto)
            .toList());
  }

  @PostMapping("conditions")
  ResponseEntity<List<Condition>> searchConditions(@RequestBody final String query) {
    return ResponseEntity.ok(search(Condition.class, conditionRepository, query));
  }

  <T extends Searchable<Long>> List<T> search(
      Class<T> classType, JpaRepository<T, Long> repository, String searchTerm) {
    final var select =
        "WITH subquery AS ( "
            + "    SELECT UNNEST(STRING_TO_ARRAY(CAST(:searchTerm AS STRING), ' ')) AS substring_element "
            + ") "
            + "SELECT "
            + "       SUM(LENGTH(metaphone) - LENGTH(REPLACE(metaphone, CAST(substring_element AS STRING), ''))) AS importance, "
            + "       id "
            + "FROM "
            + classType.getName()
            + ", subquery "
            + "GROUP BY id, substring_element "
            + "ORDER BY importance desc ";

    final Query query = entityManager.createQuery(select);
    query.setParameter("searchTerm", MetaphoneUtils.generateMetaphone(searchTerm));
    query.setMaxResults(50);
    System.out.println("Search for" + MetaphoneUtils.generateMetaphone(searchTerm));

    final List<Object[]> queryResult = query.getResultList();

    final var foundIds = queryResult.stream().map(e -> (Long) e[1]).collect(Collectors.toList());

    final var results = repository.findAllById(foundIds);

    return results.stream()
        .sorted(Comparator.comparingInt(e -> foundIds.indexOf(e.getId())))
        .toList();
  }
}

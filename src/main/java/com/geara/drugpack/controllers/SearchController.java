package com.geara.drugpack.controllers;

import com.geara.drugpack.entities.condition.Condition;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.utils.MetaphoneUtils;
import com.geara.drugpack.utils.Searchable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("search")
@Tag(
    name = "Search",
    description = "Methods for searching data")
public class SearchController {
  @PersistenceContext private EntityManager entityManager;

  @PostMapping("drugs")
  ResponseEntity<List<Drug>> searchDrugs(@RequestBody final String query) {
    return search(Drug.class, query);
  }

  @PostMapping("conditions")
  ResponseEntity<List<Condition>> searchConditions(@RequestBody final String query) {
    return search(Condition.class, query);
  }

  <T extends Searchable<Long>> ResponseEntity<List<T>> search(Class<T> classType, String searchTerm) {
    final var select =
        "SELECT e FROM " + classType.getName() + " e WHERE e.metaphone LIKE :searchTerm";

    TypedQuery<T> q = entityManager.createQuery(select, classType);
    q.setParameter("searchTerm", "%" + MetaphoneUtils.generateMetaphone(searchTerm) + "%");
    q.setMaxResults(50);
    return ResponseEntity.ok(q.getResultList());
  }
}

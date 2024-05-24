package com.geara.drugpack.configs;

import com.geara.drugpack.entities.condition.ConditionService;
import com.geara.drugpack.entities.condition.aurora.condition.AuroraConditionService;
import com.geara.drugpack.entities.drug.DrugService;
import com.geara.drugpack.entities.drug.aurora.activesubstance.AuroraActiveSubstanceService;
import com.geara.drugpack.entities.drug.aurora.description.AuroraDescriptionService;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrugService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@Component
public class DatabaseInitializer {

  private final DataSource dataSource;
  private final AuroraDrugService auroraDrugService;
  private final AuroraActiveSubstanceService auroraActiveSubstanceService;
  private final AuroraDescriptionService auroraDescriptionService;
  private final AuroraConditionService auroraConditionService;
  private final DrugService drugService;
  private final ConditionService conditionService;

  @Value("${sources.aurora}")
  private String auroraPath;

  @PostConstruct
  @Transactional
  public void initialize() {
    try (Connection connection = dataSource.getConnection()) {
      // add fuzzystrmatch to db
      String sql = "CREATE EXTENSION IF NOT EXISTS fuzzystrmatch; ";

      // load aurora drugs
      auroraActiveSubstanceService.loadFromJson(
          auroraPath + "aurora_dict_active_substances_20240508.json");
      auroraDescriptionService.loadFromJson(auroraPath + "aurora_lib_desc_20240501.json");
      auroraDrugService.loadFromJson(auroraPath + "aurora_inventory_brief_20240501.json");

      // load aurora conditions
      auroraConditionService.loadFromJson(auroraPath + "aurora_classes_mkb_synon_20240523.json");

      drugService.update();
      conditionService.update();

      try (Statement statement = connection.createStatement()) {
        statement.execute(sql);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize database", e);
    }
  }
}

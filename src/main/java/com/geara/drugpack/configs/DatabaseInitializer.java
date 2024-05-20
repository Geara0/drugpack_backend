package com.geara.drugpack.configs;

import com.geara.drugpack.entities.drug.DrugService;
import com.geara.drugpack.entities.drug.aurora.activesubstance.AuroraActiveSubstanceService;
import com.geara.drugpack.entities.drug.aurora.description.AuroraDescriptionService;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrugService;
import com.geara.drugpack.utils.MetaphoneUtils;
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
  private final DrugService drugService;

  @Value("${sources.aurora}")
  private String auroraPath;

  @PostConstruct
  @Transactional
  public void initialize() {
    try (Connection connection = dataSource.getConnection()) {
      String sql =
          // add fuzzystrmatch to db
          "CREATE EXTENSION IF NOT EXISTS fuzzystrmatch; "
              +
              // init metaphone function
              MetaphoneUtils.getSqlMetaphone();

      auroraActiveSubstanceService.loadFromJson(
          auroraPath + "aurora_dict_active_substances_20240508.json");
      auroraDescriptionService.loadFromJson(auroraPath + "aurora_lib_desc_20240501.json");
      auroraDrugService.loadFromJson(auroraPath + "aurora_inventory_brief_20240501.json");

      drugService.update();

      try (Statement statement = connection.createStatement()) {
        statement.execute(sql);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize database", e);
    }
  }
}

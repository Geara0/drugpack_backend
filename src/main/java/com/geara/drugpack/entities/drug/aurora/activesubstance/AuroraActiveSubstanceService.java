package com.geara.drugpack.entities.drug.aurora.activesubstance;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuroraActiveSubstanceService {
  private final AuroraActiveSubstanceRepository repository;

  public void loadFromJson(String path) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final List<AuroraActiveSubstance> substances =
        objectMapper
            .registerModule(new ParameterNamesModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(
                new File(path),
                objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, AuroraActiveSubstance.class));

    repository.saveAll(substances);
  }
}

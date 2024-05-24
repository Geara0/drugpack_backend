package com.geara.drugpack.entities.condition.aurora.condition;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuroraConditionService {

  private final AuroraConditionRepository repository;

  public void loadFromJson(String path) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final List<AuroraCondition> conditions =
        objectMapper
            .registerModule(new ParameterNamesModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(
                new File(path),
                objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, AuroraCondition.class));

    repository.saveAll(conditions);
  }
}

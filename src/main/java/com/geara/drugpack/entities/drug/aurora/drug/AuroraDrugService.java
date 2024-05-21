package com.geara.drugpack.entities.drug.aurora.drug;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.geara.drugpack.dto.DrugDto;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.aurora.activesubstance.AuroraActiveSubstanceRepository;
import com.geara.drugpack.entities.drug.aurora.description.AuroraDescriptionRepository;
import java.io.File;
import java.io.IOException;
import java.util.*;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuroraDrugService {

  private final AuroraDrugRepository drugRepository;
  private final AuroraDescriptionRepository descriptionRepository;
  private final AuroraActiveSubstanceRepository activeSubstanceRepository;

  public void loadFromJson(String path) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<AuroraDrug> drugs =
        objectMapper
            .registerModule(new ParameterNamesModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(
                new File(path),
                objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, AuroraDrug.class));

    for (final var drug : drugs) {
      final var asId = drug.getAsId();
      final var descId = drug.getDescId();

      if (asId != null) {
        final var substance = activeSubstanceRepository.findById(asId).get();
        drug.setActiveSubstance(substance);
      }

      if (descId != null) {
        final var description = descriptionRepository.findById(descId).get();
        drug.setDescription(description);
      }
    }

    drugRepository.saveAll(drugs);
  }

  public DrugDto getDto(@NotNull Drug drug) {
    if (drug == null) throw new RuntimeException();

    final var auroraDrug = drugRepository.findById(drug.getForeignId()).get();

    return new DrugDto(
        drug.getId(),
        auroraDrug.getDescription().toString(),
        auroraDrug.getPrepFull(),
        auroraDrug.getPrepShort(),
        auroraDrug.getAmount(),
        auroraDrug.getPackingFull(),
        auroraDrug.getPackingShort(),
        auroraDrug.getBarcode(),
        auroraDrug.getFirms(),
        auroraDrug.getActiveSubstance().toString(),
        auroraDrug.getLifetimeText(),
        auroraDrug.getLifetimeMonth(),
        auroraDrug.getScText(),
        auroraDrug.getScShort(),
        new byte[0],
        auroraDrug.getDosageFormFullName());
  }
}

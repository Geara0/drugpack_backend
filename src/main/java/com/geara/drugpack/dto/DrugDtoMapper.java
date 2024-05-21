package com.geara.drugpack.dto;

import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrugService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrugDtoMapper {
  private final AuroraDrugService auroraDrugService;

  public DrugDto drugToDrugDto(@NotNull Drug drug) {
    return switch (drug.getSource()) {
      case aurora -> auroraDrugService.getDto(drug);
      case null -> throw new RuntimeException();
    };
  }
}

package com.geara.drugpack.dto.compatibility;

import com.geara.drugpack.entities.account.CompatibilityData;
import com.geara.drugpack.entities.drug.Source;
import com.geara.drugpack.entities.drug.aurora.compatibility.AuroraCompatibilityRepository;
import com.geara.drugpack.entities.drug.aurora.compatibility.AuroraCompatibilityService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompatibilityDtoMapper {
  private final AuroraCompatibilityService auroraInteractionService;
  private final AuroraCompatibilityRepository auroraCompatibilityRepository;

  public @NotNull List<CompatibilityDto> compatibilityDataToCompatibility(
      @NotNull Source source, @NotNull CompatibilityData data) {
    return switch (source) {
      case aurora ->
          data.ids.stream()
              .map(
                  e ->
                      auroraInteractionService.getDto(
                          auroraCompatibilityRepository.getReferenceById(e)))
              .toList();
      case null -> throw new IllegalArgumentException();
    };
  }
}

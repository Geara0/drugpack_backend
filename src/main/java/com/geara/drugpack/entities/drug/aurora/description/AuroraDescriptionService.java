package com.geara.drugpack.entities.drug.aurora.description;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.geara.drugpack.dto.description.DescriptionDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuroraDescriptionService {
  private final AuroraDescriptionRepository repository;

  public void loadFromJson(String path) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final List<AuroraDescription> descriptions =
        objectMapper
            .registerModule(new ParameterNamesModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(
                new File(path),
                objectMapper
                    .getTypeFactory()
                    .constructCollectionType(List.class, AuroraDescription.class));

    repository.saveAll(descriptions);
  }

  public  @NotNull DescriptionDto getDto(@NotNull AuroraDescription description) {
    if (description == null) throw new IllegalArgumentException("description is null");

    return new DescriptionDto(
            description.getComposition(),
            description.getCompositionDf(),
            description.getCharacters(),
            description.getPharmaActions(),
            description.getActonOrg(),
            description.getComponentsProperties(),
            description.getDrugFormDescr(),
            description.getPharmaKinetic(),
            description.getPharmaDynamic(),
            description.getPharmaProperties(),
            description.getClinicalPharmacology(),
            description.getDirection(),
            description.getIndications(),
            description.getRecommendations(),
            description.getContraindications(),
            description.getPregnancyUse(),
            description.getUseMethodAndDoses(),
            description.getInstrForPac(),
            description.getSideActions(),
            description.getInteractions(),
            description.getOverdose(),
            description.getPrecautions(),
            description.getSpecialGuidelines(),
            description.getForm(),
            description.getAptekaCondition(),
            description.getLiterature(),
            description.getComment(),
            description.getManufacturer(),
            description.getApply(),
            description.getComplectation(),
            description.getPrinciple(),
            description.getMainTechChars(),
            description.getObservation(),
            description.getObservation(),
            description.getService(),
            description.getPharmGroups(),
            description.getPharmActions());
  }
}

package com.geara.drugpack.entities.drug.aurora.drug;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.geara.drugpack.dto.activesubstance.ActiveSubstanceDto;
import com.geara.drugpack.dto.drug.DrugDto;
import com.geara.drugpack.dto.description.DescriptionDto;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.aurora.activesubstance.AuroraActiveSubstanceRepository;
import com.geara.drugpack.entities.drug.aurora.description.AuroraDescriptionRepository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class AuroraDrugService {

  @Value("${sources.aurora}")
  private String auroraPath;

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

  public @NotNull DrugDto getDto(@NotNull Drug drug) {
    if (drug == null) throw new RuntimeException();

    final var auroraDrug = drugRepository.findById(drug.getForeignId()).get();
    final var auroraDescription = auroraDrug.getDescription();
    final var auroraActiveSubstance = auroraDrug.getActiveSubstance();

    final var description =
        auroraDescription != null
            ? new DescriptionDto(
                auroraDescription.getComposition(),
                auroraDescription.getCompositionDf(),
                auroraDescription.getCharacters(),
                auroraDescription.getPharmaActions(),
                auroraDescription.getActonOrg(),
                auroraDescription.getComponentsProperties(),
                auroraDescription.getDrugFormDescr(),
                auroraDescription.getPharmaKinetic(),
                auroraDescription.getPharmaDynamic(),
                auroraDescription.getPharmaProperties(),
                auroraDescription.getClinicalPharmacology(),
                auroraDescription.getDirection(),
                auroraDescription.getIndications(),
                auroraDescription.getRecommendations(),
                auroraDescription.getContraindications(),
                auroraDescription.getPregnancyUse(),
                auroraDescription.getUseMethodAndDoses(),
                auroraDescription.getInstrForPac(),
                auroraDescription.getSideActions(),
                auroraDescription.getInteractions(),
                auroraDescription.getOverdose(),
                auroraDescription.getPrecautions(),
                auroraDescription.getSpecialGuidelines(),
                auroraDescription.getForm(),
                auroraDescription.getAptekaCondition(),
                auroraDescription.getLiterature(),
                auroraDescription.getComment(),
                auroraDescription.getManufacturer(),
                auroraDescription.getApply(),
                auroraDescription.getComplectation(),
                auroraDescription.getPrinciple(),
                auroraDescription.getMainTechChars(),
                auroraDescription.getObservation(),
                auroraDescription.getObservation(),
                auroraDescription.getService(),
                auroraDescription.getPharmGroups(),
                auroraDescription.getPharmActions())
            : null;

    final var activeSubstance =
        auroraActiveSubstance != null
            ? new ActiveSubstanceDto(
                auroraActiveSubstance.getAsNameRus(),
                auroraActiveSubstance.getAsNameEng(),
                auroraActiveSubstance.getAsNameLatGenitive(),
                auroraActiveSubstance.getAsNameLat(),
                auroraActiveSubstance.getTcfsDescId(),
                auroraActiveSubstance.getFdaCat(),
                auroraActiveSubstance.getAsFormula(),
                auroraActiveSubstance.getAsFormulaHtml(),
                auroraActiveSubstance.getAsCasCode(),
                auroraActiveSubstance.getAsterisk())
            : null;

    return new DrugDto(
        drug.getId(),
        description,
        auroraDrug.getPrepFull(),
        auroraDrug.getPrepShort(),
        auroraDrug.getAmount(),
        auroraDrug.getPackingFull(),
        auroraDrug.getPackingShort(),
        auroraDrug.getBarcode(),
        auroraDrug.getFirms(),
        activeSubstance,
        auroraDrug.getLifetimeText(),
        auroraDrug.getLifetimeMonth(),
        auroraDrug.getScText(),
        auroraDrug.getScShort(),
        getImageBytes(getImagesPath() + auroraDrug.getPicname()),
        auroraDrug.getDosageFormFullName());
  }

  private static byte[] getImageBytes(String imagePath) {
    try {
      BufferedImage image = ImageIO.read(new File(imagePath));

      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      ImageIO.write(image, "jpg", baos);

      return baos.toByteArray();
    } catch (IOException e) {
      return null;
    }
  }

  private String getImagesPath() {
    return auroraPath + "aurora_inventory_pics";
  }
}

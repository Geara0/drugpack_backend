package com.geara.drugpack.entities.drug;

import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrug;
import com.geara.drugpack.utils.MetaphoneUtils;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface DrugMapper {
  @Mapping(target = "foreignId", source = "packingId")
  @Mapping(target = "source", constant = "aurora")
  @Mapping(target = "accounts", ignore = true)
  @Mapping(target = "metaphone", source = ".", qualifiedByName = "auroraMetaphone")
  Drug auroraToDrug(@NotNull AuroraDrug drug);

  @Named("auroraMetaphone")
  default String auroraMetaphone(AuroraDrug drug) {
    return MetaphoneUtils.generateMetaphone(Arrays.asList(drug.getPrepFull(), drug.getFirms()));
  }
}

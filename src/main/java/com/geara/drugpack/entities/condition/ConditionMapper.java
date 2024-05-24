package com.geara.drugpack.entities.condition;

import com.geara.drugpack.entities.condition.aurora.condition.AuroraCondition;
import com.geara.drugpack.utils.MetaphoneUtils;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ConditionMapper {
    @Mapping(target = "foreignId", source = "id")
    @Mapping(target = "source", constant = "aurora")
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "metaphone", source = ".", qualifiedByName = "auroraMetaphone")
    @NotNull Condition auroraToCondition(@NotNull AuroraCondition condition);

    @Named("auroraMetaphone")
    default String auroraMetaphone(AuroraCondition condition) {
        return MetaphoneUtils.generateMetaphone(condition.getSynonymName());
    }
}


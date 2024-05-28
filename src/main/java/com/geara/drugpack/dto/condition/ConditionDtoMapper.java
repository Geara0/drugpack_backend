package com.geara.drugpack.dto.condition;

import com.geara.drugpack.entities.condition.Condition;
import com.geara.drugpack.entities.condition.aurora.condition.AuroraConditionService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConditionDtoMapper {
    private final AuroraConditionService auroraConditionService;

    public @NotNull ConditionDto conditionToConditionDto(@NotNull Condition condition) {
        return switch (condition.getSource()) {
            case aurora -> auroraConditionService.getDto(condition);
            case null -> throw new IllegalArgumentException();
        };
    }
}

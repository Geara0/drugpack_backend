package com.geara.drugpack.responses;

import com.geara.drugpack.dto.compatibility.CompatibilityDto;
import com.geara.drugpack.dto.condition.ConditionDto;
import com.geara.drugpack.dto.drug.DrugDto;
import com.geara.drugpack.entities.drug.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class GetAccountResponse {
    List<ConditionDto>conditions;
    List<DrugDto> drugs;
    Map<Source, List<CompatibilityDto>> compatibilities;
}

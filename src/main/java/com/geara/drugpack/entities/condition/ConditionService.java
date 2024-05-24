package com.geara.drugpack.entities.condition;

import com.geara.drugpack.entities.condition.aurora.condition.AuroraConditionRepository;
import com.geara.drugpack.entities.drug.Source;
import com.geara.drugpack.utils.MetaphoneUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ConditionService {
  private final ConditionRepository conditionRepository;
  private final AuroraConditionRepository auroraConditionRepository;
  private final ConditionMapper auroraConditionMapper;

  public void update() {
    final var auroraConditions = auroraConditionRepository.findAll();
    final var newConditions = new ArrayList<Condition>();

    for (final var auroraCondition : auroraConditions) {
      final var condition =
          conditionRepository.findBySourceAndForeignId(Source.aurora, auroraCondition.getId());
      if (condition.isEmpty()) {
        newConditions.add(auroraConditionMapper.auroraToCondition(auroraCondition));
        continue;
      }

      final var upd = condition.get();
      upd.setMetaphone(MetaphoneUtils.generateMetaphone(auroraCondition.getSynonymName()));
      newConditions.add(upd);
    }

    conditionRepository.saveAll(newConditions);
  }
}

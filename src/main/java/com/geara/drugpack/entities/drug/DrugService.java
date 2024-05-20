package com.geara.drugpack.entities.drug;

import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrugRepository;
import com.geara.drugpack.entities.drug.mappers.AuroraDrugMapper;
import com.geara.drugpack.utils.MetaphoneUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DrugService {
  private final DrugRepository drugRepository;
  private final AuroraDrugRepository auroraDrugRepository;
  private final AuroraDrugMapper auroraDrugMapper;

  public void update() {
    final var auroraDrugs = auroraDrugRepository.findAll();
    final var newDrugs = new ArrayList<Drug>();

    for (final var auroraDrug : auroraDrugs) {
      final var drug =
          drugRepository.findBySourceAndForeignId(DrugSource.aurora, auroraDrug.getPackingId());
      if (drug.isEmpty()) {
        newDrugs.add(auroraDrugMapper.auroraToDrug(auroraDrug));
        continue;
      }

      final var upd = drug.get();
      upd.setMetaphone(
          MetaphoneUtils.generateMetaphone(
              Arrays.asList(auroraDrug.getPrepFull(), auroraDrug.getFirms())));
      newDrugs.add(upd);
    }

    drugRepository.saveAll(newDrugs);
  }
}

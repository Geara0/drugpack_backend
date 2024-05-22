package com.geara.drugpack.dto.drug;

import com.geara.drugpack.dto.activesubstance.ActiveSubstanceDto;
import com.geara.drugpack.dto.description.DescriptionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Препарат")
public class DrugDto {
  @Schema(description = "Идентификатор (в таблице drug)")
  private Long id;

  @Schema(description = "Описание", nullable = true)
  private DescriptionDto description;

  @Schema(description = "Полное название")
  private String name;

  @Schema(description = "Cокращенное название", nullable = true)
  private String nameShort;

  @Schema(description = "Общее количество элементов (таблеток, ампул) в упаковке", nullable = true)
  private String amount;

  @Schema(description = "Полное описание товарной упаковки", nullable = true)
  private String packing;

  @Schema(description = "Сокращенное описание товарной упаковки", nullable = true)
  private String packingShort;

  @Schema(description = "Штрихкод EAN", nullable = true)
  private String barcode;

  @Schema(description = "Название фирмы производителя/упаковщика")
  private String firm;

  @Schema(description = "Действующее вещество", nullable = true)
  private ActiveSubstanceDto activeSubstance;

  @Schema(description = "Срок годности.", nullable = true)
  private String lifetimeText;

  @Schema(description = "Срок годности в месяцах.", nullable = true)
  private Double lifetimeMonth;

  @Schema(description = "Условия хранения", nullable = true)
  private String storageConditions;

  @Schema(description = "Условия хранения в сокращенном виде", nullable = true)
  private String storageConditionsShort;

  @Schema(description = "Картинка (массив может быть пустым)")
  private byte[] picname;

  @Schema(description = "Форма препарата", nullable = true, example = "таблетки")
  private String form;
}

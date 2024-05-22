package com.geara.drugpack.dto.activesubstance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Описание препарата")
public class ActiveSubstanceDto {

  @Schema(description = "Название действующего вещетсва на русском языке")
  private String asNameRus;

  @Schema(description = "Навзание действующего вещетсва на английском языке")
  private String asNameEng;

  @Schema(
      description = "Название действующего вещества на латинском языке в родительном падеже",
      nullable = true)
  private String asNameLatGenitive;

  @Schema(description = "Название действующего вещества на латинском языке", nullable = true)
  private String asNameLat;

  @Schema(
      description =
          "Идентификатор типовой клинико-фармакологической статьи о действующем веществе из раздела Описания library_as_description?desc_id={desc_id}",
      nullable = true)
  private long tcfsDescId;

  @Schema(description = "Категория действия на плод FDA", nullable = true)
  private String fdaCat;

  @Schema(description = "Химическая формула", nullable = true)
  private String asFormula;

  @Schema(description = "Химическая формула (HTML)", nullable = true)
  private String asFormulaHtml;

  @Schema(description = "CAS код действующего вещества", nullable = true)
  private String asCasCode;

  @Schema(
      description = "При asterisk=true действующее вещество входит в список МНН",
      nullable = true)
  private Boolean asterisk;
}

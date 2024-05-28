package com.geara.drugpack.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Болезнь/состояние по МКБ-10")
public class ConditionDto {
  @Schema(description = "Идентификатор (в таблице condition)")
  private long id;

  @Schema(description = "Название болезни/состояния")
  private String synonymName;
}

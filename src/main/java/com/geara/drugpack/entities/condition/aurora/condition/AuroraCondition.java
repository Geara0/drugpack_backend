package com.geara.drugpack.entities.condition.aurora.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

// https://aurora.rlsnet.ru/Help/Api/GET-api-classes_mkb_synon_mkb_id

@Entity
@Table(schema = "aurora", name = "condition")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuroraCondition {
  @Id private long id;

  @Column(length = -1)
  @Schema(description = "Дата актуализации записи")
  private String actdate;

  @Schema(description = "ID узла МКБ-10. Ссылка на справочник МКБ-10 (метод classes?classcode=mkb)")
  @JsonProperty(value = "mkb_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long mkbId;

  @Schema(description = "ID синонима болезни или состояния")
  @JsonProperty(value = "synonim_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long synonymId;

  @Column(length = 256)
  @Schema(description = "Название синонима болезни или состояния")
  @JsonProperty(value = "synonim_name", access = JsonProperty.Access.WRITE_ONLY)
  private String synonymName;
}

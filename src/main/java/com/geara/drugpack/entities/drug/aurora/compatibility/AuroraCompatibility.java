package com.geara.drugpack.entities.drug.aurora.compatibility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(schema = "aurora", name = "compatibility")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"leftSubstance", "rightSubstance"})
public class AuroraCompatibility {
  @Id private long id;

  @Schema(description = "id действующего вещества слева")
  @JsonProperty(value = "as1_id", access = JsonProperty.Access.WRITE_ONLY)
  private long leftAsId;

  @Schema(description = "id действующего вещества справа")
  @JsonProperty(value = "as2_id", access = JsonProperty.Access.WRITE_ONLY)
  private long rightAsId;

  @Schema(
      description =
          """
              числовое представление класса/категории взаимодействия.
              1 - Одновременное назначение возможно/допустимо
              2 - При одновременном назначении требуется оценка его риска и пользы
              3 - Одновременное назначение не рекомендуется/противопоказано""",
      type = "integer",
      allowableValues = "1,2,3")
  @JsonProperty(value = "class_id", access = JsonProperty.Access.WRITE_ONLY)
  private int classId;

  @Column(length = 128)
  @Schema(description = "название класса взаимодействия")
  @JsonProperty(value = "class_name", access = JsonProperty.Access.WRITE_ONLY)
  private String className;

  @Schema(
      description =
          """
             числовое представление подкласса взаимодействия в рамках дополнительной классификации.
             1 - Следует соблюдать осторожность, необходимы наблюдение/контроль
             2 - Взаимодействие возможно, хотя его клиническая значимость не определена/не изучалась
             3 - Усиление побочного эффекта/повышение токсичности
             4 - Усиление/пролонгация эффекта
             5 - Снижение выраженности/продолжительности эффекта
             6 - Требуется изменение режима дозирования
             """,
      type = "integer",
      allowableValues = "1,2,3,4,5,6",
      nullable = true)
  @JsonProperty(value = "subclass_id", access = JsonProperty.Access.WRITE_ONLY)
  private Integer subclassId;

  @Column(length = 128)
  @Schema(description = "название подкласса взаимодействия")
  @JsonProperty(value = "subclass_name", access = JsonProperty.Access.WRITE_ONLY)
  private String subclassName;

  @Schema(
      description =
          """
             числовое представление направленности взаимодействия.
             1 - Неопределено
             2 - Справа налево
             3 - Слева направо
             4 - ???
          """,
      type = "integer",
      allowableValues = "1,2,3,4")
  @JsonProperty(value = "direction_id", access = JsonProperty.Access.WRITE_ONLY)
  private int directionId;

  @Column(length = 300)
  @Schema(description = "название направленности взаимодействия")
  @JsonProperty(value = "direction_name", access = JsonProperty.Access.WRITE_ONLY)
  private String directionName;

  @Column(length = -1)
  @Schema(
      description =
          "название основного, официального, источника, из которого взята информация о взаимодействии")
  @JsonProperty(value = "official_src_name", access = JsonProperty.Access.WRITE_ONLY)
  private String officialSrcName;

  @Column(length = -1)
  @Schema(description = "текстовое описание взаимодействия, взятое из основного источника")
  @JsonProperty(value = "official_src_note", access = JsonProperty.Access.WRITE_ONLY)
  private String officialSrcNote;

  @Schema(description = "дата последней актуализации сведений из основного источника")
  @JsonProperty(value = "official_src_date", access = JsonProperty.Access.WRITE_ONLY)
  private String officialSrcDate;

  @Column(length = -1)
  @Schema(
      description =
          "название дополнительного авторитетного источника, из которого взята информация о взаимодействии; в случае нескольких источников – будут указаны все, разделены между собой слеш-символом / ")
  @JsonProperty(value = "alternative_src_name", access = JsonProperty.Access.WRITE_ONLY)
  private String alternativeSrcName;

  @Column(length = -1)
  @Schema(
      description =
          "текстовое описание взаимодействия, взятое из дополнительного источника; в случае нескольких источников – будут последовательно представлены все имеющиеся фрагменты с указанием в заголовке названия источника")
  @JsonProperty(value = "alternative_src_note", access = JsonProperty.Access.WRITE_ONLY)
  private String alternativeSrcNote;

  @Schema(
      description =
          "дата последней актуализации сведений из дополнительного источника; в случае нескольких источников – будет указана наиболее актуальная среди всех имеющихся")
  @JsonProperty(value = "alternative_src_date", access = JsonProperty.Access.WRITE_ONLY)
  private String alternativeSrcDate;
}

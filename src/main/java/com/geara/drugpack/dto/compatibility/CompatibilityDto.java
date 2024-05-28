package com.geara.drugpack.dto.compatibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Совместимость двух действующих веществ")
public class CompatibilityDto {
  @Schema(description = "id действующего вещества слева")
  private long leftAsId;

  @Schema(description = "id действующего вещества справа")
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
  private int classId;

  @Schema(description = "название класса взаимодействия")
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
  private Integer subclassId;

  @Schema(description = "название подкласса взаимодействия")
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
  private int directionId;

  @Schema(description = "название направленности взаимодействия")
  private String directionName;

  @Schema(
      description =
          "название основного, официального, источника, из которого взята информация о взаимодействии")
  private String officialSrcName;

  @Schema(description = "текстовое описание взаимодействия, взятое из основного источника")
  private String officialSrcNote;

  @Schema(description = "дата последней актуализации сведений из основного источника")
  private String officialSrcDate;

  @Schema(
      description =
          "название дополнительного авторитетного источника, из которого взята информация о взаимодействии; в случае нескольких источников – будут указаны все, разделены между собой слеш-символом / ")
  private String alternativeSrcName;

  @Schema(
      description =
          "текстовое описание взаимодействия, взятое из дополнительного источника; в случае нескольких источников – будут последовательно представлены все имеющиеся фрагменты с указанием в заголовке названия источника")
  private String alternativeSrcNote;

  @Schema(
      description =
          "дата последней актуализации сведений из дополнительного источника; в случае нескольких источников – будет указана наиболее актуальная среди всех имеющихся")
  private String alternativeSrcDate;
}

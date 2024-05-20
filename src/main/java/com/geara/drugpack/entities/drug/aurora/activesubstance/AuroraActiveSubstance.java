package com.geara.drugpack.entities.drug.aurora.activesubstance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrug;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// https://aurora.rlsnet.ru/Help/Api/GET-api-dict_active_substances-id

@Entity
@Table(schema = "aurora", name = "active_substances")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"drugs"})
public class AuroraActiveSubstance {
  @Id
  @Schema(description = "Идентификатор действующего вещества.")
  private long id;

  @OneToMany(mappedBy = "activeSubstance")
  @ToString.Exclude
  private List<AuroraDrug> drugs;

  @Column(length = 768)
  @Schema(description = "название действующего вещетсва на русском языке.")
  @JsonProperty(value = "as_name_rus", access = JsonProperty.Access.WRITE_ONLY)
  private String asNameRus;

  @Column(length = 768)
  @Schema(description = "Навзание действующего вещетсва на английском языке.")
  @JsonProperty(value = "as_name_eng", access = JsonProperty.Access.WRITE_ONLY)
  private String asNameEng;

  @Column(length = 500)
  @Schema(description = "Название действующего вещества на латинском языке в родительном падеже.")
  @JsonProperty(value = "as_name_lat_genitive", access = JsonProperty.Access.WRITE_ONLY)
  private String asNameLatGenitive;

  @Column(length = 500)
  @Schema(description = "Название действующего вещества на латинском языке.")
  @JsonProperty(value = "as_name_lat", access = JsonProperty.Access.WRITE_ONLY)
  private String asNameLat;

  @Schema(description = "Дата актуализации записи.")
  @Column(length = -1)
  private String actdate;

  @Schema(
      description =
          "Идентификатор описания действующего вещества из раздела Описания library_as_description?desc_id={desc_id}")
  @JsonProperty(value = "ac_desc_id", access = JsonProperty.Access.WRITE_ONLY)
  private long acDescId;

  @Schema(
      description =
          "Идентификатор типовой клинико-фармакологической статьи о действующем веществе из раздела Описания library_as_description?desc_id={desc_id}")
  @JsonProperty(value = "tcfs_desc_id", access = JsonProperty.Access.WRITE_ONLY)
  private long tcfsDescId;

  @Column(length = 50)
  @Schema(description = "Категория действия на плод FDA.")
  @JsonProperty(value = "fda_cat", access = JsonProperty.Access.WRITE_ONLY)
  private String fdaCat;

  @Column(length = 200)
  @Schema(description = "Химическая формула")
  @JsonProperty(value = "as_formula", access = JsonProperty.Access.WRITE_ONLY)
  private String asFormula;

  @Column(length = 200)
  @Schema(description = "Химическая формула (HTML)")
  @JsonProperty(value = "as_formula_html", access = JsonProperty.Access.WRITE_ONLY)
  private String asFormulaHtml;

  @Column(length = 100)
  @Schema(description = "CAS код действующего вещества.")
  @JsonProperty(value = "as_cas_code", access = JsonProperty.Access.WRITE_ONLY)
  private String asCasCode;

  @Schema(description = "При asterisk=true действующее вещество входит в список МНН.")
  private Boolean asterisk;
}

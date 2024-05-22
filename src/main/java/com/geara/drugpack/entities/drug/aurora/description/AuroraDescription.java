package com.geara.drugpack.entities.drug.aurora.description;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrug;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// https://aurora.rlsnet.ru/Help/Api/GET-api-library_description_desc_id

@Entity
@Table(schema = "aurora", name = "description")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"drugs"})
public class AuroraDescription {
  @Id
  @Schema(description = "Идентификатор описания.")
  @JsonProperty(value = "desc_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long descId;

  @OneToMany(mappedBy = "description")
  @ToString.Exclude
  private List<AuroraDrug> drugs;

  @Column(length = -1)
  @Schema(description = "Дата подготовки описания.")
  @JsonProperty(value = "actdate", access = JsonProperty.Access.WRITE_ONLY)
  private String actdate;

  @Column(length = -1)
  @Schema(description = "Название описания.")
  @JsonProperty(value = "desc_name", access = JsonProperty.Access.WRITE_ONLY)
  private String descName;

  @Column(length = -1)
  @Schema(description = "Состав и форма выпуска")
  @JsonProperty(value = "COMPOSITION", access = JsonProperty.Access.WRITE_ONLY)
  private String composition;

  @Column(length = -1)
  @Schema(description = "Состав")
  @JsonProperty(value = "COMPOSITION_DF", access = JsonProperty.Access.WRITE_ONLY)
  private String compositionDf;

  @Column(length = -1)
  @Schema(description = "Характеристика")
  @JsonProperty(value = "CHARACTERS", access = JsonProperty.Access.WRITE_ONLY)
  private String characters;

  @Column(length = -1)
  @Schema(description = "Фармакологические действия.")
  @JsonProperty(value = "PHARMAACTIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmaActions;

  @Column(length = -1)
  @Schema(description = "Действие на организм.")
  @JsonProperty(value = "ACTONORG", access = JsonProperty.Access.WRITE_ONLY)
  private String actonOrg;

  @Column(length = -1)
  @Schema(description = "Свойства компонентов.")
  @JsonProperty(value = "COMPONENTSPROPERTIES", access = JsonProperty.Access.WRITE_ONLY)
  private String componentsProperties;

  @Column(length = -1)
  @Schema(description = "Описание лекарственной формы.")
  @JsonProperty(value = "DRUGFORMDESCR", access = JsonProperty.Access.WRITE_ONLY)
  private String drugFormDescr;

  @Column(length = -1)
  @Schema(description = "Фармакокинетика.")
  @JsonProperty(value = "PHARMAKINETIC", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmaKinetic;

  @Column(length = -1)
  @Schema(description = "Фармакодинамика.")
  @JsonProperty(value = "PHARMADYNAMIC", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmaDynamic;

  @Column(length = -1)
  @Schema(description = "Фармакологические (иммунобиологические) свойства.")
  @JsonProperty(value = "PHARMAPROPERTIES", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmaProperties;

  @Column(length = -1)
  @Schema(description = "Клиническая фармакология.")
  @JsonProperty(value = "CLINICALPHARMACOLOGY", access = JsonProperty.Access.WRITE_ONLY)
  private String clinicalPharmacology;

  @Column(length = -1)
  @Schema(description = "Инструкция.")
  @JsonProperty(value = "DIRECTION", access = JsonProperty.Access.WRITE_ONLY)
  private String direction;

  @Column(length = -1)
  @Schema(description = "Показания.")
  @JsonProperty(value = "INDICATIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String indications;

  @Column(length = -1)
  @Schema(description = "Рекомендации.")
  @JsonProperty(value = "RECOMMENDATIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String recommendations;

  @Column(length = -1)
  @Schema(description = "Противопоказания.")
  @JsonProperty(value = "CONTRAINDICATIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String contraindications;

  @Column(length = -1)
  @Schema(description = "Применение при беременности и кормлении грудью.")
  @JsonProperty(value = "PREGNANCYUSE", access = JsonProperty.Access.WRITE_ONLY)
  private String pregnancyUse;

  @Column(length = -1)
  @Schema(description = "Способ применения и дозы.")
  @JsonProperty(value = "USEMETHODANDDOSES", access = JsonProperty.Access.WRITE_ONLY)
  private String useMethodAndDoses;

  @Column(length = -1)
  @Schema(description = "Инструкция для пациента.")
  @JsonProperty(value = "INSTRFORPAC", access = JsonProperty.Access.WRITE_ONLY)
  private String instrForPac;

  @Column(length = -1)
  @Schema(description = "Побочные действия.")
  @JsonProperty(value = "SIDEACTIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String sideActions;

  @Column(length = -1)
  @Schema(description = "Взаимодействие.")
  @JsonProperty(value = "INTERACTIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String interactions;

  @Column(length = -1)
  @Schema(description = "Передозировка.")
  @JsonProperty(value = "OVERDOSE", access = JsonProperty.Access.WRITE_ONLY)
  private String overdose;

  @Column(length = -1)
  @Schema(description = "Меры предосторожности.")
  @JsonProperty(value = "PRECAUTIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String precautions;

  @Column(length = -1)
  @Schema(description = "Особые указания.")
  @JsonProperty(value = "SPECIALGUIDELINES", access = JsonProperty.Access.WRITE_ONLY)
  private String specialGuidelines;

  @Column(length = -1)
  @Schema(description = "Форма выпуска.")
  @JsonProperty(value = "FORM", access = JsonProperty.Access.WRITE_ONLY)
  private String form;

  @Column(length = -1)
  @Schema(description = "Условия отпуска из аптек.")
  @JsonProperty(value = "APTEKA_CONDITION", access = JsonProperty.Access.WRITE_ONLY)
  private String aptekaCondition;

  @Column(length = -1)
  @Schema(description = "Литература.")
  @JsonProperty(value = "LITERATURE", access = JsonProperty.Access.WRITE_ONLY)
  private String literature;

  @Column(length = -1)
  @Schema(description = "Комментарий.")
  @JsonProperty(value = "COMMENT", access = JsonProperty.Access.WRITE_ONLY)
  private String comment;

  @Column(length = -1)
  @Schema(description = "Производитель")
  @JsonProperty(value = "MANUFACTURER", access = JsonProperty.Access.WRITE_ONLY)
  private String manufacturer;

  @Column(length = -1)
  @Schema(description = "Назначение")
  @JsonProperty(value = "APPLY", access = JsonProperty.Access.WRITE_ONLY)
  private String apply;

  @Column(length = -1)
  @Schema(description = "Комплектация")
  @JsonProperty(value = "COMPLECTATION", access = JsonProperty.Access.WRITE_ONLY)
  private String complectation;

  @Column(length = -1)
  @Schema(description = "Принцип действия")
  @JsonProperty(value = "PRINCIPLE", access = JsonProperty.Access.WRITE_ONLY)
  private String principle;

  @Column(length = -1)
  @Schema(description = "Технические характеристики")
  @JsonProperty(value = "MAINTECHCHARS", access = JsonProperty.Access.WRITE_ONLY)
  private String mainTechChars;

  @Column(length = -1)
  @Schema(description = "Наблюдение за лечением")
  @JsonProperty(value = "OBSERVATION", access = JsonProperty.Access.WRITE_ONLY)
  private String observation;

  @Column(length = -1)
  @Schema(description = "Спецификация")
  @JsonProperty(value = "SPECIFICATION", access = JsonProperty.Access.WRITE_ONLY)
  private String specification;

  @Column(length = -1)
  @Schema(description = "Обслуживание")
  @JsonProperty(value = "SERVICE", access = JsonProperty.Access.WRITE_ONLY)
  private String service;

  @Column(length = -1)
  @Schema(description = "Фармгруппы")
  @JsonProperty(value = "PHARMGROUPS", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmGroups;

  @Column(length = -1)
  @Schema(description = "Фармдействия")
  @JsonProperty(value = "PHARMACTIONS", access = JsonProperty.Access.WRITE_ONLY)
  private String pharmActions;

  @JsonProperty(value = "MKB", access = JsonProperty.Access.WRITE_ONLY)
  @Column(length = -1)
  private String mkb;

  @Column(length = -1)
  @JsonProperty(value = "ATC", access = JsonProperty.Access.WRITE_ONLY)
  private String atc;
}

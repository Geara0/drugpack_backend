package com.geara.drugpack.dto.description;

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
public class DescriptionDto {

  @Schema(description = "Состав и форма выпуска", nullable = true)
  private String composition;

  @Schema(description = "Состав", nullable = true)
  private String compositionDf;

  @Schema(description = "Характеристика", nullable = true)
  private String characteristics;

  @Schema(description = "Фармакологические действия", nullable = true)
  private String pharmaActions;

  @Schema(description = "Действие на организм", nullable = true)
  private String actonOrg;

  @Schema(description = "Свойства компонентов", nullable = true)
  private String componentsProperties;

  @Schema(description = "Описание лекарственной формы", nullable = true)
  private String drugFormDescription;

  @Schema(description = "Фармакокинетика", nullable = true)
  private String pharmaKinetic;

  @Schema(description = "Фармакодинамика", nullable = true)
  private String pharmaDynamic;

  @Schema(description = "Фармакологические (иммунобиологические) свойства", nullable = true)
  private String pharmaProperties;

  @Schema(description = "Клиническая фармакология", nullable = true)
  private String clinicalPharmacology;

  @Schema(description = "Инструкция", nullable = true)
  private String direction;

  @Schema(description = "Показания", nullable = true)
  private String indications;

  @Schema(description = "Рекомендации", nullable = true)
  private String recommendations;

  @Schema(description = "Противопоказания", nullable = true)
  private String contraindications;

  @Schema(description = "Применение при беременности и кормлении грудью", nullable = true)
  private String pregnancyUse;

  @Schema(description = "Способ применения и дозы", nullable = true)
  private String useMethodAndDoses;

  @Schema(description = "Инструкция для пациента", nullable = true)
  private String instrForPac;

  @Schema(description = "Побочные действия", nullable = true)
  private String sideActions;

  @Schema(description = "Взаимодействие", nullable = true)
  private String interactions;

  @Schema(description = "Передозировка", nullable = true)
  private String overdose;

  @Schema(description = "Меры предосторожности", nullable = true)
  private String precautions;

  @Schema(description = "Особые указания", nullable = true)
  private String specialGuidelines;

  @Schema(description = "Форма выпуска", nullable = true)
  private String form;

  @Schema(description = "Условия отпуска из аптек", nullable = true)
  private String aptekaCondition;

  @Schema(description = "Литература", nullable = true)
  private String literature;

  @Schema(description = "Комментарий", nullable = true)
  private String comment;

  @Schema(description = "Производитель", nullable = true)
  private String manufacturer;

  @Schema(description = "Назначение", nullable = true)
  private String apply;

  @Schema(description = "Комплектация", nullable = true)
  private String complectation;

  @Schema(description = "Принцип действия", nullable = true)
  private String principle;

  @Schema(description = "Технические характеристики", nullable = true)
  private String mainTechChars;

  @Schema(description = "Наблюдение за лечением", nullable = true)
  private String observation;

  @Schema(description = "Спецификация", nullable = true)
  private String specification;

  @Schema(description = "Обслуживание", nullable = true)
  private String service;

  @Schema(description = "Фармгруппы", nullable = true)
  private String pharmGroups;

  @Schema(description = "Фармдействия", nullable = true)
  private String pharmActions;
}

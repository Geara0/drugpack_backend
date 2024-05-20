package com.geara.drugpack.entities.drug.aurora.drug;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.geara.drugpack.entities.drug.aurora.activesubstance.AuroraActiveSubstance;
import com.geara.drugpack.entities.drug.aurora.description.AuroraDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

// https://aurora.rlsnet.ru/Help/Api/GET-api-inventory_brief_packing_id

@Entity
@Table(schema = "aurora", name = "drug")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"description", "activeSubstance"})
public class AuroraDrug {
  @Id
  @Schema(
      description =
          "Идентификатор уровня товарной упаковки. Главный идентификатор номенклатурной позиции.")
  @JsonProperty(value = "packing_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long packingId;

  @Schema(description = "ID описания препарата из блока Описания (метод library_description).")
  @JsonProperty(value = "desc_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long descId;

  @Schema(description = "Идентификатор уровня препарата")
  @JsonProperty(value = "prep_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long prepId;

  @ManyToOne
  @JoinColumn(name = "description_id")
  @ToString.Exclude
  private AuroraDescription description;

  @Column(length = -1)
  @Schema(
      description =
          "Описание препарата. Включает в себя торговое название, форму выпуска и дозировку. Например, \"ЛИДОКАИН р-р д/ин. 20 мг/мл с нож. амп. №10\". Здесь ЛИДОКАИН - торговое название; р-р д/ин - раствор для инъекций, форма выпуска; 20 мг/мл - дозировка; с нож. амп. - с ножом ампульным, комплектация; №10 - общее количество 10 ампул.")
  @JsonProperty(value = "prep_short", access = JsonProperty.Access.WRITE_ONLY)
  private String prepShort;

  @Column(length = -1)
  @Schema(
      description =
          "Описание препарата. Включает в себя торговое название, форму выпуска и дозировку. Например, \"ЛИДОКАИН раствор для инъекций. 20 мг/мл с ножом ампульным №10\". Здесь ЛИДОКАИН - торговое название; раствор для инъекций - форма выпуска; 20 мг/мл - дозировка; с ножом ампульным - комплектация; №10 - общее количество 10 ампул.")
  @JsonProperty(value = "prep_full", access = JsonProperty.Access.WRITE_ONLY)
  private String prepFull;

  @Column(length = 32)
  @Schema(description = "Общее количество элементов (таблеток, ампул) в упаковке")
  private String amount;

  @Column(length = 769)
  @Schema(
      description =
          "Описание товарной упаковки в сокращенном виде. Включает в себя первичную упаковку, количество первичных упаковок, вторичную упаковку, количество вторичных упаковок, упаковку третьего уровня. Например, \"амп. 2 мл (5) - уп. контурн. пластик. (поддоны) (2) - пач. картон.\". Здесь внутри картонной пачки находится две пластиковые контурные упаковки, каждая из которых содежит 5 ампул по 2 мл.")
  @JsonProperty(value = "packing_short", access = JsonProperty.Access.WRITE_ONLY)
  private String packingShort;

  @Column(length = 769)
  @Schema(
      description =
          "Описание товарной упаковки в полном виде. Включает в себя первичную упаковку, количество первичных упаковок, вторичную упаковку, количество вторичных упаковок, упаковку третьего уровня. Например, \"ампулы 2 мл (5) - упаковка контурная пластиковая (поддоны) (2) - пачка картонная\". Здесь внутри картонной пачки находится две пластиковые контурные упаковки, каждая из которых содежит 5 ампул по 2 мл.")
  @JsonProperty(value = "packing_full", access = JsonProperty.Access.WRITE_ONLY)
  private String packingFull;

  @Column(length = 1024)
  @Schema(description = "Штрихкод EAN")
  private String barcode;

  @Column(length = 62)
  @Schema(description = "ID фирмы производителя / упаковщика")
  @JsonProperty(value = "firms_id", access = JsonProperty.Access.WRITE_ONLY)
  private String firmsId;

  @Column(length = -1)
  @Schema(description = "Название фирмы производителя/упаковщика")
  private String firms;

  @Schema(description = "ID действующего вещества")
  @JsonProperty(value = "as_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long asId;

  @ManyToOne()
  @JoinColumn(name = "active_substance_id")
  @ToString.Exclude
  private AuroraActiveSubstance activeSubstance;

  @Column(length = 300)
  @Schema(description = "Действующее вещество на русском языке (МНН).")
  @JsonProperty(value = "as_name_rus", access = JsonProperty.Access.WRITE_ONLY)
  private String asNameRus;

  @Schema(description = "ID регистрационного удостоверения")
  @JsonProperty(value = "reg_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long regId;

  @Schema(
      description =
          "Числовое значение статуса госрегистрации. 2 - дейстует; 3 - истек срок; 4 - аннулировано; 5 - приостановлено; 6 - заменено.")
  @JsonProperty(value = "reg_status_id", access = JsonProperty.Access.WRITE_ONLY)
  private int regStatusId;

  @Column(length = -1)
  @Schema(description = "Номер, дата, фирма-регистратор и статус регистрационного удостоверения.")
  private String registration;

  @Schema(description = "Код классификатора НТФР.")
  @JsonProperty(value = "ntfr_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long ntfrId;

  @Column(length = 1024)
  @Schema(description = "Название класса НТФР.")
  @JsonProperty(value = "ntfr_name", access = JsonProperty.Access.WRITE_ONLY)
  private String ntfrName;

  @Column(length = 802)
  @Schema(description = "Срок годности.")
  @JsonProperty(value = "lifetime_text", access = JsonProperty.Access.WRITE_ONLY)
  private String lifetimeText;

  @Schema(description = "Срок годности в месяцах.")
  @JsonProperty(value = "lifetime_month", access = JsonProperty.Access.WRITE_ONLY)
  private Double lifetimeMonth;

  @Column(length = 512)
  @Schema(description = "Условия хранения в сокращенном виде")
  @JsonProperty(value = "sc_short", access = JsonProperty.Access.WRITE_ONLY)
  private String scShort;

  @Column(length = 1026)
  @Schema(description = "Условия хранения.")
  @JsonProperty(value = "sc_text", access = JsonProperty.Access.WRITE_ONLY)
  private String scText;

  @Column(length = -1)
  @Schema(description = "Дата актуализации записи")
  private String actdate;

  @Column(length = 100)
  @Schema(description = "Имя файла картинки")
  private String picname;

  @JsonProperty(value = "dosage_form_id", access = JsonProperty.Access.WRITE_ONLY)
  private Long dosageFormId;

  @Column(length = 128)
  @JsonProperty(value = "dosage_form_full_name", access = JsonProperty.Access.WRITE_ONLY)
  private String dosageFormFullName;
}

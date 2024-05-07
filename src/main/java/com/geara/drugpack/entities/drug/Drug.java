package com.geara.drugpack.entities.drug;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geara.drugpack.entities.account.Account;
import com.geara.drugpack.utils.Searchable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"new", "auroraPackingId", "auroraDescriptionId", "accounts"})
public class Drug extends Searchable<Long> {
  @Column(nullable = false, unique = true)
  @Schema(description = "Packing id in aurora db", hidden = true)
  private long auroraPackingId;

  @Column(nullable = false)
  @Schema(description = "Name of the drug")
  private String name;

  @Schema(description = "Form of packaging")
  private String packaging;

  @Schema(description = "Manufacturer")
  private String firm;

  @Column(nullable = false)
  @Schema(description = "Description id in aurora db", hidden = true)
  private long auroraDescriptionId;

  @JsonBackReference
  @ToString.Exclude
  @Column(nullable = false)
  @Schema(description = "Accounts with this drug")
  @ManyToMany(mappedBy = "drugs")
  private Set<Account> accounts;

  public Drug() {}
}

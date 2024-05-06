package com.geara.drugpack.entities.drug;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geara.drugpack.entities.account.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"new", "auroraPackingId", "auroraDescriptionId", "metaphone"})
public class Drug extends AbstractPersistable<Long> {
  @Column(nullable = false)
  @Schema(description = "Packing id in aurora db", hidden = true)
  private String auroraPackingId;

  @Column(nullable = false)
  @Schema(description = "Name of the drug")
  private String name;

  @Schema(description = "Form of packaging")
  private String packaging;

  @Schema(description = "Manufacturer")
  private String firm;

  @Column(nullable = false)
  @Schema(description = "Description id in aurora db", hidden = true)
  private String auroraDescriptionId;

  @JsonBackReference
  @ToString.Exclude
  @Column(nullable = false)
  @Schema(description = "Accounts with this drug")
  @ManyToMany(mappedBy = "drugs")
  private Set<Account> accounts;

  @Column(nullable = false)
  @Schema(hidden = true)
  private String metaphone;

  public Drug() {}
}

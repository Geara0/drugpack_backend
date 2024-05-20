package com.geara.drugpack.entities.drug;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geara.drugpack.entities.account.Account;
import com.geara.drugpack.utils.Searchable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"new", "accounts", "source"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"source", "foreignId"}))
public class Drug extends Searchable<Long> {
  @Column(nullable = false, unique = true)
  @Schema(description = "Id in other db schema (like aurora)", hidden = true)
  private long foreignId;

  @JsonBackReference
  @ToString.Exclude
  @Column(nullable = false)
  @Schema(description = "Accounts with this drug")
  @ManyToMany(mappedBy = "drugs")
  private Set<Account> accounts;

  @Column(nullable = false)
  @Schema(description = "Source of drug (like aurora)")
  private DrugSource source;
}

package com.geara.drugpack.entities.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.geara.drugpack.entities.condition.Condition;
import com.geara.drugpack.entities.drug.Drug;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;

@Entity
@ToString
@Getter
@Setter
public class Account extends AbstractPersistable<Long> {
  @Schema(description = "Account's email")
  private String email;

  @Schema(description = "Account's password, encrypted in sha256")
  private String shaPassword;

  @JsonManagedReference
  @ToString.Exclude
  @Schema(description = "Set of drugs, which user takes")
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "account_drugs",
          joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
  private Set<Drug> drugs;

  @JsonManagedReference
  @ToString.Exclude
  @Schema(description = "Set of conditions, which user have")
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "account_conditions",
      joinColumns = @JoinColumn(name = "condition_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
  private Set<Condition> conditions;

  public Account(String email, String shaPassword) {
    this.email = email;
    this.shaPassword = shaPassword;
  }

  public Account() {}
}

package com.geara.drugpack.entities.drug;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Drug extends AbstractPersistable<Long> {

  @Column(nullable = false)
  @Schema(description = "Name of the drug")
  private String name;

  @JsonBackReference
  @ToString.Exclude
  @Schema(description = "Accounts with this condition")
  @ManyToMany(mappedBy = "drugs")
  private Set<Account> accounts;

  public Drug() {}
}

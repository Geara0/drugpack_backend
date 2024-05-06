package com.geara.drugpack.entities.condition;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geara.drugpack.entities.account.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@JsonIgnoreProperties({"new"})
public class Condition extends AbstractPersistable<Long> {

  @Column(nullable = false)
  @Schema(description = "Name of the condition")
  private String name;

  @JsonBackReference
  @ToString.Exclude
  @Schema(description = "Accounts with this condition")
  @ManyToMany(mappedBy = "conditions")
  private Set<Account> accounts;

  public Condition() {}
}

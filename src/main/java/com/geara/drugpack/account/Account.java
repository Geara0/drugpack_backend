package com.geara.drugpack.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {
  @Schema(description = "Account's email")
  private String email;

  @Schema(description = "Account's password, encrypted in sha256")
  private String shaPassword;

  public Account() {}
}

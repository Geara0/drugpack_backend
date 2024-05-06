package com.geara.drugpack.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {
  @Schema(example = "a@a.a", requiredMode = Schema.RequiredMode.REQUIRED)
  private @NonNull String email;

  @Schema(
      description = "Password (preferably encrypted)",
      example = "66e8dfebeab1b04517001fa570993815c7cc8dc5a16f9136e293aefef8cf5ad2",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private @NonNull String password;
}

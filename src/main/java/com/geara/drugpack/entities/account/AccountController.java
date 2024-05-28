package com.geara.drugpack.entities.account;

import com.geara.drugpack.entities.condition.ConditionRepository;
import com.geara.drugpack.dto.compatibility.CompatibilityDtoMapper;
import com.geara.drugpack.dto.condition.ConditionDto;
import com.geara.drugpack.dto.condition.ConditionDtoMapper;
import com.geara.drugpack.dto.drug.DrugDto;
import com.geara.drugpack.dto.drug.DrugDtoMapper;
import com.geara.drugpack.entities.drug.DrugRepository;
import com.geara.drugpack.responses.GetAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("account")
@Tag(name = "Account", description = "Methods used for getting info about specific account")
public class AccountController {

  private final AccountRepository repository;
  private final DrugRepository drugRepository;
  private final ConditionRepository conditionRepository;
  private final DrugDtoMapper drugDtoMapper;
  private final ConditionDtoMapper conditionDtoMapper;
  private final AuroraCompatibilityService auroraCompatibilityService;
  private final CompatibilityDtoMapper compatibilityDtoMapper;

  private void updateCompatibility(Account account) {
    final var grouped = account.getDrugs().stream().collect(Collectors.groupingBy(Drug::getSource));
    auroraCompatibilityService.updateForAccount(account, grouped.get(Source.aurora));
  }

  public Account newAccount(String email, String password) {
    final var account = new Account(email, password);
    repository.save(account);
    return account;
  }

  @Operation(summary = "Get all account info")
  @ApiResponse(
      responseCode = "200",
      description = "Returns conditions",
      content = @Content(schema = @Schema(implementation = GetAccountResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("get")
  public ResponseEntity<Object> get(Principal principal) {
    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    final var conditions =
        account.getConditions().stream().map(conditionDtoMapper::conditionToConditionDto).toList();
    final var drugs = account.getDrugs().stream().map(drugDtoMapper::drugToDrugDto).toList();
    final var compatibilities =
        account.getCompatibility().entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    e ->
                        compatibilityDtoMapper.compatibilityDataToCompatibility(
                            e.getKey(), e.getValue())));

    return ResponseEntity.ok(new GetAccountResponse(conditions, drugs, compatibilities));
  }

  @Operation(summary = "Get account conditions")
  @ApiResponse(
      responseCode = "200",
      description = "Returns conditions",
      content =
          @Content(array = @ArraySchema(schema = @Schema(implementation = ConditionDto.class))))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("conditions")
  public ResponseEntity<Object> getConditions(Principal principal) {
    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    return ResponseEntity.ok(
        account.getConditions().stream().map(conditionDtoMapper::conditionToConditionDto).toList());
  }

  @Operation(summary = "Get account drugs")
  @ApiResponse(
      responseCode = "200",
      description = "Returns drugs",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = DrugDto.class))))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("drugs")
  public ResponseEntity<Object> getDrugs(Principal principal) {
    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    return ResponseEntity.ok(
        account.getDrugs().stream().map(drugDtoMapper::drugToDrugDto).toList());
  }

  @Operation(summary = "Add account conditions by ids")
  @ApiResponse(
      responseCode = "200",
      description = "true if set of conditions changed as a result of the call",
      content = @Content(schema = @Schema(implementation = boolean.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("addConditions")
  public ResponseEntity<Object> addConditions(
      Principal principal, @RequestBody final Set<Long> conditions) {
    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    final var dbConditions = conditionRepository.findAllById(conditions);

    final var newAccountConditions = account.getConditions();
    final var res = newAccountConditions.addAll(dbConditions);
    account.setConditions(newAccountConditions);
    repository.save(account);

    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Add account drugs by ids")
  @ApiResponse(
      responseCode = "200",
      description = "true if set of drugs changed as a result of the call",
      content = @Content(schema = @Schema(implementation = boolean.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("addDrugs")
  public ResponseEntity<Object> addDrugs(Principal principal, @RequestBody final Set<Long> drugs) {
    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    final var newAccountDrugs = account.getDrugs();
    final var res = newAccountDrugs.addAll(drugRepository.findAllById(drugs));
    account.setDrugs(newAccountDrugs);
    repository.save(account);
    updateCompatibility(account);

    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Remove account conditions by ids")
  @ApiResponse(
      responseCode = "200",
      description = "true if any elements were removed",
      content = @Content(schema = @Schema(implementation = boolean.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("removeConditions")
  public ResponseEntity<Object> removeConditions(
      Principal principal, @RequestBody final Set<Long> conditions) {
    if (conditions == null || conditions.isEmpty()) {
      return ResponseEntity.badRequest().body("Conditions cannot be an empty field");
    }

    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    final var newAccountConditions = account.getConditions();
    final var res = newAccountConditions.removeIf(e -> conditions.contains(e.getId()));
    account.setConditions(newAccountConditions);
    repository.save(account);

    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Remove account drugs by ids")
  @ApiResponse(
      responseCode = "200",
      description = "true if any elements were removed",
      content = @Content(schema = @Schema(implementation = void.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Returns error message",
      content = @Content(schema = @Schema(implementation = String.class)))
  @PostMapping("removeDrugs")
  public ResponseEntity<Object> removeDrugs(
      Principal principal, @RequestBody final Set<Long> drugs) {
    if (drugs == null || drugs.isEmpty()) {
      return ResponseEntity.badRequest().body("Drugs cannot be an empty field");
    }

    final var account = repository.findByEmail(principal.getName());

    if (account == null) {
      return ResponseEntity.badRequest().body("No such account found");
    }

    final var newAccountDrugs = account.getDrugs();
    final var res = newAccountDrugs.removeIf(e -> drugs.contains(e.getId()));
    account.setDrugs(newAccountDrugs);
    repository.save(account);
    updateCompatibility(account);

    return ResponseEntity.ok(res);
  }
}

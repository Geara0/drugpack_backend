package com.geara.drugpack.entities.drug.aurora.compatibility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.geara.drugpack.dto.compatibility.CompatibilityDto;
import com.geara.drugpack.entities.account.Account;
import com.geara.drugpack.entities.account.AccountRepository;
import com.geara.drugpack.entities.account.CompatibilityData;
import com.geara.drugpack.entities.drug.Drug;
import com.geara.drugpack.entities.drug.Source;
import com.geara.drugpack.entities.drug.aurora.drug.AuroraDrugRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-secret.properties")
public class AuroraCompatibilityService {

  private final AuroraDrugRepository drugRepository;
  private final AuroraCompatibilityRepository interactionRepository;
  private final AccountRepository accountRepository;

  @Value("${aurora.interact.password}")
  private String auroraInteractPassword;

  @Value("${aurora.interact.login}")
  private String auroraInteractLogin;

  public void updateForAccount(Account account, List<Drug> drugs) {
    if (drugs == null) return;

    final var auroraDrugs =
        drugRepository.findAllById(drugs.stream().map(Drug::getForeignId).toList());

    final var auroraSubstanceIds =
        auroraDrugs.stream()
            .map(
                e -> {
                  final var as = e.getActiveSubstance();
                  if (as != null) return as.getId();
                  return null;
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    if (auroraSubstanceIds.isEmpty()) return;

    final var requestIds =
        auroraSubstanceIds.stream().map(String::valueOf).collect(Collectors.joining(","));

    final var provider = new BasicCredentialsProvider();

    provider.setCredentials(
        AuthScope.ANY,
        new UsernamePasswordCredentials(auroraInteractLogin, auroraInteractPassword));
    final var httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();

    final var request = new HttpGet("https://rls-aurora.ru/api/interact_v2?as_ids=" + requestIds);

    try {
      HttpResponse response = httpClient.execute(request);

      final var json = EntityUtils.toString(response.getEntity());
      final ObjectMapper objectMapper = new ObjectMapper();
      final List<AuroraCompatibility> interactions =
          objectMapper
              .registerModule(new ParameterNamesModule())
              .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
              .readValue(
                  json,
                  objectMapper
                      .getTypeFactory()
                      .constructCollectionType(List.class, AuroraCompatibility.class));

      interactionRepository.saveAll(interactions);

      final var fullCompatibility = account.getCompatibility();
      var compatibility = fullCompatibility.get(Source.aurora);

      if (compatibility == null) {
        compatibility = new CompatibilityData();
        compatibility.ids = new HashSet<>();
      }

      compatibility.ids.addAll(interactions.stream().map(AuroraCompatibility::getId).toList());
      fullCompatibility.put(Source.aurora, compatibility);
      account.setCompatibility(fullCompatibility);
      accountRepository.save(account);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public @NotNull CompatibilityDto getDto(@NotNull AuroraCompatibility interaction) {
    if (interaction == null) throw new IllegalArgumentException();

    return new CompatibilityDto(
        interaction.getLeftAsId(),
        interaction.getRightAsId(),
        interaction.getClassId(),
        interaction.getClassName(),
        interaction.getSubclassId(),
        interaction.getSubclassName(),
        interaction.getDirectionId(),
        interaction.getDirectionName(),
        interaction.getOfficialSrcName(),
        interaction.getOfficialSrcNote(),
        interaction.getOfficialSrcDate(),
        interaction.getAlternativeSrcName(),
        interaction.getAlternativeSrcNote(),
        interaction.getAlternativeSrcDate());
  }
}

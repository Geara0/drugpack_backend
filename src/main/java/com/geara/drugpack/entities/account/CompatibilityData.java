package com.geara.drugpack.entities.account;

import jakarta.persistence.Embeddable;

import java.util.Set;

@Embeddable
public class CompatibilityData {
  public Set<Long> ids;
}

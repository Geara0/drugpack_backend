package com.geara.drugpack.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@MappedSuperclass
@JsonIgnoreProperties({"metaphone"})
public abstract class Searchable<T extends Serializable> extends AbstractPersistable<T> {
  @Column(nullable = false)
  @Schema(hidden = true)
  private String metaphone;
}

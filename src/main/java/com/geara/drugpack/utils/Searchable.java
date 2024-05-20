package com.geara.drugpack.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@JsonIgnoreProperties({"metaphone"})
public abstract class Searchable<T extends Serializable> extends AbstractPersistable<T> {
  @Column(nullable = false, length = -1)
  @Schema(hidden = true)
  private String metaphone;
}

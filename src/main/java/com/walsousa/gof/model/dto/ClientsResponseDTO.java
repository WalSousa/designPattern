package com.walsousa.gof.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.walsousa.gof.model.SimplePageable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ClientsResponseDTO implements Serializable {
  static final long serialVersionUID = -8767999397685664L;

  @JsonProperty("_pageable")
  SimplePageable pageable = new SimplePageable();

  @JsonProperty("_content")
  private List<ClientsDTO> content;
}

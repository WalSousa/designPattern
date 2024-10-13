package com.walsousa.gof.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Endereco {

	@Id
	@JsonProperty
	private String cep;
	@JsonProperty
	private String logradouro;
	@JsonProperty
	private String complemento;
	@JsonProperty
	private String bairro;
	@JsonProperty
	private String localidade;
	@JsonProperty
	private String uf;
	@JsonProperty
	private String ibge;
	@JsonProperty
	private String gia;
	@JsonProperty
	private String ddd;
	@JsonProperty
	private String siafi;
}

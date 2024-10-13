package com.walsousa.gof.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String profissao;
	private String idade;
	@ManyToOne
	private Endereco endereco;
}

package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @NoArgsConstructor @ToString
public class AutorDTO {
	
	@Getter @Setter private Long id;
	@Getter @Setter private String nome;
	private Optional<String> descricao = Optional.empty();
	
	public Optional<String> getDescricao() {
		return descricao;
	}

	public void setSenha(Optional<String> descricao) {
		this.descricao = descricao;
	}
	
}

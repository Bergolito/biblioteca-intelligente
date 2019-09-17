package com.kazale.pontointeligente.api.dtos;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @NoArgsConstructor @ToString
public class UsuarioDTO {
	
	@Getter @Setter private Long id;
	@Getter @Setter private String nome;
	@Getter @Setter private String email;
	@Getter @Setter private String cpf;
	private Optional<String> senha = Optional.empty();
	
	public Optional<String> getSenha() {
		return senha;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}
	
}

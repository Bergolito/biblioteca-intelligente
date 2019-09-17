package com.kazale.pontointeligente.api.security.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @ToString
public class JwtAuthenticationDto {
	
	@Setter private String email;
	@Setter private String senha;

	@NotEmpty(message = "Email não pode ser vazio.")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}

//	public void setEmail(String email) {
//		this.email = email;
//	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

//	public void setSenha(String senha) {
//		this.senha = senha;
//	}

//	@Override
//	public String toString() {
//		return "JwtAuthenticationRequestDto [email=" + email + ", senha=" + senha + "]";
//	}

}

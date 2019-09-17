package com.kazale.pontointeligente.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor @NoArgsConstructor @ToString
public class TokenDto {

	@Getter @Setter private String token; 
	
}

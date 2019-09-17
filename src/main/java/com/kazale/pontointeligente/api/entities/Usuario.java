package com.kazale.pontointeligente.api.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.kazale.pontointeligente.api.enums.PerfilEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@AllArgsConstructor @NoArgsConstructor @ToString
public class Usuario implements Serializable {

	private static final long serialVersionUID = -5754246207015712518L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private Long id;
	
	@Column(name = "nome", nullable = false)
	@Getter @Setter private String nome;
	
	@Column(name = "email", nullable = false)
	@Getter @Setter private String email;
	
	@Column(name = "senha", nullable = false)
	@Getter @Setter private String senha;
	
	@Column(name = "cpf", nullable = false)
	@Getter @Setter private String cpf;
	
	@Column(name = "perfil", nullable = false)
	@Getter @Setter private PerfilEnum perfil;

}

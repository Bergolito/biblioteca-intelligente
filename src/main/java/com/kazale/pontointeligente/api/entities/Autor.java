package com.kazale.pontointeligente.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "autor")
@AllArgsConstructor @NoArgsConstructor @ToString
public class Autor implements Serializable {

	private static final long serialVersionUID = -5754246207015712518L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private Long id;
	
	@Column(name = "nome", nullable = false)
	@Getter @Setter private String nome;
	
	@Column(name = "descricao", nullable = true)
	@Getter @Setter private String descricao;
	
}

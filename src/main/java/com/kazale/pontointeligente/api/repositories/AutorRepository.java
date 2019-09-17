package com.kazale.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kazale.pontointeligente.api.entities.Autor;
import com.kazale.pontointeligente.api.entities.Usuario;

@Transactional(readOnly = true)
public interface AutorRepository extends JpaRepository<Autor, Long> {

	Autor findByNome(String nome);
	
	Autor findByDescricao(String descricao);
	
	Autor findByNomeOrDescricao(String nome, String descricao);
}

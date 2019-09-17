package com.kazale.pontointeligente.api.services;

import java.util.Optional;

import com.kazale.pontointeligente.api.entities.Autor;

public interface AutorService {
	
	/**
	 * Persiste um funcionário na base de dados.
	 * 
	 * @param Autor
	 * @return Autor
	 */
	Autor persistir(Autor autor);
	
	/**
	 * Busca e retorna um funcionário dado um CPF.
	 * 
	 * @param cpf
	 * @return Optional<Autor>
	 */
	Optional<Autor> buscarPorNome(String nome);
	
	/**
	 * Busca e retorna um funcionário dado um email.
	 * 
	 * @param email
	 * @return Optional<Autor>
	 */
	Optional<Autor> buscarPorDescricao(String descricao);
	
	/**
	 * Busca e retorna um funcionário por ID.
	 * 
	 * @param id
	 * @return Optional<Autor>
	 */
	Optional<Autor> buscarPorId(Long id);

}

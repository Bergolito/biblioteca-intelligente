package com.kazale.pontointeligente.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kazale.pontointeligente.api.entities.Autor;
import com.kazale.pontointeligente.api.repositories.AutorRepository;
import com.kazale.pontointeligente.api.services.AutorService;

@Service
public class AutorServiceImpl implements AutorService {
	
	private static final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

	@Autowired
	private AutorRepository autorRepository;
	
	public Autor persistir(Autor Autor) {
		log.info("Persistindo autor: {}", Autor);
		return this.autorRepository.save(Autor);
	}
	
	public Optional<Autor> buscarPorNome(String nome) {
		log.info("Buscando autor pelo CPF {}", nome);
		return Optional.ofNullable(this.autorRepository.findByNome(nome));
	}
	
	public Optional<Autor> buscarPorDescricao(String descricao) {
		log.info("Buscando autor pelo email {}", descricao);
		return Optional.ofNullable(this.autorRepository.findByDescricao(descricao));
	}
	
	public Optional<Autor> buscarPorId(Long id) {
		log.info("Buscando autor pelo ID {}", id);
		return Optional.ofNullable(this.autorRepository.findOne(id));
	}

}

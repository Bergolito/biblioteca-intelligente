package com.kazale.pontointeligente.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kazale.pontointeligente.api.entities.Usuario;
import com.kazale.pontointeligente.api.repositories.UsuarioRepository;
import com.kazale.pontointeligente.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario persistir(Usuario Usuario) {
		log.info("Persistindo usuário: {}", Usuario);
		return this.usuarioRepository.save(Usuario);
	}
	
	public Optional<Usuario> buscarPorCpf(String cpf) {
		log.info("Buscando usuário pelo CPF {}", cpf);
		return Optional.ofNullable(this.usuarioRepository.findByCpf(cpf));
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando usuário pelo email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
	
	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando usuário pelo IDl {}", id);
		return Optional.ofNullable(this.usuarioRepository.findOne(id));
	}

}

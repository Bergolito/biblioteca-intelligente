package com.kazale.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kazale.pontointeligente.api.dtos.UsuarioDTO;
import com.kazale.pontointeligente.api.entities.Usuario;
import com.kazale.pontointeligente.api.enums.PerfilEnum;
import com.kazale.pontointeligente.api.response.Response;
import com.kazale.pontointeligente.api.services.UsuarioService;
import com.kazale.pontointeligente.api.utils.PasswordUtils;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/cadastrar-usuario")
@CrossOrigin(origins = "*")
@NoArgsConstructor
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Cadastra um usuário pessoa física no sistema.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroPFDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> cadastrar(
			@Valid @RequestBody UsuarioDTO cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Cadastrando PF: {}", cadastroPFDto.toString());
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();

		Usuario usuario = this.converterDtoParaUsuario(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.usuarioService.persistir(usuario);

		response.setData(this.converterUsuarioDTO(usuario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte os dados do DTO para usuário.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return Usuario
	 * @throws NoSuchAlgorithmException
	 */
	private Usuario converterDtoParaUsuario(UsuarioDTO usuarioDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setCpf(usuarioDTO.getCpf());
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		usuario.setSenha(PasswordUtils.gerarBCrypt(usuarioDTO.getSenha().get()));

		return usuario;
	}

	/**
	 * Atualiza os dados de um usuário.
	 * 
	 * @param id
	 * @param UsuarioDTO
	 * @param result
	 * @return ResponseEntity<Response<UsuarioDTO>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDTO>> atualizar(
			@PathVariable("id") Long id,
			@Valid @RequestBody UsuarioDTO UsuarioDTO, BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Atualizando usuário: {}", UsuarioDTO.toString());
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();

		Optional<Usuario> Usuario = this.usuarioService.buscarPorId(id);
		if (!Usuario.isPresent()) {
			result.addError(new ObjectError("Usuario", "usuário não encontrado."));
		}

		this.atualizarDadosUsuario(Usuario.get(), UsuarioDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando usuário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(Usuario.get());
		response.setData(this.converterUsuarioDTO(Usuario.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do usuário com base nos dados encontrados no DTO.
	 * 
	 * @param Usuario
	 * @param UsuarioDTO
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosUsuario(Usuario Usuario, UsuarioDTO usuarioDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		Usuario.setNome(usuarioDTO.getNome());

		if (!Usuario.getEmail().equals(usuarioDTO.getEmail())) {
			this.usuarioService.buscarPorEmail(usuarioDTO.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
			Usuario.setEmail(usuarioDTO.getEmail());
		}

		if (usuarioDTO.getSenha().isPresent()) {
			Usuario.setSenha(PasswordUtils.gerarBCrypt(usuarioDTO.getSenha().get()));
		}
	}

	/**
	 * Retorna um DTO com os dados de um usuário.
	 * 
	 * @param Usuario
	 * @return UsuarioDTO
	 */
	private UsuarioDTO converterUsuarioDTO(Usuario Usuario) {
		UsuarioDTO UsuarioDTO = new UsuarioDTO();
		UsuarioDTO.setId(Usuario.getId());
		UsuarioDTO.setEmail(Usuario.getEmail());
		UsuarioDTO.setNome(Usuario.getNome());

		return UsuarioDTO;
	}

}

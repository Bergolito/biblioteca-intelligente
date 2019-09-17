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
import com.kazale.pontointeligente.api.dtos.AutorDTO;
import com.kazale.pontointeligente.api.entities.Autor;
import com.kazale.pontointeligente.api.response.Response;
import com.kazale.pontointeligente.api.services.AutorService;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/cadastrar-autor")
@CrossOrigin(origins = "*")
@NoArgsConstructor
public class AutorController {

	private static final Logger log = LoggerFactory.getLogger(AutorController.class);

	@Autowired
	private AutorService autorService;

	/**
	 * Cadastra um usuário pessoa física no sistema.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroPFDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<AutorDTO>> cadastrar(
			@Valid @RequestBody AutorDTO cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Cadastrando Autor: {}", cadastroPFDto.toString());
		Response<AutorDTO> response = new Response<AutorDTO>();

		Autor usuario = this.converterDtoParaAutor(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de autor: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.autorService.persistir(usuario);

		response.setData(this.converterAutorDTO(usuario));
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
	private Autor converterDtoParaAutor(AutorDTO AutorDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		
		Autor autor = new Autor();
		autor.setNome(AutorDTO.getNome());
		autor.setDescricao(AutorDTO.getDescricao().get());

		return autor;
	}

	/**
	 * Atualiza os dados de um usuário.
	 * 
	 * @param id
	 * @param AutorDTO
	 * @param result
	 * @return ResponseEntity<Response<AutorDTO>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<AutorDTO>> atualizar(
			@PathVariable("id") Long id,
			@Valid @RequestBody AutorDTO AutorDTO, BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Atualizando usuário: {}", AutorDTO.toString());
		Response<AutorDTO> response = new Response<AutorDTO>();

		Optional<Autor> autor = this.autorService.buscarPorId(id);
		if (!autor.isPresent()) {
			result.addError(new ObjectError("autor", "autor não encontrado."));
		}

		this.atualizarDadosAutor(autor.get(), AutorDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando usuário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.autorService.persistir(autor.get());
		response.setData(this.converterAutorDTO(autor.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do usuário com base nos dados encontrados no DTO.
	 * 
	 * @param Usuario
	 * @param AutorDTO
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosAutor(Autor autor, AutorDTO AutorDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		autor.setNome(AutorDTO.getNome());

		if (!autor.getNome().equals(AutorDTO.getNome())) {
			this.autorService.buscarPorNome(AutorDTO.getNome())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
			autor.setNome(AutorDTO.getNome());
		}

		if (AutorDTO.getDescricao().isPresent()) {
			autor.setDescricao(AutorDTO.getDescricao().get());
		}
	}

	/**
	 * Retorna um DTO com os dados de um usuário.
	 * 
	 * @param Usuario
	 * @return AutorDTO
	 */
	private AutorDTO converterAutorDTO(Autor autor) {
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setId(autor.getId());
		autorDTO.setNome(autor.getNome());
		autorDTO.getDescricao().ifPresent(desc -> autor.setDescricao(desc));

		return autorDTO;
	}

}

package br.ucsal.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ucsal.dto.*;
import br.ucsal.service.ILivroService;

@RestController
@RequestMapping("livros")
@Tag(name = "Livros")
public class LivroController {

	@Autowired
	private ILivroService service;

	@GetMapping("/{id}")
	@Operation(summary = "Recuperar um livro pelo ID")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		var livro = service.get(id);
		if (livro.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(livro);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new LivroNotFoundResponse(id));
		}

	}

	@GetMapping
	@Operation(summary = "Recuperar todos os livros")
	public ResponseEntity<List<LivroResponse>> getAll() {

		var response = service.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PostMapping
	@Operation(summary = "Criar um livro")
	public ResponseEntity<AddLivroResponse> create(@RequestBody LivroRequest dto) {

		System.out.println("Request: " +dto.titulo() + dto.anoPublicacao() + dto.categoria());
		var response = service.add(dto);

		if (response.success())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar um livro")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LivroRequest request) {

		var response = service.update(id, request);

		if (response.success())
			return ResponseEntity.status(HttpStatus.OK).body(response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir um livro")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		var response = service.delete(id);

		if (response.success())
			return ResponseEntity.status(HttpStatus.OK).body(response);

		if (response.message().contains("não encontrado"))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}